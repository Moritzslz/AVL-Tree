package gad.avl;

import java.util.ArrayList;

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
        // Create and insert new node recursively
        AVLTreeNode node = new AVLTreeNode(key);
        root.insert(node);

        // Re-balance Tree is necessary
        if (validAVL()) {
            return;
        } else {

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

    public static void main(String[] args) {
        AVLTree avlTree = new AVLTree();
        AVLTreeNode one = new AVLTreeNode(10);
        avlTree.setRoot(one);
        AVLTreeNode two = new AVLTreeNode(11);
        one.setRight(two);
        AVLTreeNode three = new AVLTreeNode(9);
        one.setLeft(three);
        AVLTreeNode four = new AVLTreeNode(11);
        three.setRight(four);
        AVLTreeNode five = new AVLTreeNode(5);
        two.setLeft(five);
        one.setBalance(0);
        two.setBalance(-1);
        three.setBalance(1);
        four.setBalance(0);
        five.setBalance(0);


        System.out.println(avlTree.dot());
        System.out.println(avlTree.validAVL()); // Expected output: true
    }
}