package gad.avl;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;

public class AVLTree {
    private AVLTreeNode root = null;
    private ArrayList<AVLTreeNode> nodes = new ArrayList<>();

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
            if (calculatedBalance > 1) {
                return false;
            } else if (root.getLeft().getKey() > root.getRight().getKey()) {
                return false;
            }
            return true;
    }

    public void insert(int key) {
        AVLTreeNode node = new AVLTreeNode(key);
        nodes.add(node);
        nodes.sort(Comparator.comparing(AVLTreeNode::getKey));
        if (key > root.getKey()) {
            // insert right
        } else  {
            // insert left
        }
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