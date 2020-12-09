import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import java.util.ArrayList;

/**
 * Main K tree class.
 * @param <E> Object
 */
public class SimpleKTree<E> implements Collection<E> {
    
    /**
     * Tree.
     */
    protected ArrayList<E> tree;
    /**
     * K value.
     */
    protected int kary;

    /**
     * K tree constructor.
     * @param k int
     */
    public SimpleKTree(int k) {
        //creates an empty k-ary tree
        //throws InvalidKException if the k value is invalid (< 2)
        tree = new ArrayList<>();
        this.kary = k;
    }

    /**
     * K tree constructor.
     * @param arrayTree E[]
     * @param k int
     */
    public SimpleKTree(E[] arrayTree, int k) {
        //creates a k-ary tree from an array
        //throws InvalidKException if the k value is invalid (< 2)
        if (k < 2)
            throw new InvalidKException();
        tree = new ArrayList<>();
        tree.addAll(Arrays.asList(arrayTree));
        this.kary = k;
        //NOTE: Assume null values are not allowed in the tree, so any elements
        //in the array are real elements and the array will not contain more space than
        //is needed for the nearly complete tree (so no nulls appearing anywhere).
    }

    /**
     * Height of tree.
     * @return Integer
     */
    public int height() {
        //returns the height of the k-ary tree
        double height = Math.pow(size() , 1.0 / kary);
        return (int) height;
    }

    /**
     * Clears tree.
     */
    public void clear() {
        //removes all the elements from the k-ary tree
        tree = new ArrayList<>();
    }

    /**
     * To String.
     * @return String
     */
    public String toString() {
        //creates a string representation of the current tree where each element
        //is separated by spaces

        //EXAMPLES:

        //The following is a k-ary tree of where k=2, height=2
        //(2 and 3 are children of 1; 4 and 5 are children of 2; 6 and 7 are children of 3):
        //  "1 2 3 4 5 6 7 "
        //Note the space at the end is allowed, but not required, this is also ok:
        //  "1 2 3 4 5 6 7"

        //The following is a k-ary tree of where k=3, height=2
        //(2, 3, and 4 are children of 1; 5, 6, and 7 are children of 2; 8, 9, and 10 are children of 3):
        //  "1 2 3 4 5 6 7 8 9 10 "
        //Note the space at the end is allowed, but not required, this is also ok:
        //  "1 2 3 4 5 6 7 8 9 10"

        //NOTE: Any values not in the heap are not printed (no printing nulls for incomplete levels!)

        String stringTree = "";
        for (E element : tree)
        {
            stringTree += element + " ";
        }
        return stringTree;
    }

    /**
     * To String Pre ORder.
     * @return String
     */
    public String toStringPreOrder() {
        //prints out a pre-order walk of the tree

        //Examples for the k=2 and k=3 trees from toString():
        //    k=2:  "1 2 4 5 3 6 7 "
        //    k=3:  "1 2 5 6 7 3 8 9 10 4 "
        //Note the space at the end is allowed, but not required,
        //so for k=2 this is also ok: "1 2 4 5 3 6 7"

        //NOTE: Any values not in the heap are not printed (no printing nulls for incomplete levels!)

        return preOrderTraverse(0);
    }

    /**
     * To String  Post order.
     * @return String
     */
    public String toStringPostOrder() {
        //prints out a post-order walk of the tree
        //  "1 2 3 4 5 6 7 8 9 10 "

        //Examples for the k=2 and k=3 trees from toString():
        //    k=2:  "4 5 2 6 7 3 1 "
        //    k=3:  "5 6 7 2 8 9 10 3 4 1 "
        //Note the space at the end is allowed, but not required,
        //so for k=2 this is also ok: "4 5 2 6 7 3 1"

        //NOTE: Any values not in the heap are not printed (no printing nulls for incomplete levels!)
        return postOrderTraverse(0);
    }

    /**
     * Returns the number of elements in this collection.  If this collection
     * contains more than {@code Integer.MAX_VALUE} elements, returns
     * {@code Integer.MAX_VALUE}.
     *
     * @return the number of elements in this collection
     */
    public int size() {
        return tree.size();
    }

