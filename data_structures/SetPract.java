package algorithms.data_structures;


import java.util.*;

public class SetPract {
    public Set<Integer> union(Set<Integer> A, Set<Integer> B){
        Set<Integer> U = new HashSet<Integer>(A);
        U.addAll(B);
        System.out.println("Union" + U);
        return U;
    }

    public Set<Integer> intersect(Set<Integer> A, Set<Integer> B){
        Set<Integer> U = new HashSet<Integer>(A);
        U.retainAll(B);
        System.out.println("Intersect" + U);
        return U;
    }

    public Set<Integer> difference(Set<Integer> A, Set<Integer> B){
        Set<Integer> U = new HashSet<Integer>(A);
        U.removeAll(B);
        System.out.println("Difference" + U);
        return U;
    }

    public static void main(String[] args) {
        // All implementations are of Set interface
        // No duplicate elements

        // Normal hash set - Completely unordered
        // Search, Insert, Remove: O(1)
        Set<Integer> hashPract = new HashSet<Integer>(Arrays.asList(1, 2, 3, 4, 9, 7, 5));
        System.out.println("Search: " + hashPract.contains(1));
        // Unordered
        hashPract.stream().forEach(System.out::println);

        // LinkedHashSet - Same as hashset, but
        // keep a record of elements ordered by their entrance (in a doubly linked list)
        Set<Integer> linkedHashPract = new LinkedHashSet<Integer>(Arrays.asList(1, 2, 3, 14, 29, 37, 5));
        System.out.println("Search: " + linkedHashPract.contains(1));
        // Order of enter
        linkedHashPract.stream().forEach(System.out::println);

        // TreeSet - Maintain a red-black tree underneath
        // Search, Insert, Remove: O(log n)
        // But all elements are sorted
        Set<Integer> treeSetPract = new TreeSet<Integer>(Collections.reverseOrder());
        System.out.println("Search: " + treeSetPract.contains(1));
        treeSetPract.addAll(Arrays.asList(1, 2, 3, 54, 59, 57, 5));
        // Order from largest to smallest
        treeSetPract.stream().forEach(System.out::println);

    }
}
