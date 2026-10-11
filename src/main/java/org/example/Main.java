package org.example;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        int[] sizes = {100, 1000, 10000};

        for (int i = 0; i < 5; i++) {
            runTest(10000, false, false);
            runTest(10000, true, false);
        }

        System.out.println("=== RANDOM order ===");
        for (int n : sizes) runTest(n, false, true);

        System.out.println("=== SORTED order ===");
        for (int n : sizes) runTest(n, true, true);
    }

    static int[] makeKeys(int n, boolean sorted) {
        int[] keys = new int[n];
        for (int i = 0; i < n; i++) keys[i] = i;
        if (!sorted) {
            Random rnd = new Random(42);
            for (int i = n - 1; i > 0; i--) {
                int j = rnd.nextInt(i + 1);
                int t = keys[i];
                keys[i] = keys[j];
                keys[j] = t;
            }
        }
        return keys;
    }

    static void runTest(int n, boolean sorted, boolean print) {
        int[] keys = makeKeys(n, sorted);
        BST bst = new BST();
        HashTable ht = new HashTable();

        long start = System.nanoTime();
        for (int k : keys) bst.insert(k);
        long bstInsert = System.nanoTime() - start;

        start = System.nanoTime();
        for (int k : keys) bst.search(k);
        long bstSearch = System.nanoTime() - start;

        start = System.nanoTime();
        for (int k : keys) bst.delete(k);
        long bstDelete = System.nanoTime() - start;

        start = System.nanoTime();
        for (int k : keys) ht.insert(k);
        long htInsert = System.nanoTime() - start;

        start = System.nanoTime();
        for (int k : keys) ht.search(k);
        long htSearch = System.nanoTime() - start;

        start = System.nanoTime();
        for (int k : keys) ht.delete(k);
        long htDelete = System.nanoTime() - start;

        if (print) {
            System.out.printf("n=%-6d BST:       insert=%.4f ms  search=%.4f ms  delete=%.4f ms%n",
                    n, bstInsert / 1e6, bstSearch / 1e6, bstDelete / 1e6);
            System.out.printf("n=%-6d HashTable: insert=%.4f ms  search=%.4f ms  delete=%.4f ms%n",
                    n, htInsert / 1e6, htSearch / 1e6, htDelete / 1e6);
        }
    }
}