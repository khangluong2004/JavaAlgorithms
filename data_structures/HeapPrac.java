package algorithms.data_structures;
import java.util.Collections;
import java.util.PriorityQueue;


public class HeapPrac {
    public static void main(String[] args){
        // Heap: A complete binary tree, such that parent node > (or <) than child node
        // Stored using an array/ queue with parent node at i-index, the children at: 2i + 1 and 2i + 2;
        // The first/ root value is a max/ min value of a tree
        // Used for problem which needs finding/ removing maximum/ minimum value each loop, or insertion
        // concerns with removing root node.

        // Complexity: Insertion & removing root (O(log n)) , Contains (O(n)), peek (O(1))
        // Default capacity: 11

        PriorityQueue<Integer> minHeap = new PriorityQueue<>(11, null);
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(11, Collections.reverseOrder());

        // Insertion (Enqueue)
        for (int i=0; i < 10; i++){
            minHeap.add(i);
            maxHeap.add(i); 
        }
        System.out.println(minHeap);
        System.out.println(maxHeap);
        
        // Peek (Look at root but not remove)
        System.out.println(minHeap.peek());
        System.out.println(maxHeap.peek());

        // Removing root (Dequeue)
        System.out.println(minHeap.poll());
        System.out.println(maxHeap.poll());
        System.out.println(minHeap);
        System.out.println(maxHeap);

    }
}
