import java.util.*;

/**
 * Priority Tree class.
 * @param <E> Object
 */
public class PriorityTree<E> extends SimpleKTree<E> implements Queue<E> {
    /**
     * Comparator.
     */
    private Comparator<? super E> comp;

    /**
     * PriorityTree constructor.
     * @param comp comparator
     * @param k int
     */
    public PriorityTree(Comparator<? super E> comp, int k) {
        super(k);
        this.comp = comp;
    }

    /**
     * PriorityTree constructor.
     * @param comp comparator
     * @param arrayTree array
     * @param k int
     */
    public PriorityTree(Comparator<? super E> comp, E[] arrayTree, int k) {
        super(-1);
    }

    @Override
    public boolean offer(E e) {
        return add(e);
    }

    @Override
    public E remove() {
        if (tree.isEmpty())
            throw new NoSuchElementException();
        else
        {
            E oldHead = tree.get(0);
            E head = tree.get(size() - 1);
            tree.remove(size() - 1);
            if (!tree.isEmpty())
            {
                tree.set(0, head);
                int index = 0;
                int childIndex = getTopChild(index);
                while (comp.compare(tree.get(childIndex), head) < 0)
                {
                    Collections.swap(tree, index, childIndex);
                    index = childIndex;
                    head = tree.get(index);
                    childIndex = getTopChild(index);
                }
            }
            return oldHead;
        }
    }

    @Override
    public E poll() {
        if (tree.isEmpty())
            return null;
        else
            return remove();
    }

    @Override
    public E element() {
        if (tree.isEmpty())
            throw new NoSuchElementException();
        else
            return tree.get(0);
    }

    @Override
    public E peek() {
        return tree.get(0);
    }

    @Override
    public boolean add(E e) {
        if (size() == 0)
        {
            tree.add(e);
        }
        else
        {
            tree.add(e);
            int child = size() - 1;
            int parent = ((child - 1) / this.kary);
            while (comp.compare(e, tree.get(parent)) < 0)
            {
                Collections.swap(tree, parent, child);
                child = parent;
                parent = ((parent - 1) / this.kary);
            }
        }
        return true;
    }


    /**
     * Get top child helper function.
     * @param index Index
     * @return int
     */
    private int getTopChild(int index)
    {
        int topChildIndex = (kary * index + 1);
        if (topChildIndex > size() - 1)
            return index;
        else
        {
            E topChild = tree.get(topChildIndex);
            for (int i = 2; i <= kary; i++)
                if (size() > kary * index + i && comp.compare(tree.get(kary * index + i), topChild) < 0)
                {
                    topChildIndex = kary * index + i;
                    topChild = tree.get(topChildIndex);
                }
            return topChildIndex;
        }
    }

    /**
     * Testing method.
     * @param arg args
     */
    public static void main(String[] arg) {

        Comparator<Integer> comp1 = new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        };


        PriorityTree<Integer> priorityTree = new PriorityTree<>(comp1, 2);

        priorityTree.add(100);
        priorityTree.add(300);
        priorityTree.add(400);
        priorityTree.add(500);
        priorityTree.add(600);
        priorityTree.add(600);
        priorityTree.add(650);

        System.out.print(priorityTree.getTopChild(0));
        priorityTree.poll();
        priorityTree.add(1);
        priorityTree.add(2);
        priorityTree.add(3);
        priorityTree.add(1);
        priorityTree.add(3);
        priorityTree.add(-10);
        priorityTree.add(-1);
        priorityTree.remove();

        /**
        * Testing class.
        */
        class Banana { int size; }

		int x;
		String s;
		boolean b;
		Banana a;
		PriorityTree<Banana> tree1;
		PriorityTree<String> tree2;


		//comparator for bananas in size order
		Comparator<Banana> comp = new Comparator<Banana>() {
			public int compare(Banana o1, Banana o2) { return ((Integer)(o1.size)).compareTo(o2.size); }
		};

		//comparator for strings in reverse alphabetical order
		Comparator<String> revComp = new Comparator<String>() {
			public int compare(String o1, String o2) { return o2.compareTo(o1); }
		};


		tree1 = new PriorityTree<>(comp, 2);
		tree2 = new PriorityTree<>(revComp, new String[] {"a", "b", "c"}, 7);

		b = tree1.add(new Banana());
		b = tree1.offer(new Banana());
		a = tree1.element();
		a = tree1.peek();
		a = tree1.poll();
		a = tree1.remove();

		x = tree1.height();
		tree1.clear();
		s = tree1.toString();
		s = tree1.toStringPreOrder();
		s = tree1.toStringPostOrder();
		s = tree1.toStringWithLevels();
		x = tree1.size();
		b = tree1.isEmpty();

    }
}