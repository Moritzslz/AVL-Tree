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
        ArrayList<AVLTreeNode> nodes = new ArrayList<>();
        return root.validate(nodes) && !root.hasCircle(new ArrayList<>());
    }

    public void insert(int key) {
        AVLTreeNode node = new AVLTreeNode(key);
        if (root != null) {
            root = insertNode(root, node);
        } else {
            root = node;
        }
        balanceTree(node);
    }

    private AVLTreeNode insertNode(AVLTreeNode current, AVLTreeNode node) {
        if (current == null) {
            return node;
        }

        if (node.getKey() < current.getKey()) {
            current.setLeft(insertNode(current.getLeft(), node));
        } else if (node.getKey() > current.getKey()) {
            current.setRight(insertNode(current.getRight(), node));
        }

        return current;
    }
    private void balanceTree(AVLTreeNode node) {
        AVLTreeNode current = node;
        while (current != null) {
            int balance = current.calculateBalance();
            if (balance > 1) {
                if (current.getRight() != null && current.getRight().calculateBalance() < 0) {
                    current.setRight(rotateRight(current.getRight()));
                }
                current = rotateLeft(current);
            } else if (balance < -1) {
                if (current.getLeft() != null && current.getLeft().calculateBalance() > 0) {
                    current.setLeft(rotateLeft(current.getLeft()));
                }
                current = rotateRight(current);
            }
        }
    }

    private AVLTreeNode rotateLeft(AVLTreeNode node) {
        AVLTreeNode rightChild = node.getRight();
        node.setRight(rightChild.getLeft());
        rightChild.setLeft(node);
        return rightChild;
    }

    private AVLTreeNode rotateRight(AVLTreeNode node) {
        AVLTreeNode leftChild = node.getLeft();
        node.setLeft(leftChild.getRight());
        leftChild.setRight(node);
        return leftChild;
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