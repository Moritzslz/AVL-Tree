package gad.avl;

import java.util.ArrayList;

public class AVLTreeNode {
    private int key;
    private int balance = 0;
    private AVLTreeNode left = null;
    private AVLTreeNode right = null;

    public AVLTreeNode(int key) {
        this.key = key;
    }

    public AVLTreeNode getLeft() {
        return left;
    }

    public AVLTreeNode getRight() {
        return right;
    }

    public int getBalance() {
        return balance;
    }

    public int getKey() {
        return key;
    }

    public void setLeft(AVLTreeNode left) {
        this.left = left;
    }

    public void setRight(AVLTreeNode right) {
        this.right = right;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int height() {
        if (getLeft() == null && getRight() == null) {
            return 1;
        } else {
            int height = 0;
            if (getLeft() != null) {
                height += getLeft().height();
            }
            if (getRight() != null) {
                height += getRight().height();
            }
            return height;
        }
    }

    public boolean validate(ArrayList<AVLTreeNode> nodes) {
        if (nodes.contains(this)) {
            return false;
        }
        nodes.add(this);

        int leftHeight = (left != null) ? left.height() : 0;
        int rightHeight = (right != null) ? right.height() : 0;

        if (Math.abs(balance) > 1 || Math.abs(balance) != (rightHeight - leftHeight)) {
            return false;
        }

        boolean validLeft = (left == null) || (left.validate(nodes) && left.getKey() <= key);
        boolean validRight = (right == null) || (right.validate(nodes) && right.getKey() >= key);

        return validLeft && validRight;
    }

    public boolean hasCircle(ArrayList<AVLTreeNode> visited) {
        if (visited.contains(this)) {
            return true;
        } else {
            visited.add(this);
            boolean circleLeft = (left != null) && left.hasCircle(new ArrayList<>(visited));
            boolean circleRight = (right != null) && right.hasCircle(new ArrayList<>(visited));
            return circleLeft || circleRight;
        }
    }

    public boolean find(int key) {
        if (getKey() == key) {
            return true;
        }
        if (key < getKey()) {
            // Search left side
            if (getLeft() != null) {
                return getLeft().find(key);
            } else {
                return false;
            }
        } else {
            // Search right side
            if (getRight() != null) {
                return getRight().find(key);
            } else {
                return false;
            }
        }
    }

    public void insert(AVLTreeNode node) {
        if (node.getKey() < key) {
            if (left != null) {
                left.insert(node);
            } else {
                left = node;
            }
        } else if (node.getKey() > key) {
            if (right != null) {
                right.insert(node);
            } else {
                right = node;
            }
        }
    }

    public int calculateBalance() {
        int leftHeight = (left != null) ? left.height() : 0;
        int rightHeight = (right != null) ? right.height() : 0;
        return rightHeight - leftHeight;
    }

    public void resetBalance (AVLTreeNode node) {
        int leftHeight = (node.getLeft() != null) ? node.getLeft().height() : 0;
        int rightHeight = (node.getRight() != null) ? node.getRight().height() : 0;
        int calculatedBalance = rightHeight - leftHeight;
        node.setBalance(calculatedBalance);
    }


    /**
     * Diese Methode wandelt den Baum in das Graphviz-Format um.
     *
     * @param sb der StringBuilder für die Ausgabe
     */
    public void dot(StringBuilder sb) {
        dotNode(sb, 0);
    }

    private int dotNode(StringBuilder sb, int idx) {
        sb.append(String.format("\t%d [label=\"%d, b=%d\"];%n", idx, key, balance));
        int next = idx + 1;
        if (left != null) {
            next = left.dotLink(sb, idx, next, "l");
        }
        if (right != null) {
            next = right.dotLink(sb, idx, next, "r");
        }
        return next;
    }

    private int dotLink(StringBuilder sb, int idx, int next, String label) {
        sb.append(String.format("\t%d -> %d [label=\"%s\"];%n", idx, next, label));
        return dotNode(sb, next);
    }
}