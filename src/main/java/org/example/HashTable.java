package org.example;

public class HashTable {

    private class Entry {
        int key;
        Entry next;

        Entry(int key, Entry next) {
            this.key = key;
            this.next = next;
        }
    }

    private Entry[] table = new Entry[16];
    private int size = 0;

    private int hash(int key) {
        return Math.abs(key % table.length);
    }

    public void insert(int key) {
        int i = hash(key);
        for (Entry e = table[i]; e != null; e = e.next) {
            if (e.key == key) return; // уже есть
        }
        table[i] = new Entry(key, table[i]); // добавляем в начало списка
        size++;
        if (size > table.length * 0.75) {
            resize();
        }
    }

    public boolean search(int key) {
        int i = hash(key);
        for (Entry e = table[i]; e != null; e = e.next) {
            if (e.key == key) return true;
        }
        return false;
    }

    public boolean delete(int key) {
        int i = hash(key);
        Entry prev = null;
        for (Entry e = table[i]; e != null; e = e.next) {
            if (e.key == key) {
                if (prev == null) table[i] = e.next;
                else prev.next = e.next;
                size--;
                return true;
            }
            prev = e;
        }
        return false;
    }

    private void resize() {
        Entry[] old = table;
        table = new Entry[old.length * 2];
        for (Entry head : old) {
            for (Entry e = head; e != null; e = e.next) {
                int i = hash(e.key);
                table[i] = new Entry(e.key, table[i]);
            }
        }
    }
}