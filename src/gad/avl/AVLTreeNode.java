package gad.avl;

import java.util.ArrayList;

public class AVLTreeNode {
    private int key;
    private int balance = 0;
    private AVLTreeNode left = null;
    private AVLTreeNode right = null;

    private boolean visited = false;

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

    public int height(AVLTreeNode node) {
        if (node == null) {
            return 0;
        }
        return Math.max(height(node.getLeft()), height(node.getRight())) + 1;
    }

    public boolean validateLeft(int allowedMax) {
        if (getKey() > allowedMax) {
            //System.out.println("To big value in left subtree: " + getKey());
            return false;
        }

        if (!validate()) {
            return false;
        }

        boolean left = getLeft() != null ? getLeft().validateLeft(allowedMax) : true;
        boolean right = getRight() != null ? getRight().validateLeft(allowedMax) : true;

        return left && right;
    }

    public boolean validateRight(int allowedMin) {
        if (getKey() < allowedMin) {
            //System.out.println("To small value in right subtree: " + getKey());
            return false;
        }

        if (!validate()) {
            return false;
        }

        boolean left = getLeft() != null ? getLeft().validateRight(allowedMin) : true;
        boolean right = getRight() != null ? getRight().validateRight(allowedMin) : true;

        return left && right;
    }

    public boolean validate () {
        int leftHeight = getLeft() != null ? height(getLeft()) : 0;
        int rightHeight = getRight() != null ? height(getRight()) : 0;
        int calculatedBalance = rightHeight - leftHeight;

        if (getBalance() != calculatedBalance) {
            //System.out.println("Wrong saved balance, calculated balance: " + calculatedBalance + " saved balance: " + getBalance() + " key: " + getKey());
            return false;
        }
        if (Math.abs(getBalance()) > 1) {
            //System.out.println("Balance to big");
            return false;
        }
        if (getLeft() != null && getLeft().getKey() > getKey()) {
            //System.out.println("Left key bigger current key");
            return false;
        }
        if (getRight() != null && getRight().getKey() < getKey()) {
            //System.out.println("Right key smaller current key");
            return false;
        }

        return true;
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