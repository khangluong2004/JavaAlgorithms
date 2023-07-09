package algorithms.search_sort;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.System;


public class Sort {
    public static int[] quickSort(int[] arr){
        // Recursion implement. Could be enhanced with Double pivot?
        // Stable sort. Time complexity: O(n log(n))
        if (arr.length <= 1){
            return arr;
        }

        int pivot = arr[arr.length / 2];
        ArrayList<Integer> lower = new ArrayList<Integer>();
        ArrayList<Integer> higher = new ArrayList<Integer>();

        for (int i = 0; i < arr.length; i++){
            if (i == arr.length/ 2){
                continue;
            }
            int elem = arr[i];
            if (elem < pivot){
                lower.add(elem);
            } else {
                higher.add(elem);
            }
        }

        int[] lower_arr = lower.stream().mapToInt(i -> i).toArray();
        int[] higher_arr = higher.stream().mapToInt(i -> i).toArray();

        lower_arr = quickSort(lower_arr);
        higher_arr = quickSort(higher_arr);

        int[] sorted = new int[arr.length];
        
        for (int i = 0; i < sorted.length; i++){
            if (i < lower_arr.length){
                sorted[i] = lower_arr[i];
                continue;
            }

            if (i == lower_arr.length){
                sorted[i] = pivot;
                continue;
            }

            sorted[i] = higher_arr[i - lower_arr.length - 1];
        }

        return sorted;

    }

    public static int[] countingSort(int[] arr, int start, int end){
        // Only used for when all elements in `arr` is within range [`start`, `end`]
        // Stable sort (extra steps shown for sorting objects. 
        // For int, generate output from counts directly)
        // Time complexity: O(n + k) sort (n: number of items in arr, k = start - end + 1)
        // Space complexity: O(n + k)

        int k = end - start + 1; // Range value
        int[] count = new int[k];
        int[] output = new int[arr.length];
        
        // Fill the count
        for (int i=0; i < arr.length; i++){
            count[arr[i] - start] += 1;
        }


        // Find cumulative counts for identifying index 
        // Used in insertion in next step
        for (int i=1; i < k; i++){
            count[i] += count[i - 1];
        }


        // Insert elements in reversed order
        // To preserve same order for same key
        for (int i=arr.length - 1; i >= 0; i--){
            int cur_elem = arr[i];
            output[count[cur_elem - start] - 1] = arr[i];
            count[cur_elem - start]--;
        }

        return output;
    }

    private static int[] countingSortForRadix(int[] arr, int start, int end, short bit_position){
        // Only used for when all elements in `arr` is within range [`start`, `end`]
        // Stable sort (extra steps shown for sorting objects. 
        // For int, generate output from counts directly)
        // bit_position: The bit used to sort for radix
        // Time complexity: O(n + k) sort (n: number of items in arr, k = start - end + 1)
        // Space complexity: O(n + k)

        int k = end - start + 1; // Range value
        int[] count = new int[k];
        int[] output = new int[arr.length];
        
        // Fill the count
        for (int i=0; i < arr.length; i++){
            int cur_elem = arr[i] >> bit_position & 1;
            count[cur_elem - start] += 1;
        }


        // Find cumulative counts for identifying index 
        // Used in insertion in next step
        for (int i=1; i < k; i++){
            count[i] += count[i - 1];
        }


        // Insert elements in reversed order
        // To preserve same order for same key
        for (int i=arr.length - 1; i >= 0; i--){
            int cur_elem = arr[i] >> bit_position & 1;
            output[count[cur_elem - start] - 1] = arr[i];
            count[cur_elem - start]--;
        }

        return output;
    }

    public static int[] radixSort(int[] arr){
        // int-type has 32 bits
        // Apply LSD (Least Significant Digit) radixSort by applying countingSort 
        // on each bit
        // Time complexity: O(n), Space complexity: O(n) for the counting sort

        for (short i=0; i < 32; i++){
            arr = countingSortForRadix(arr, 0, 1, i);
        }
        return arr;
    }

    public static void main(String[] args){
        int[] test = {9, 8, 2, 3, 4, 7, 10, 11, 15, 21, 30};

        System.out.println("Built-in sort:");
        int[] copy_test = test.clone();
        long start = System.nanoTime();
        Arrays.sort(copy_test);
        System.out.println(Arrays.toString(copy_test));
        long end = System.nanoTime();
        System.out.println("Time: " + (end - start)/1000000l + " ms\n");


        System.out.println("Quicksort:");
        start = System.nanoTime();
        System.out.println(Arrays.toString(quickSort(test)));
        end = System.nanoTime();
        System.out.println("Time: " + (end - start)/1000000l + " ms\n");

        System.out.println("Counting sort:");
        start = System.nanoTime();
        System.out.println(Arrays.toString(countingSort(test, 2, 30)));
        end = System.nanoTime();
        System.out.println("Time: " + (end - start)/1000000l + " ms\n");

        System.out.println("Radix sort:");
        start = System.nanoTime();
        System.out.println(Arrays.toString(radixSort(test)));
        end = System.nanoTime();
        System.out.println("Time: " + (end - start)/1000000l + " ms\n");
    }
}

