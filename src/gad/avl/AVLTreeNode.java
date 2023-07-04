package gad.avl;

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
        int savedBalance = getBalance();
        int calculatedBalance = 0;
        if (getLeft() == null && getRight() != null) {
            calculatedBalance = getRight().height() + 1;
        } else if (getRight() == null && getLeft() != null) {
            calculatedBalance = getLeft().height() + 1;
        } else if (getLeft() != null && getRight() != null) {
            calculatedBalance = getRight().height() - getLeft().height();
            if (getLeft().getKey() > getRight().getKey()) {
                return false;
            }
        }
        if (savedBalance != calculatedBalance) {
            return false;
        }
        calculatedBalance = calculatedBalance * calculatedBalance;
        if (calculatedBalance > 1) {
            return false;
        }
        if (getLeft() != null) {
            valid = getLeft().validate();
        }
        if (getRight() != null && valid) {
            valid = getRight().validate();
        }
        return valid;
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