package gad.avl;

public class AVLTree {
    private AVLTreeNode root = null;

    public AVLTree() {
    }

    public AVLTreeNode getRoot() {
        return root;
    }

    public void setRoot(AVLTreeNode root) {
        this.root = root;
    }

    public int height() {
        return root.height();
    }

    public boolean validAVL() {
        int savedBalance = root.getBalance();
        int calculatedBalance = root.getRight().height() - root.getLeft().height();
        if (savedBalance != calculatedBalance) {
            return false;
        }
        calculatedBalance = calculatedBalance * calculatedBalance;
        if (calculatedBalance < 1) {
            return false;
        } else if (root.getLeft().getKey() > root.getRight().getKey()) {
            return false;
        }
        return true;
    }

    public void insert(int key) {
    }

    public boolean find(int key) {
        return false;
    }

    /**
     * Diese Methode wandelt den Baum in das Graphviz-Format um.
     *
     * @return der Baum im Graphiz-Format
     */
    private String dot() {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph {" + System.lineSeparator());
        if (root != null) {
            root.dot(sb);
        }
        sb.append("}");
        return sb.toString();
    }

    @Override
    public String toString() {
        return dot();
    }
}