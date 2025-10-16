import java.io.*;
import java.util.*;

public class OptimizedAStar {
    static class Edge {
        int to;
        double cost;
        Edge(int to, double cost) {
            this.to = to;
            this.cost = cost;
        }
    }

    static class Node implements Comparable<Node> {
        int id;
        double fScore; // f = g + h
        Node(int id, double fScore) {
            this.id = id;
            this.fScore = fScore;
        }
        @Override
        public int compareTo(Node other) {
            return Double.compare(this.fScore, other.fScore);
        }
    }

    static List<Edge>[] adj;
    static double[] heuristic;

    public static double aStar(int start, int goal) {
        int n = adj.length;
        double[] gScore = new double[n];
        Arrays.fill(gScore, Double.POSITIVE_INFINITY);
        gScore[start] = 0;

        boolean[] visited = new boolean[n];
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(start, heuristic[start]));

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int u = current.id;
            if (visited[u]) continue;
            visited[u] = true;

            if (u == goal) return gScore[goal];

            for (Edge e : adj[u]) {
                int v = e.to;
                double tentativeG = gScore[u] + e.cost;
                if (tentativeG < gScore[v]) {
                    gScore[v] = tentativeG;
                    pq.add(new Node(v, tentativeG + heuristic[v]));
                }
            }
        }

        return Double.POSITIVE_INFINITY; // goal unreachable
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        int numNodes = 10000;
        String graphFile = "src/main/resources/astar_graph_10000_nodes.txt";
        String heuristicFile = "src/main/resources/astar_heuristic_10000_nodes.txt";

        // Initialize adjacency list
        adj = new ArrayList[numNodes];
        for (int i = 0; i < numNodes; i++) adj[i] = new ArrayList<>();
        heuristic = new double[numNodes];

        // Load graph
        try (BufferedReader br = new BufferedReader(new FileReader(graphFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\s+");
                int u = Integer.parseInt(parts[0]);
                int v = Integer.parseInt(parts[1]);
                double cost = Double.parseDouble(parts[2]);
                adj[u].add(new Edge(v, cost));
                adj[v].add(new Edge(u, cost)); // undirected
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Load heuristics
        try (BufferedReader br = new BufferedReader(new FileReader(heuristicFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\s+");
                int node = Integer.parseInt(parts[0]);
                heuristic[node] = Double.parseDouble(parts[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        int startNode = 0;
        int goalNode = numNodes - 1;

        // Warm-up (100 runs) for JIT
        for (int i = 0; i < 100; i++) {
            aStar(startNode, goalNode);
        }

        // Timed run
        long startTime = System.nanoTime();
        double shortestDistance = aStar(startNode, goalNode);
        long endTime = System.nanoTime();

        double durationMs = (endTime - startTime) / 1_000_000.0;
        System.out.printf("A* execution time: %.3f ms%n", durationMs);
        System.out.println("Shortest distance from node " + startNode + " to node " + goalNode + " = " + shortestDistance);
    }
}
