package algorithms.graph_theory;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.ArrayList;
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

    public GraphHT(int[][] edges){
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

    // BST: Breadth-first search
    // Search for a node from start, and return 
    // the number of edges needed to reach that node 
    // Return -1 if not found
    public int BFS(int start, int search_elem){
        Queue<ArrayList<Integer>> queue = new LinkedList<ArrayList<Integer>>();
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

                int cur_dist = 1 + top_elem.get(1);
                ArrayList<Integer> curInfo = new ArrayList<Integer>(3);
                if (neighbour == search_elem){
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
    

    public static void main(String[] args) {
        int[][] test_edges = {{1, 2, 3}, {2, 3, 4}, {1, 3, 5}, {3, 4, 9}, {4, 5, 8}};
        GraphHT testGraph = new GraphHT(test_edges);
        System.out.println("BFS test: " + testGraph.BFS(1, 5));
    }

}