package org.example;

public class BST {

    private class Node {
        int key;
        Node left, right;

        Node(int key) {
            this.key = key;
        }
    }

    private Node root;

    public void insert(int key) {
        Node newNode = new Node(key);
        if (root == null) {
            root = newNode;
            return;
        }
        Node cur = root;
        while (true) {
            if (key < cur.key) {
                if (cur.left == null) {
                    cur.left = newNode;
                    return;
                }
                cur = cur.left;
            } else if (key > cur.key) {
                if (cur.right == null) {
                    cur.right = newNode;
                    return;
                }
                cur = cur.right;
            } else {
                return;
            }
        }
    }

    public boolean search(int key) {
        Node cur = root;
        while (cur != null) {
            if (key == cur.key) return true;
            if (key < cur.key) cur = cur.left;
            else cur = cur.right;
        }
        return false;
    }

    public boolean delete(int key) {
        Node parent = null;
        Node cur = root;
        while (cur != null && cur.key != key) {
            parent = cur;
            if (key < cur.key) cur = cur.left;
            else cur = cur.right;
        }
        if (cur == null) return false; // ключа нет

        if (cur.left != null && cur.right != null) {
            Node succParent = cur;
            Node succ = cur.right;
            while (succ.left != null) {
                succParent = succ;
                succ = succ.left;
            }
            cur.key = succ.key;
            parent = succParent;
            cur = succ;
        }

        Node child = (cur.left != null) ? cur.left : cur.right;
        if (parent == null) root = child;
        else if (parent.left == cur) parent.left = child;
        else parent.right = child;
        return true;
    }
}