package algorithms.graph_theory;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

// Implementation of Directed graph
// Graph = Set of nodes + Set of edges (with weights)
// Each node is uniquely labelled from 0 to n;
// Each edge is represented by a pair of nodes & weight: [Node1, Node2, Weight]

// Other implementation: Adjacency matrix & Adjacency LinkedList

public class GraphHT {
    // Recommended: Easy to implement & quite efficient
    // Graphs as Hash table of hash table
    HashMap<Integer, HashMap<Integer, Integer>> graph = new HashMap<Integer, HashMap<Integer, Integer>>();
    int num_nodes = 0;

    public GraphHT(int[][] edges, int num_nodes){
        this.num_nodes = num_nodes;
        for (int i = 0; i < edges.length; i++){
            this.addEdge(edges[i]);
        }
    }

    public void addEdge(int[] edge){
        HashMap<Integer, Integer> curAdj = graph.get(edge[0]);
        if (curAdj == null){
            curAdj = new HashMap<Integer, Integer>();
            graph.put(edge[0], curAdj);
        }
        curAdj.put(edge[1], edge[2]);
    }

    // Helper functions to re-create travelled paths
    private void findPath(int[] parents, LinkedList<Integer> path, int start, int search_elem){
        path.addFirst(search_elem);
        int pre_elem = search_elem;
        int cur_parent;
        do {
            cur_parent = parents[pre_elem];
            path.addFirst(cur_parent);
            pre_elem = cur_parent;
        } while (cur_parent != start);
    }


    // BST: Breadth-first search: O(V + E)
    // Use a queue to search at each "level" from start
    // Used to find path with least edges

    // Search for a node from start, and return 
    // the number of edges needed to reach that node 
    // Return -1 if not found
    // Update in-place the path list between start and search_elem
    // If not found, leave empty

    public int BFS(int start, int search_elem, LinkedList<Integer> path){
        Queue<ArrayList<Integer>> queue = new LinkedList<ArrayList<Integer>>();
        // Store parents for back-tracking to find paths
        int[] parents = new int[num_nodes];
        Arrays.fill(parents, -1); 
        HashSet<Integer> visited = new HashSet<Integer>();

        ArrayList<Integer> queueInfo = new ArrayList<Integer>(3);
        queueInfo.add(start);
        queueInfo.add(0);
        queue.add(queueInfo);

        while (queue.size() != 0){
            // System.out.println(queue);
            ArrayList<Integer> top_elem = queue.remove();
            Set<Integer> neighbours = graph.get(top_elem.get(0)).keySet();
            for (Integer neighbour: neighbours){
                if (visited.contains(neighbour)){
                    continue;
                }
                
                // Parents pointer
                parents[neighbour] = top_elem.get(0);

                int cur_dist = 1 + top_elem.get(1);
                ArrayList<Integer> curInfo = new ArrayList<Integer>(3);
                if (neighbour == search_elem){
                    findPath(parents, path, start, search_elem);
                    return cur_dist;
                }
                curInfo.add(neighbour);
                curInfo.add(cur_dist);
                visited.add(neighbour);
                queue.add(curInfo);
            } 
        }

        return -1;
    }

    // DST: Depth-first Search: O(V + E)
    // Use a stack to go full-depth then backtrack
    // Used to check if a path exists, efficient for wide, shallow graph

    // Search for a path, return if found
    // Update inplace the found path
    public boolean DFS(int start, int search_elem, LinkedList<Integer> path){
        Stack<Integer> stack = new Stack<Integer>();
        // Store parents for back-tracking to find paths
        int[] parents = new int[num_nodes];
        Arrays.fill(parents, -1);
        HashSet<Integer> visited = new HashSet<Integer>(); 

        stack.push(start);
        while (!stack.empty()){
            int top_elem = stack.pop();
            Set<Integer> neighbours = graph.get(top_elem).keySet();
            for (int neighbour: neighbours){
                if (visited.contains(neighbour)){
                    continue;
                }

                visited.add(neighbour);
                parents[neighbour] = top_elem;
                if (neighbour == search_elem){
                    findPath(parents, path, start, search_elem);
                    return true;
                }

                stack.push(top_elem);
                stack.push(neighbour);
                break;
            }
        }

        return false;

    }

    

    public static void main(String[] args) {
        int[][] test_edges = {{0, 1, 5}, {1, 2, 3}, {2, 3, 4}, {1, 3, 5}, {3, 4, 9}, {4, 5, 8}};
        GraphHT testGraph = new GraphHT(test_edges, 6);

        // BFS
        LinkedList<Integer> BFS_path = new LinkedList<Integer>();
        long start = System.nanoTime();
        System.out.println("BFS test: " + testGraph.BFS(1, 5, BFS_path));
        System.out.println("BFS path: " + BFS_path);
        long end = System.nanoTime();
        System.out.println("Takes: " + (end - start) + " ns \n");

        // DFS
        LinkedList<Integer> DFS_path = new LinkedList<Integer>();
        start = System.nanoTime();
        System.out.println("DFS test: " + testGraph.DFS(1, 5, DFS_path));
        System.out.println("DFS path: " + DFS_path);
        end = System.nanoTime();
        System.out.println("Takes: " + (end - start) + " ns \n");
    }

}