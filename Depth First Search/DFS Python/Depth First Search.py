import time

def load_graph(file_path):
    max_node = 0
    min_node = float("inf")
    edges = []

    # --- First pass: get bounds ---
    with open(file_path, "r") as f:
        for line in f:
            if not line.strip():
                continue
            u, v = map(int, line.split())
            edges.append((u, v))
            max_node = max(max_node, u, v)
            min_node = min(min_node, u, v)

    # --- Build adjacency list ---
    adj = [[] for _ in range(max_node + 1)]
    for u, v in edges:
        adj[u].append(v)
        adj[v].append(u)

    return adj, min_node, max_node


def dfs_iterative(start, adj, visited):
    stack = [start]
    while stack:
        node = stack.pop()
        if not visited[node]:
            visited[node] = True
            for neighbor in adj[node]:
                if not visited[neighbor]:
                    stack.append(neighbor)


def main():
    file_path = "dfs_graph_10000_nodes_50000_edges.txt"

    # Load graph (not timed)
    adj, min_node, max_node = load_graph(file_path)

    # --- Warm-up (100 runs) ---
    for _ in range(100):
        visited = [False] * (max_node + 1)
        dfs_iterative(min_node, adj, visited)

    # --- Timed run ---
    visited = [False] * (max_node + 1)
    start = time.perf_counter_ns()
    dfs_iterative(min_node, adj, visited)
    end = time.perf_counter_ns()

    duration_ms = (end - start) / 1_000_000.0
    print(f"DFS execution time: {duration_ms:.3f} ms")


if __name__ == "__main__":
    main()