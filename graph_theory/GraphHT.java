package algorithms.graph_theory;
import java.util.HashMap;

// Implementation of (Un)directed graph
// Graph = Set of nodes + Set of edges (with weights)
// Each node is labelled from 0 to n;
// Each edge is represented by a pair of nodes & weight: [Node1, Node2, Weight]

// Other implementation: Adjacency matrix & Adjacency LinkedList

public class GraphHT {
    // Recommended: Easy to implement & quite efficient
    // Graphs as Hash table of hash table
    HashMap<Integer, HashMap<Integer, Integer>> graph;

    public GraphHT(int[][] edges){
        for (int i = 0; i < edges.length; i++){
            this.addEdge(edges[i]);
        }
    }

    public void addEdge(int[] edge){
        HashMap<Integer, Integer> curAdj = graph.get(edge[0]);
        curAdj.put(edge[1], edge[2]);
    }

    //BST: Breadth-first search
    

    public static void main(String[] args) {
        int[][] test_edges = {{1, 2, 3}, {2, 3, 4}, {1, 3, 5}};
        GraphHT testGraph = new GraphHT(test_edges);
    }

}