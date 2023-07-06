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

    public boolean validate() {
        boolean valid = true;

        if (this == null) {
            return true;
        }

        int leftHeight = (left != null) ? left.height() : 0;
        int rightHeight = (right != null) ? right.height() : 0;
        int calculatedBalance = rightHeight - leftHeight;

        // Check if the AVL invariants are met
        if (balance != calculatedBalance) {
            return false;
        }
        if (Math.abs(calculatedBalance) > 1) {
            return false;
        }
        if (left != null && left.getKey() > key) {
            return false;
        }
        if (right != null && right.getKey() < key) {
            return false;
        }

        // Recursive call
        valid = left.validate() && right.validate();
        return valid;
    }

    public boolean hasCircle(ArrayList<AVLTreeNode> nodes) {
        if (this == null) {
            return false;
        }

        // Check whether the node has been visited before if so a circle is present
        if (nodes.contains(this)) {
            return true;
        } else {
            nodes.add(this);
            if (left != null && left.hasCircle(nodes)) {
                return true;
            }
            if (right != null && right.hasCircle(nodes)) {
                return true;
            }
        }
        return left.hasCircle(nodes) && right.hasCircle(nodes);
    }

    public boolean find(int key) {
        if (this.key == key) {
            return true;
        }
        if (key < this.key) {
            // Search left side
            if (left != null) {
                return left.find(key);
            } else {
                return false;
            }
        } else {
            // Search right side
            if (right != null) {
                return right.find(key);
            } else {
                return false;
            }
        }
    }

    public void insert(AVLTreeNode node) {
        if (node.getKey() < key) {
            // Insert left
            if (left != null) {
                left.insert(node);
            } else {
                left = node;
                resetBalance(left);
            }
        } else  {
            // Insert right
            if (right != null) {
                right.insert(node);
            } else {
                right = node;
                resetBalance(right);
            }
        }
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