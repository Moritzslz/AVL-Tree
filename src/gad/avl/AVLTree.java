package gad.avl;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;

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
        ArrayList<AVLTreeNode> nodes = new ArrayList<>();
        return root.validate(nodes);
    }

    public void insert(int key) {
        AVLTreeNode node = new AVLTreeNode(key);
        if (key < root.getKey()) {
            // Insert left
        } else  {
            // Insert right
        }
    }

    public boolean find(int key) {
       return root.find(key);
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