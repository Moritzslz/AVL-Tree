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
        ArrayList<AVLTreeNode> visited = new ArrayList<>();

        if (hasCircle(root, visited)) {
            //System.out.println("Has circle");
            return false;
        }

        if (!root.validate()) {
            return false;
        }

        boolean leftValid = root.getLeft() != null ? root.getLeft().validateLeft(root.getKey()) : true;
        boolean rightValid = root.getRight() != null ? root.getRight().validateRight(root.getKey()) : true;

        return leftValid && rightValid;
    }

    public boolean hasCircle(AVLTreeNode node, ArrayList<AVLTreeNode> visited) {
        if (node == null) {
            return false;
        }

        if (visited.contains(node)) {
            return true;
        } else {
            visited.add(node);
        }

        return hasCircle(node.getLeft(), visited) ||  hasCircle(node.getRight(), visited);
    }

    public void insert(int key) {
        AVLTreeNode node = new AVLTreeNode(key);
        if (root == null) {
            root = node;
        } else {
            root.insert(node);
            if (!validAVL()) {
                setRoot(balance(root));
            }
        }
    }

    private void updateBalance(AVLTreeNode node) {
        int leftHeight = node.getLeft() != null ? node.getLeft().height() : -1;
        int rightHeight = node.getRight() != null ? node.getRight().height() : -1;
        node.setBalance(rightHeight - leftHeight);
    }

    private AVLTreeNode balance(AVLTreeNode node) {
        if (node.getBalance() < -1) {
            if (node.getLeft().getBalance() > 0) {
                node.setLeft(rotateLeft(node.getLeft()));
            }
            return rotateRight(node);
        } else if (node.getBalance() > 1) {
            if (node.getRight().getBalance() < 0) {
                node.setRight(rotateRight(node.getRight()));
            }
            return rotateLeft(node);
        }

        return node;
    }

    private AVLTreeNode rotateLeft(AVLTreeNode node) {
        AVLTreeNode newRoot = node.getRight();
        node.setRight(newRoot.getLeft());
        newRoot.setLeft(node);

        updateBalance(node);
        updateBalance(newRoot);

        return newRoot;
    }

    private AVLTreeNode rotateRight(AVLTreeNode node) {
        AVLTreeNode newRoot = node.getLeft();
        node.setLeft(newRoot.getRight());
        newRoot.setRight(node);

        updateBalance(node);
        updateBalance(newRoot);

        return newRoot;
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

        AVLTree avlTree2 = new AVLTree();
        AVLTreeNode one2 = new AVLTreeNode(10);
        avlTree2.setRoot(one2);
        AVLTreeNode two2 = new AVLTreeNode(12);
        one2.setRight(two2);
        AVLTreeNode three2 = new AVLTreeNode(8);
        one2.setLeft(three2);
        AVLTreeNode four2 = new AVLTreeNode(9);
        three2.setRight(four2);
        AVLTreeNode five2 = new AVLTreeNode(7);
        three2.setLeft(five2);
        one2.setBalance(0);
        two2.setBalance(-1);
        three2.setBalance(1);
        four2.setBalance(0);
        five2.setBalance(0);

        System.out.println(avlTree2.dot());
        System.out.println(avlTree2.validAVL()); // Expected output: false (saved balance miss-match)

        AVLTree avlTree3 = new AVLTree();
        AVLTreeNode one3 = new AVLTreeNode(10);
        avlTree3.setRoot(one3);
        AVLTreeNode two3 = new AVLTreeNode(12);
        one3.setRight(two3);
        AVLTreeNode three3 = new AVLTreeNode(8);
        one3.setLeft(three3);
        AVLTreeNode four3 = new AVLTreeNode(9);
        three3.setRight(four3);
        AVLTreeNode five3 = new AVLTreeNode(7);
        three3.setLeft(five3);
        four3.setLeft(five3); // Create a circle by setting the left child of 'four3' to 'five3'
        one3.setBalance(0);
        two3.setBalance(-1);
        three3.setBalance(1);
        four3.setBalance(0);
        five3.setBalance(0);

        System.out.println(avlTree3.dot());
        System.out.println(avlTree3.validAVL()); // Expected output: false (invalid AVL tree with circle)

        /*AVLTree avlTree4 = new AVLTree();
        AVLTreeNode one4 = new AVLTreeNode(10);
        avlTree4.setRoot(one4);
        AVLTreeNode two4 = new AVLTreeNode(12);
        one4.setRight(two4);
        AVLTreeNode three4 = new AVLTreeNode(8);
        one4.setLeft(three4);
        AVLTreeNode four4 = new AVLTreeNode(9);
        three4.setRight(four4);
        AVLTreeNode five4 = new AVLTreeNode(7);
        three4.setLeft(five4);
        four4.setRight(one4); // Create a circle by setting the right child of 'four4' to 'one4'
        one4.setBalance(-1);
        two4.setBalance(0);
        three4.setBalance(1);
        four4.setBalance(1);
        five4.setBalance(0);

        System.out.println(avlTree4.dot());
        System.out.println(avlTree4.validAVL()); // Expected output: false (invalid AVL tree with circle)*/

        AVLTree avlTree5 = new AVLTree();
        AVLTreeNode one5 = new AVLTreeNode(10);
        avlTree5.setRoot(one5);
        AVLTreeNode two5 = new AVLTreeNode(11);
        one5.setRight(two5);
        AVLTreeNode three5 = new AVLTreeNode(9);
        one5.setLeft(three5);
        AVLTreeNode four5 = new AVLTreeNode(10);
        three5.setRight(four5);
        AVLTreeNode five5 = new AVLTreeNode(9);
        three5.setLeft(five5);
        AVLTreeNode six5 = new AVLTreeNode(10);
        two5.setLeft(six5);
        one5.setBalance(0);
        two5.setBalance(-1);
        three5.setBalance(0);
        four5.setBalance(0);
        five5.setBalance(0);
        six5.setBalance(0);

        System.out.println(avlTree5.dot());
        System.out.println(avlTree5.validAVL()); // Expected output: true
    }
}