import java.io.*;
import java.util.*;

public class Main {

    private Map<Integer, List<Integer>> adj = new HashMap<>();
    private boolean[] visited;

    // --- BFS Traversal ---
    public void bfs(int start) {
        visited = new boolean[visited.length]; // reset visited array
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        visited[start] = true;

        while (!queue.isEmpty()) {
            int node = queue.poll();
            for (int neighbor : adj.getOrDefault(node, Collections.emptyList())) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.add(neighbor);
                }
            }
        }
    }

    // --- Load graph from edge list file ---
    public void loadGraph(String filePath) {
        int maxNode = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                int u = Integer.parseInt(parts[0]);
                int v = Integer.parseInt(parts[1]);

                adj.computeIfAbsent(u, k -> new ArrayList<>()).add(v);
                adj.computeIfAbsent(v, k -> new ArrayList<>()).add(u);

                maxNode = Math.max(maxNode, Math.max(u, v));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        visited = new boolean[maxNode + 1];
    }

    public static void main(String[] args) {
        Main graph = new Main();
        String filePath = "src/main/resources/bfs_graph_10000_nodes_50000_edges.txt";

        // Load graph (not timed)
        graph.loadGraph(filePath);

        // --- Warm-up BFS to trigger JIT ---
        graph.bfs(0);

        // --- Timed BFS runs ---
        int runs = 10;
        long totalTime = 0;

        for (int i = 0; i < runs; i++) {
            long startTime = System.nanoTime();
            graph.bfs(0);
            long endTime = System.nanoTime();
            totalTime += (endTime - startTime);
        }

        double avgTimeMs = totalTime / (runs * 1_000_000.0);
        System.out.println("Average BFS execution time over " + runs + " runs: " + avgTimeMs + " ms");
    }
}
