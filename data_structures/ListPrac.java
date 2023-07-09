package algorithms.data_structures;

import java.util.ArrayList;
import java.util.LinkedList;

public class ListPrac {
    // 2 types supported: ArrayList & LinkedList

    // ArrayList: Resizable, contiguous, random-access list
    // Time complexity: Insertion/ Add (O(n)), Get/Set (O(1)), Remove by Value/ Index (O(n), 
    // but O(1) if removed at the end)
    
    public static void testArrayList(){
        ArrayList<Integer> test = new ArrayList<Integer>();
        test.add(10);
        System.out.println(test);

        System.out.println(test.get(0));
        test.set(0, 11);
        System.out.println(test.remove(0));
        
        test.add(0, 10);
        System.out.println(test);
    }

    // LinkedList: LinkedList :)
    // Time complexity: Insertion/ Add/ Remove at the first/last position (O(1)), Get/Set (O(n)), 
    // Remove by value/ index (O(n))
    // Same interface as ArrayList (List interface)
    public static void testLinkedList(){
        LinkedList<Integer> test = new LinkedList<Integer>();
        test.addFirst(10);
        test.addLast(99);
        System.out.println(test);

        System.out.println(test.get(0));
        test.set(0, 11);
        System.out.println(test.removeFirst());
        
        test.add(0, 10);
        System.out.println(test);
    }

    // Check
    public static void main(String[] args) {
        System.out.println("ArrayList");
        testArrayList();
        System.out.println("LinkedList");
        testLinkedList();
    }
}
