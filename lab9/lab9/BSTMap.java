package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) {
            return null;
        }

        if (key.compareTo(p.key) == 0) {
            return p.value;
        } else if (key.compareTo(p.key) < 0) {
            return getHelper(key, p.left);
        } else {
            return getHelper(key, p.right);
        }
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            p = new Node(key, value);
            size += 1;
        } else if (key.compareTo(p.key) < 0) {
            p.left = putHelper(key, value, p.left);
        } else if (key.compareTo(p.key) > 0) {
            p.right = putHelper(key, value, p.right);
        } else {
            throw new IllegalArgumentException("key should not exist already");
        }
        return p;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        root = putHelper(key, value, root);
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    private void keySetHelper(Set<K> s, Node node) {
        if (node != null) {
            s.add(node.key);
            keySetHelper(s, node.left);
            keySetHelper(s, node.right);
        }
    }

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> s = new HashSet<>();
        keySetHelper(s, root);
        return s;
    }

    private void removeNode(Node parent, Node toRemove) {
        if (toRemove.left == null && toRemove.right == null) {
            if (parent == null) { // toRemove is root
                root = null;
            } else {
                // compare key to tell if toRemove is the left or right child of parent's
                if (toRemove.key.compareTo(parent.key) < 0) {
                    parent.left = null;
                } else {
                    parent.right = null;
                }
            }
        } else if (toRemove.left == null) {
            if (parent == null) {
                root = toRemove.right;
            } else {
                if (toRemove.key.compareTo(parent.key) < 0) {
                    parent.left = toRemove.right;
                } else {
                    parent.right = toRemove.right;
                }
            }
        } else if (toRemove.right == null) {
            if (parent == null) {
                root = toRemove.left;
            } else {
                if (toRemove.key.compareTo(parent.key) < 0) {
                    parent.left = toRemove.left;
                } else {
                    parent.right = toRemove.left;
                }
            }
        } else {
            // find leftmost node of right child's
            Node candidateParent = toRemove.right;
            if (candidateParent.left != null) {
                Node leftmost = candidateParent.left;
                while (leftmost.left != null) {
                    leftmost = leftmost.left;
                    candidateParent = candidateParent.left;
                }

                // easier replaced than removed actually
                toRemove.key = leftmost.key;
                toRemove.value = leftmost.value;
                candidateParent.left = leftmost.right;
            } else {
                toRemove.key = candidateParent.key;
                toRemove.value = candidateParent.value;
                toRemove.right = candidateParent.right;
            }
        }
    }

    private V removeHelper(K key, Node parent, Node current) {
        if (current == null) {
            return null;
        }

        V value;
        if (key.compareTo(current.key) == 0) {
            size -= 1;
            value = current.value;
            removeNode(parent, current);
        } else if (key.compareTo(current.key) < 0) {
            value = removeHelper(key, current, current.left);
        } else {
            value = removeHelper(key, current, current.right);
        }
        return value;
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        return removeHelper(key, null, root);
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        if (get(key) != value) {
            return null;
        }
        return remove(key);
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }
}
