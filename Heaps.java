import java.util.Scanner;
import java.util.Comparator;

/**
 * PA 3 Class.
 */
public class Heaps {

    /**
     * Heap sort.
     * @param values T[]
     * @param comp comparator
     * @param <T> Generic
     */
    public static <T> void heapsort(T[] values, Comparator<T> comp) {
        PriorityTree<T> tree = new PriorityTree<>(comp, 2);
        for (T element : values)
        {
            tree.add(element);
        }
    }

    /**
     * Program.
     * @param arg args
     */
    public static void main(String[] arg) {

        Comparator<Integer> revComp = new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                return o2.compareTo(o1);
            }
        };

        Comparator<Integer> compToUse = null;
        String usage = "Usage: java Heaps {min|max}";
        if(arg.length == 1) {
            if(arg[0].equals("max")) {
                compToUse = revComp;
            }
            else if(arg[0].equals("min")) {
                compToUse = new Comparator<Integer>() {
                    public int compare(Integer o1, Integer o2) {
                        return o1.compareTo(o2);
                    }
                };
            }
            else {
                System.out.println(usage);
                return;
            }
        }
        else {
            System.out.println(usage);
            return;
        }

        PriorityTree<Integer> intTree = new PriorityTree<>(compToUse, 3);
        Scanner keyboard = null;
        try {
            keyboard = new Scanner(System.in);
        }
        catch(Exception e) {
            System.out.println("Error connecting to keyboard input:\n"+e.toString());
            return;
        }

        int choice = 4;
        String choiceString = "";
        do {
            System.out.print("\n1. Enqueue a number\n"
                    +"2. Dequeue a number\n"
                    +"3. Sort a list of numbers\n"
                    +"4. Exit\n\n");

            choice = forceIntChoice(keyboard, "Enter your choice: ", 1, 4);

            switch(choice) {
                case 1:
                    int numToAdd = forceIntChoice(keyboard, "Enter a number to add: ", Integer.MIN_VALUE, Integer.MAX_VALUE);
                    intTree.add(numToAdd);
                    break;
                case 2:
                    System.out.println("Removed: " + intTree.poll());
                    break;
                case 3:
                    System.out.print("Enter numbers to sort, separated by spaces: ");
                    try {
                        String numberString = keyboard.nextLine();
                        String[] valueStrings = numberString.split(" ");
                        Integer[] values = new Integer[valueStrings.length];
                        for(int i = 0; i < valueStrings.length; i++) {
                            values[i] = Integer.parseInt(valueStrings[i]);
                        }
                        Heaps.heapsort(values, revComp);
                        System.out.print("Sorted: ");
                        for(Integer value : values) System.out.print(value + " ");
                        System.out.println();
                    }
                    catch(Exception e) {
                        System.out.println("Error: one or more values was not a valid integer.");
                        System.out.println(e.toString());
                        e.printStackTrace();
                    }
                    break;
            }

            if(choice < 3) {
                System.out.println("\nCurrent tree (height " + intTree.height() + ")");
                System.out.println("Level order walk: " + intTree.toString());
                System.out.println("Pre order walk:   " + intTree.toStringPreOrder());
                System.out.println("Post order walk:  " + intTree.toStringPostOrder());
                //System.out.println("Level Order with Level Breaks:\n" + intTree.toStringWithLevels());
            }
        }
        while(choice != 4);
    }

    /**
     * Force int choice.
     * @param input input
     * @param prompt prompt
     * @param min min
     * @param max max
     * @return int
     */
    private static int forceIntChoice(Scanner input, String prompt, int min, int max) {
        int choice = -1;
        while(choice == -1) {
            try {
                System.out.print(prompt);
                choice = Integer.parseInt(input.nextLine());
                if(choice >= min && choice <= max) {
                    return choice;
                }
                System.out.println("You must enter an integer between "+min+" and "+max+ "");
            }
            catch(RuntimeException e) { }
            System.out.println("You must enter a valid integer.");
        }
        return choice;
    }
}