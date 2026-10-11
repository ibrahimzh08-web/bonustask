# BST vs Hash Table: Performance Comparison

Java implementation and comparison of a Binary Search Tree (BST) and a Hash Table.

## Files
- `src/main/java/org/example/BST.java` - unbalanced binary search tree
- `src/main/java/org/example/HashTable.java` - hash table with separate chaining and resizing
- `src/main/java/org/example/Main.java` - benchmark (100, 1,000, 10,000 elements)

## How to run
Open the project in IntelliJ IDEA and run `Main`.

## Implementation
**BST.** Each node has a key and links to left and right children. Smaller keys go left, larger keys go right. Insert, search and delete walk down one path from the root. Delete handles three cases: leaf, one child, two children (replace with the in-order successor).

**Hash Table.** An array of buckets; each bucket is a linked list (separate chaining). The bucket index is `key % capacity`. When the load factor (size / capacity) exceeds 0.75, the array doubles and all elements are re-inserted.

## Complexity analysis

| Operation | BST best / average | BST worst | Hash Table best / average | Hash Table worst |
|---|---|---|---|---|
| Insert | O(log n) | O(n) | O(1) amortized | O(n) |
| Search | O(1) (key in root) / O(log n) | O(n) | O(1) | O(n) |
| Delete | O(log n) | O(n) | O(1) | O(n) |

- BST cost depends on the tree height h. Random keys give h ≈ log n. Sorted keys make the tree a linked list with h = n (worst case).
- Hash table is O(1) on average when keys are spread evenly. Worst case O(n) happens when all keys fall into one bucket. A resize costs O(n) but is rare, so insert is O(1) amortized.

## Experimental results
Keys 0..n-1, total time for n operations, in milliseconds. 5 warm-up runs before measuring.

### Random order
| n | Structure | Insert | Search | Delete |
|---|---|---|---|---|
| 100 | BST | 0.0031 | 0.0031 | 0.0031 |
| 100 | Hash Table | 0.0026 | 0.0002 | 0.0002 |
| 1,000 | BST | 0.0870 | 0.0677 | 0.0491 |
| 1,000 | Hash Table | 0.0570 | 0.0024 | 0.0034 |
| 10,000 | BST | 1.2482 | 0.9443 | 0.8607 |
| 10,000 | Hash Table | 0.4831 | 0.3290 | 0.1098 |

### Sorted order (worst case for BST)
| n | Structure | Insert | Search | Delete |
|---|---|---|---|---|
| 100 | BST | 0.0144 | 0.0130 | 0.0049 |
| 100 | Hash Table | 0.0071 | 0.0045 | 0.0048 |
| 1,000 | BST | 0.9325 | 0.6822 | 0.0042 |
| 1,000 | Hash Table | 0.0366 | 0.0038 | 0.0058 |
| 10,000 | BST | 72.9938 | 78.4319 | 0.0840 |
| 10,000 | Hash Table | 0.3462 | 0.2226 | 0.1002 |

## Comparison
- For 100 elements the difference is very small.
- With random keys the hash table is faster, and the gap grows with n.
- With sorted keys the BST degenerates into a list: when n grows 10 times (1,000 → 10,000), BST insert/search time grows about 80-115 times, which matches O(n) per operation (O(n²) in total). At 10,000 elements BST search is about 350 times slower than the hash table.
- BST delete on sorted keys is fast because keys are deleted in increasing order, so the smallest key is always the root.
- The hash table gives almost the same time for random and sorted keys.

## Why performance differs in practice
1. **Input order**: an unbalanced BST depends on the order of keys; sorted data is the worst case.
2. **Constant factors**: hashing and resizing take time, so for small n the difference is small.
3. **Memory / cache**: BST follows many pointers to nodes spread in memory; a hash table goes almost directly to the right bucket.
4. **JVM effects**: JIT compilation and garbage collection make times noisy, so warm-up runs were used.
5. **Features**: a BST keeps keys sorted (min/max, range queries); a hash table cannot.

## Conclusions
- For insert / search / delete by key the hash table is faster and does not depend on input order.
- An unbalanced BST is fine on random data (O(log n)) but very slow on sorted data (O(n)).
- When sorted order is needed, a balanced tree (AVL, Red-Black, Java `TreeMap`) should be used.