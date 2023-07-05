package gad.avl;

import java.lang.reflect.Array;
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
            // Circle exists
            return false;
        } else {
            nodes.add(this);
        }

        boolean valid = true;
        int balance = 0;

        if (hasLeft() && hasRight()) {
            if (left.getKey() > getKey() || right.getKey() > getKey()) {
                return false;
            }

            if (left.getKey() > right.getKey()) {
                return false;
            }

            balance = right.height() - left.height();
        } else if (hasLeft()) {
            if (left.getKey() > getKey()) {
                return false;
            }
            balance = 0 - left.height();
        } else if (hasRight()) {
            if (right.getKey() > getKey()) {
                return false;
            }
            balance = right.height();
        }

        if (balance != getBalance()) {
            return false;
        }

        if (getBalance() != -1 && getBalance() != 0 && getBalance() != 1) {
            return false;
        }

        // Recursive call
        if (hasLeft()) {
            valid = getLeft().validate(nodes);
        }
        if (hasRight() && valid) {
            valid = getRight().validate(nodes);
        }

        return valid;
    }

    private boolean hasRight() {
        return (getRight() != null);
    }

    private boolean hasLeft() {
        return (getLeft() != null);
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