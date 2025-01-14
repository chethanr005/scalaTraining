package com.chethan.dataStructureAlgorithms.dataStructures.bigO;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by Chethan on Dec 03, 2024.
 */

public class BinarySearchTree {
    Node root;

    public BinarySearchTree(int value) {
        Node newNode = new Node(value);
        root = newNode;
    }

    public BinarySearchTree() {
    }


    class Node {
        int  value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
        }
    }


    public Node getRoot() {
        return root;
    }

    public boolean insert(int value) {
        Node newNode = new Node(value);
        if (root == null) root = newNode;
        else {
            Node temp = root;
            while (temp != null) {
                if (newNode.value > temp.value) {
                    if (temp.right == null) {
                        temp.right = newNode;
                        temp = newNode;
                    }
                    temp = temp.right;
                } else if (newNode.value < temp.value) {
                    if (temp.left == null) {
                        temp.left = newNode;
                        temp = newNode;
                    }
                    temp = temp.left;
                } else return false;
            }
        }
        return true;
    }

    public boolean contains(int value) {
        if (root == null) return false;
        else {
            Node temp = root;
            while (temp != null) {
                if (temp.value == value) return true;
                else if (value > temp.value) temp = temp.right;
                else if (value < temp.value) temp = temp.left;
            }
            return false;
        }
    }

    public boolean rContains(int value) {
        if (root == null) return false;
        else return rContains(root, value);

    }

    private boolean rContains(Node root, int value) {
        if (value == root.value) return true;
        else if (value <= root.value) return rContains(root.left, value);
        else return rContains(root.right, value);


    }

    public Node rInsert(Node root, int value) {
        if (root == null) return new Node(value);
        else if (value <= root.value) root.left = rInsert(root.left, value);
        else root.right = rInsert(root.right, value);
        return root;
    }

    public int minValue(Node node) {
        if (root == null) return 0;
        Node baseNode = navigate(root, node);
        return findMin(baseNode);
    }

    public int findMin(Node node) {
        if (node.left == null) return node.value;
        else return findMin(node.left);
    }

    public Node navigate(Node current, Node node) {
        if (node.value == current.value) return current;
        else if (node.value <= current.value && current.left != null) return navigate(current.left, node);
        else if (node.value > current.value && current.right != null) return navigate(current.right, node);
        else return current;

    }

    public ArrayList<Integer> BFS() {
        ArrayList<Integer> result = new ArrayList<>();
        ArrayList<Node>    temp   = new ArrayList<>();
        if (root == null) return result;
        else {
            temp.add(root);
            helpBFS(temp, result);
        }
        return result;
    }

    public void helpBFS(ArrayList<Node> temp, ArrayList<Integer> result) {
        if (temp.isEmpty()) return;
        else {
            ArrayList<Node> newTemp = new ArrayList<>();
            for (Node node : temp) {
                result.add(node.value);
                if (node.left != null) newTemp.add(node.left);
                if (node.right != null) newTemp.add(node.right);
            }
            helpBFS(newTemp, result);
        }
    }

    public ArrayList<Integer> DFSPreOrder() {
        ArrayList<Integer> result = new ArrayList<>();
        if (root == null) return result;
        else helpDFSPreOrder(result, root);
        return result;
    }

    public void helpDFSPreOrder(ArrayList<Integer> result, Node node) {
        result.add(node.value);
        if (node.left != null) helpDFSPreOrder(result, node.left);
        if (node.right != null) helpDFSPreOrder(result, node.right);
    }

    public ArrayList<Integer> DFSInOrder() {
        ArrayList<Integer> result = new ArrayList<>();
        if (root == null) return result;
        else helpDFSInOrder(result, root);
        return result;
    }

    public void helpDFSInOrder(ArrayList<Integer> result, Node node) {
        if (node.left != null) helpDFSInOrder(result, node.left);
        result.add(node.value);
        if (node.right != null) helpDFSInOrder(result, node.right);
    }


    public boolean isValidBST() {
        if (root != null) return isValid(root);
        return true;
    }

    public boolean isValid(Node node) {
        if (node.left != null) {
            if (!(node.left.value < node.value)) return false;
            isValid(node.left);
        }
        if (node.right != null) {
            if (!(node.right.value > node.value)) return false;
            isValid(node.right);
        }
        return true;
    }

    public Integer kthSmallest(int k) {
        Stack<Integer> stack = new Stack<>();
        if (root != null && k > 0) {
            navigate(root, k, stack);
            if (stack.size() == k) return stack.pop();
        }
        return null;
    }

    public void navigate(Node node, int k, Stack<Integer> stack) {
        if (node.left != null) navigate(node.left, k, stack);
        if (k == stack.size()) return;
        else stack.push(node.value);
        if (node.right != null) navigate(node.right, k, stack);
    }


    public static void main(String[] args) {
        BinarySearchTree binarySearchTree = new BinarySearchTree(69);
        System.out.println(binarySearchTree.insert(35));
        System.out.println(binarySearchTree.insert(77));
        System.out.println(binarySearchTree.insert(40));
        System.out.println(binarySearchTree.insert(17));
        System.out.println(binarySearchTree.insert(70));
        System.out.println(binarySearchTree.insert(81));
//        System.out.println(binarySearchTree.insert(56));
//        ArrayList<Integer> bfs = binarySearchTree.BFS();
//        System.out.println(binarySearchTree.isValidBST());
        System.out.println(binarySearchTree.kthSmallest(8));
    }

}
