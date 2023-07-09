package algorithms.data_structures;
import java.util.Stack;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class StackQueue {
    public static void queue_practice(){
        // Queue is an interface, so requires an implementation
        // Use LinkedList, PriorityQueue (Heap) or PriorityBlockingQueue as implementation

        System.out.println("QUEUE");
        Queue<Integer> queue = new LinkedList<>();

        // Check empty
        System.out.println("Empty :" + queue.isEmpty());

        // Add O(1)
        queue.add(12);
        System.out.println("Add: " + queue);

        // Remove O(1): Doesn't return the value
        queue.remove(12);
        System.out.println("Remove: " + queue);

        queue.add(11);
        queue.add(15);
        queue.add(17);

        // Peek O(1)
        System.out.println("Peek: " + queue.peek());

        // Poll O(1)
        System.out.println("Poll: " + queue.poll());
        System.out.println(queue);

        // Iterating
        Iterator<Integer> iter = queue.iterator();
        while (iter.hasNext()){
            System.out.println(iter.next());
        }

    }

    public static void stack_practice(){
        // Stack: FILO data structure, built-in class
        // Nothing special, could be implemented with LinkedList
        // Note: Both insertion and removal takes O(1), so
        // can't use ArrayList

        System.out.println("STACK");
        Stack<Integer> stack = new Stack<Integer>();

        // Check empty
        System.out.println("Empty: " + stack.empty());

        // Push
        stack.push(10);
        System.out.println("Push: " + stack);

        // Peek at top, no removal
        System.out.println("Peek: " + stack.peek());

        // Pop
        Integer x = stack.pop();
        System.out.println("Pop: " + x);

        // Search: O(n)
        stack.push(999);
        stack.push(199);
        stack.push(299);
        System.out.println("Search: " + stack.search(999));

        // Iterating stack
        Iterator<Integer> val = stack.iterator();
        while (val.hasNext()){
            System.out.println(val.next());
        }
    }

    public static void main(String[] args) {
        stack_practice();
        System.out.println();
        queue_practice();
    }
}
