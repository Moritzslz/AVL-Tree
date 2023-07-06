package gad.avl;

import java.util.ArrayList;
import java.util.stream.*;

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
        ArrayList<AVLTreeNode> leftTree = new ArrayList<>();
        ArrayList<AVLTreeNode> rightTree = new ArrayList<>();
        ArrayList<AVLTreeNode> visited = new ArrayList<>();
        leftTree.add(root);
        rightTree.add(root);
        visited.add(root);

        boolean leftValid = root.getLeft() != null ? root.getLeft().validateLeft(visited, root.getKey() - 1) : true;
        boolean rightValid = root.getRight() != null ? root.getRight().validateRight(visited, root.getKey()) : true;

        return leftValid && rightValid;
    }

    public void insert(int key) {
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
        AVLTreeNode two = new AVLTreeNode(12);
        one.setRight(two);
        AVLTreeNode three = new AVLTreeNode(8);
        one.setLeft(three);
        AVLTreeNode four = new AVLTreeNode(9);
        three.setRight(four);
        AVLTreeNode five = new AVLTreeNode(11);
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