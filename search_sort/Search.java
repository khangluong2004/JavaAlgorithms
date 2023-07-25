package algorithms.search_sort;

class Search {
    static int binarySearch(int[] sorted_arr, int start, int end, int elem){
        int mid;
        while (start <= end){
            mid = start + (end - start)/2;
            if (sorted_arr[mid] == elem){
                return mid;
            }
            if (sorted_arr[mid] > elem){
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        return -1;
    }

    static int linearSearch(int[] arr, int start, int end, int elem){
        for (int i=start; i <= end; i++){
            if (arr[i] == elem){
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args){
        int[] test = {9, 8, 2, 3, 4, 7, 10, 11, 15, 21, 30};

        System.out.println("Binary Search O(log n):");
        long start = System.nanoTime();
        System.out.println(binarySearch(test, 0, test.length, 30));
        long end = System.nanoTime();
        System.out.println("Time: " + (end - start)/ 1000000L + " ms\n");

        System.out.println("Linear Search: O(n)");
        start = System.nanoTime();
        System.out.println(linearSearch(test, 0, test.length, 30));
        end = System.nanoTime();
        System.out.println("Time: " + (end - start)/ 1000000L + " ms\n");
    }    
}

