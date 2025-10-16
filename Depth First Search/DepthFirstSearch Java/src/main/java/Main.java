import java.io.*;
import java.util.*;

public class Main {
    static List<Integer>[] adj;
    static boolean[] visited;

    // --- Iterative DFS using ArrayDeque (faster than Stack) ---
    public static void dfsIterative(int start) {
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        stack.push(start);

        while (!stack.isEmpty()) {
            int node = stack.pop();
            if (!visited[node]) {
                visited[node] = true;
                for (int neighbor : adj[node]) {
                    if (!visited[neighbor]) {
                        stack.push(neighbor);
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        String filePath = "src/main/resources/dfs_graph_10000_nodes_50000_edges.txt";
        int maxNode = 0, minNode = Integer.MAX_VALUE;

        // --- Find graph bounds first ---
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                if (parts.length < 2) continue;
                int u = Integer.parseInt(parts[0]);
                int v = Integer.parseInt(parts[1]);
                maxNode = Math.max(maxNode, Math.max(u, v));
                minNode = Math.min(minNode, Math.min(u, v));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // --- Initialize adjacency list ---
        adj = new ArrayList[maxNode + 1];
        for (int i = 0; i <= maxNode; i++) adj[i] = new ArrayList<>();

        // --- Reload graph edges ---
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                if (parts.length < 2) continue;
                int u = Integer.parseInt(parts[0]);
                int v = Integer.parseInt(parts[1]);
                adj[u].add(v);
                adj[v].add(u);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // --- Warm-up (100 runs) ---
        for (int i = 0; i < 100; i++) {
            visited = new boolean[maxNode + 1];
            dfsIterative(minNode);
        }

        // --- Timed Run ---
        visited = new boolean[maxNode + 1];
        long startTime = System.nanoTime();
        dfsIterative(minNode);
        long endTime = System.nanoTime();

        double durationMs = (endTime - startTime) / 1_000_000.0;
        System.out.printf("DFS execution time: %.3f ms%n", durationMs);
    }
}