    /**
     * Returns {@code true} if this collection contains no elements.
     *
     * @return {@code true} if this collection contains no elements
     */
    public boolean isEmpty() {
        if (tree == null)
            return true;
        else
            return false;
    }

    /**
     * To String.
     * @param index Index
     * @return String
     */
    private String preOrderTraverse(int index)
    {
        String toString = "";
        if (index < size())
        {
            toString += tree.get(index) + " ";
            for (int i = 1; i <= kary; i++)
                toString += preOrderTraverse((kary * index + i));
        }
        return toString;
    }

    /**
     * To STring.
     * @param index Index
     * @return String
     */
    private String postOrderTraverse(int index)
    {
        String toString = "";
        if (index < size())
        {
            for (int i = 1; i <= kary; i++)
                toString += postOrderTraverse((kary * index + i));
            toString += tree.get(index) + " ";
        }
        return toString;
    }

    /**
     * To String.
     * @return String representation
     */
    public String toStringWithLevels() {
        //creates a string representation of the current tree with line breaks
        //after each level of the tree

        //Examples for the k=2 and k=3 trees from toString():

        //k=2:
        //  1
        //  2 3
        //  4 5 6 7
        //In string form, that is: "1 \n2 3 \n4 5 6 7 "
        //(a space after each element, a \n for each new level), the space at the end is optional

        //k=3:
        //  1
        //  2 3 4
        //  5 6 7 8 9 10
        //In string form, that is: "1 \n2 3 4 \n5 6 7 8 9 10 "
        //(a space after each element, a \n for each new level), the space at the end is optional

        //NOTE: Any values not in the heap are not printed (no printing nulls for incomplete levels!)

        //HINT 1: Again, heaps are already in level order, so for this you just need to determine
        //when you're at a new level and add a line break.

        //Hint 2: If you know how to get the height of a nearly complete tree of
        //a given size... you can find when items are on the next "level"
        //in the same way in O(1) time.
        return null;
    }

    //********************************************************************************
    // Testing code... edit this as much as you want!
    //********************************************************************************

    /**
     * Main testing class.
     * @param arg arg
     */
    public static void main(String[] arg) {
        SimpleKTree<Integer> tree = new SimpleKTree<>(new Integer[] {100, 300, 400, 500, 600, 600, 650}, 2);
        System.out.print(tree.toString());
        System.out.print(tree.toStringPreOrder());
        System.out.print(tree.toStringPostOrder());

        /**
        * Testing class.
        */
        class Banana { }
		
		int x;
		String s;
		boolean b;
		SimpleKTree<Integer> tree1;
		SimpleKTree<Banana> tree2;
		
		tree1 = new SimpleKTree<>(new Integer[] {1, 2, 3}, 3);
		tree2 = new SimpleKTree<>(100);
		x = tree1.height();
		tree1.clear();
		s = tree1.toString();
		s = tree1.toStringPreOrder();
		s = tree1.toStringPostOrder();
		s = tree1.toStringWithLevels();
		x = tree1.size();
		b = tree1.isEmpty();

    }

    /**
     * Exception Class.
     */
    public static class InvalidKException extends RuntimeException {};

    /**
     * Interface method.
     * @param e object
     * @return Exception
     */
    public boolean add(E e) {
        throw new UnsupportedOperationException();
    }

    /**
     * Interface method.
     * @param c collcetion
     * @return Exception
     */
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * Interface method.
     * @param o Object
     * @return Exception
     */
    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }

    /**
     * Interface method.
     * @param c colletion
     * @return Exception
     */
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * Interface method.
     * @param o Object
     * @return Exception
     */
    public boolean equals(Object o) {
        throw new UnsupportedOperationException();
    }

    /**
     * Interface method.
     * @return Exception
     */
    public int hashCode() {
        throw new UnsupportedOperationException();
    }

    /**
     * Interface method.
     * @return Exception
     */
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    /**
     * Interface method.
     * @param o Object
     * @return Exception
     */
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    /**
     * Interface method.
     * @param c collection
     * @return Exception
     */
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * Interface method.
     * @param c collection
     * @return Exception
     */
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * Interface method.
     * @return Exception
     */
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    /**
     * Interface method.
     * @param a Array
     * @param <T> Generic Type
     * @return Exception
     */
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }
}