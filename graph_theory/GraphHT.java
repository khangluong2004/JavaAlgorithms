package algorithms.graph_theory;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;

// Implementation of Directed, Simple graph
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

    public void removeEdge(int[] edge){
        graph.get(edge[0]).remove(edge[1]);
    }

    //-----------------
    // Search

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

    //--------------------
    // Shortest path
    // Djikstra algorithm, supported by PriorityQueue (Heap) for optimal minimum extraction
    // Time complexity: O((V + E) log(V)) with heap, or O(V^2) without
    // Used only on POSITIVE weights graph
    
    public int Djikstra(int start, int search_elem, LinkedList<Integer> path){
        int[] parents = new int[num_nodes];

        // Extra array of cost to keep track of update
        // for updating parents
        int[] costs = new int[num_nodes];
        Arrays.fill(costs, Integer.MAX_VALUE);
        // Heap for minimum cost
        PriorityQueue<ArrayList<Integer>> costHeap = 
            new PriorityQueue<ArrayList<Integer>>((a, b) -> { return (b.get(1) - a.get(1));});

        HashSet<Integer> visited = new HashSet<Integer>();

        ArrayList<Integer> cur_info = new ArrayList<Integer>(3);
        cur_info.add(start);
        cur_info.add(0);
        costHeap.add(cur_info);
        while (visited.size() < num_nodes){
            // Get the node with current minimum cost
            ArrayList<Integer> min_node = costHeap.poll();
            // Could contain visited node
            while (visited.contains(min_node.get(0))){
                min_node = costHeap.poll();
            }
            // Exit when search_elem is marked as minimum node
            if (min_node.get(0) == search_elem){
                findPath(parents, path, start, search_elem);
                return min_node.get(1);
            }
            visited.add(min_node.get(0));

            // Add new cost for all neighbours
            Set<Integer> neighbours = graph.get(min_node.get(0)).keySet();
            for (int neighbour: neighbours){
                if (visited.contains(neighbour)){
                    continue;
                }

                int update_cost = min_node.get(1) + graph.get(min_node.get(0)).get(neighbour);
                if (update_cost < costs[neighbour]){
                    costs[neighbour] = update_cost;

                    ArrayList<Integer> neighbour_node = new ArrayList<Integer>(3);
                    neighbour_node.add(neighbour);
                    neighbour_node.add(update_cost);
                    costHeap.add(neighbour_node);

                    // Update parents for tracking path
                    parents[neighbour] = min_node.get(0);
                }
            }
        }

        return -1;
    }

    // Bellman-Ford algorithm, used for negative weights
    // Time complexity: O(V * E)
    // Return minimum distance from source to all nodes (at corresponding index); 
    // or empty list if negative cycle exists

    // Inefficient compared to Djikstra, but can be used for negative weights
    // Used to detect negative cycle
    // Intuition:
    // 1. Without negative cycle: Paths contain at max V nodes
    // 2. With negative cycle: Shorter path with > V nodes

    public int[] BellmanFord(int start){
        int[] dists = new int[num_nodes];
        Arrays.fill(dists, Integer.MAX_VALUE);
        dists[start] = 0;

        // Iterate all possible cases
        for (int v=0; v < num_nodes - 1; v++){
            // Iterate through all edges
            for (int start_node: graph.keySet()){
                HashMap<Integer, Integer> cur_map = graph.get(start_node);
                for (int end_node: cur_map.keySet()){
                    dists[end_node] = Math.min(dists[start_node] + cur_map.get(end_node), 
                                            dists[end_node]);
                }
            }
        }

        // Check negative cycle
        for (int start_node: graph.keySet()){
            HashMap<Integer, Integer> cur_map = graph.get(start_node);
            for (int end_node: cur_map.keySet()){
                if (dists[start_node] + cur_map.get(end_node) < dists[end_node]){
                    return new int[0];
                }
            }
        }

        return dists;
    }

    // ----------------------------
    // Topological sort: Kahn's algorithm
    // Topological sort: A linear arrangments ensuring that each node's
    // precedences are satisfied

    // Time complexity: O(V + E)
    // Perform topological sort on the graph;
    // If there is no loop, return the sort as Queue, else return null
    
    // The graph must be acyclic for this to be possible
    // Assuming that an edge A -> B means: A must be completed before B

    // Inituition (Indegree = Number of edge coming into a node):
    // 1. Node with indegree = 0 means they have no precedence
    // 2. Remove the Node with indegree 0 along with their edges
    // 3. Repeat until no nodes with indegree 0 
    // 4. If all nodes removed = No cycle, otherwise there is a cycle
    public Queue<Integer> KahnSort(){
        // Pre-calculate all indegree for each node
        int[] indegree = new int[num_nodes];

        for (int start_node: graph.keySet()){
            for (int end_node: graph.get(start_node).keySet()){
                indegree[end_node] += 1;
            }
        }


        // Find the zero-indegree and add to check_queue
        Queue<Integer> sort_results = new LinkedList<Integer>();
        Queue<Integer> checkQueue = new LinkedList<Integer>();

        for (int i=0; i < num_nodes; i++){
            if (indegree[i] == 0){
                checkQueue.add(i);
            }
        }

        // Remove the zero-indegree and edit in-degree
        // Add new zero-indegree to checkQueue 
        // Loop until checkQueue is empty
        while (!checkQueue.isEmpty()){
            int top_elem = checkQueue.poll();
            sort_results.add(top_elem);

            if (graph.get(top_elem) == null){
                continue;
            }

            Set<Integer> neighbours = graph.get(top_elem).keySet();
            for (int neighbour: neighbours){
                indegree[neighbour] -= 1;
                if (indegree[neighbour] == 0){
                    checkQueue.add(neighbour);
                }
            }
        }

        // Check if there is a loop or not
        if (sort_results.size() < num_nodes){
            return null;
        } else {
            return sort_results;
        }
    }

    // -------------------------
    // Minimum spanning tree
    // Can work for negative weights
    // But must be UNDIRECTED graph

    // Prim's algorithm optimized with Heap
    // Time complexity: O(E log(V))
    // Note: log(E) <= log(V^2) <= 2 log(V), so there is 
    // no difference between log(E) and log(V) 

    // Return the graph of the minimum spanning tree

    // Helper class for the heap: Edge (endNode, weight, startNode)
    class Edge{
        int start;
        int end;
        int weight;

        public Edge(int start, int end, int weight){
            this.start = start;
            this.end = end;
            this.weight = weight;
        }
    }

    public GraphHT Prim(){
        GraphHT spanTree = new GraphHT(new int[0][0], this.num_nodes); 

        // Min heap used to keep track of the minimum edges
        // connecting nodes already in the tree and nodes that aren't 
        // in the tree 
        PriorityQueue<Edge> minHeap = new PriorityQueue<Edge>(
            (a, b) -> {return a.weight - b.weight;});

        // Visited: Keep track by a boolean array (as each node
        // is labelled from 0 to n-1)
        boolean[] visited = new boolean[this.num_nodes];

        // 1. Pick a random node to start
        int start = 0;
        visited[start] = true;

        // 2. Add all vertices connecting with `start`
        HashMap<Integer, Integer> connectEdges = this.graph.get(start);

        for (int connectNode: connectEdges.keySet()){
            minHeap.add(new Edge(start, connectNode, connectEdges.get(connectNode)));
        }

        // 3. While the minHeap is not empty (still nodes to connect)
        while (!minHeap.isEmpty()){
            // 3a. Add the minimum connected edge to the tree
            // 3b. Add new node to visited
            Edge shortest = minHeap.poll();
            while (visited[shortest.end]){
                shortest = minHeap.poll();
                if (shortest == null){
                    return spanTree;
                }
            }

            int[] addEdge = new int[3];
            addEdge[0] = shortest.start;
            addEdge[1] = shortest.end;
            addEdge[2] = shortest.weight;
            spanTree.addEdge(addEdge);

            visited[shortest.end] = true;

            // 3c. Add all new connected edge to minHeap

            connectEdges = this.graph.get(shortest.end);

            for (int connectNode: connectEdges.keySet()){
                minHeap.add(new Edge(shortest.end, connectNode, connectEdges.get(connectNode)));
            }
        }

        return spanTree;
    }

    // Kruskal's algorithm
    // Time complexity: O(E log V)
    // Rank all edges, and pick from smallest to largest
    // Make sure no cycle is created

    public GraphHT Kruskal(){
        GraphHT spanTree = new GraphHT(new int[0][0], this.num_nodes);
        boolean[] visited = new boolean[this.num_nodes];
        
        LinkedList<Edge> edgeList = new LinkedList<Edge>();

        for (int node: graph.keySet()){
            HashMap<Integer, Integer> edges = graph.get(node);
            for (int connectNode: edges.keySet()){
                Edge new_edge = new Edge(node, connectNode, edges.get(connectNode));
                edgeList.add(new_edge);
            }
        }

        Collections.sort(edgeList, (a, b) -> {return (a.weight - b.weight);});

        while (!edgeList.isEmpty()){
            // Take shortest edge
            Edge shortEdge = edgeList.removeFirst();
            // Check for cycle
            while (visited[shortEdge.end]){
                shortEdge = edgeList.removeFirst();
                if (edgeList.isEmpty()){
                    return spanTree;
                }
            }

            // Add edge to tree and visited
            int[] curEdge = new int[3];
            curEdge[0] = shortEdge.start;
            curEdge[1] = shortEdge.end;
            curEdge[2] = shortEdge.weight;
            spanTree.addEdge(curEdge);

            visited[shortEdge.end] = true;
        }

        return spanTree;
    }


    public static void main(String[] args) {
        // Create undirected graph to test Prim/ Kruskal
        // Note: Topological sort can't work on undirected graph
        int[][] test_edges = {
            {0, 1, 5}, {1, 0, 5}, 
            {1, 2, 3}, {1, 3, 5}, {2, 1, 3}, {3, 1, 5},
            {2, 3, 4}, {3, 2, 4}, 
            {3, 4, 9}, {3, 5, 7}, {4, 3, 9}, {5, 3, 7},  
            {4, 5, 8}, {5, 4, 8}};
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

        // Djikstra
        LinkedList<Integer> Djikstra_path = new LinkedList<Integer>();
        start = System.nanoTime();
        System.out.println("Minimum cost: " + testGraph.Djikstra(0, 5, Djikstra_path));
        System.out.println("Djikstra path: " + Djikstra_path);
        end = System.nanoTime();
        System.out.println("Takes: " + (end - start) + " ns \n");

        // Bellman-Ford
        start = System.nanoTime();
        System.out.println("Minimum cost: " + Arrays.toString(testGraph.BellmanFord(0)));
        end = System.nanoTime();
        System.out.println("Takes: " + (end - start) + " ns \n");

        // Kahn's topological sort
        start = System.nanoTime();
        System.out.println("Topological sort: " + testGraph.KahnSort());
        end = System.nanoTime();
        System.out.println("Takes: " + (end - start) + " ns \n");

        // Prim's algorithm
        start = System.nanoTime();
        System.out.println("Prim's tree: " + testGraph.Prim().graph);
        end = System.nanoTime();
        System.out.println("Takes: " + (end - start) + " ns \n");

        // Kruskal's algorithm
        start = System.nanoTime();
        System.out.println("Kruskal's tree: " + testGraph.Kruskal().graph);
        end = System.nanoTime();
        System.out.println("Takes: " + (end - start) + " ns \n");
    }

}