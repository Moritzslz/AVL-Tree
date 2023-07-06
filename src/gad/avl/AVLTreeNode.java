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

    public boolean validateLeft(ArrayList<AVLTreeNode> visited, int allowedMax) {
        if (getKey() > allowedMax) {
            System.out.println("To big value in left subtree: " + getKey());
            return false;
        }

        if (hasCircle(visited)) {
            System.out.println("Has circle: " + hasCircle(visited));
            return false;
        }

        int leftHeight = getLeft() != null ? getLeft().height() : 0;
        int rightHeight = getRight() != null ? getRight().height() : 0;
        int calculatedBalance = rightHeight - leftHeight;

        if (getBalance() != calculatedBalance) {
            System.out.println("Wrong saved balance");
            return false;
        }
        if (Math.abs(getBalance()) > 1) {
            System.out.println("Balance to big");
            return false;
        }
        if (getLeft() != null && getLeft().getKey() > getKey()) {
            System.out.println("Left key bigger current key");
            return false;
        }
        if (getRight() != null && getRight().getKey() < getKey()) {
            System.out.println("Right key smaller current key");
            return false;
        }

        boolean left = getLeft() != null ? getLeft().validateLeft(visited, allowedMax) : true;
        boolean right = getRight() != null ? getRight().validateLeft(visited, allowedMax) : true;

        return left && right;
    }

    public boolean validateRight(ArrayList<AVLTreeNode> visited, int allowedMin) {
        if (getKey() < allowedMin) {
            System.out.println("To small value in right subtree: " + getKey());
            return false;
        }

        if (hasCircle(visited)) {
            System.out.println("Has circle: " + hasCircle(visited));
            return false;
        }

        int leftHeight = getLeft() != null ? getLeft().height() : 0;
        int rightHeight = getRight() != null ? getRight().height() : 0;
        int calculatedBalance = rightHeight - leftHeight;

        if (getBalance() != calculatedBalance) {
            System.out.println("Wrong saved balance");
            return false;
        }
        if (Math.abs(getBalance()) > 1) {
            System.out.println("Balance to big");
            return false;
        }
        if (getLeft() != null && getLeft().getKey() > getKey()) {
            System.out.println("Left key bigger current key");
            return false;
        }
        if (getRight() != null && getRight().getKey() < getKey()) {
            System.out.println("Right key smaller current key");
            return false;
        }

        boolean left = getLeft() != null ? getLeft().validateRight(visited, allowedMin) : true;
        boolean right = getRight() != null ? getRight().validateRight(visited, allowedMin) : true;

        return left && right;
    }

    public boolean hasCircle(ArrayList<AVLTreeNode> visited) {
        if (visited.contains(this)) {
            return true;
        } else {
            visited.add(this);
            if (getLeft() != null && visited.contains(getLeft())) {
                return true;
            }
            if (getRight() != null && visited.contains(getRight())) {
                return true;
            }
        }
        return false;
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