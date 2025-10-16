import time
from collections import defaultdict, deque

# --- Load graph from edge list ---
adj = defaultdict(list)
file_path = "bfs_graph_10000_nodes_50000_edges.txt"
max_node = 0

with open(file_path, "r") as f:
    for line in f:
        u, v = map(int, line.split())
        adj[u].append(v)
        adj[v].append(u)
        max_node = max(max_node, u, v)

visited = [False] * (max_node + 1)

# --- BFS Traversal ---
def bfs(start):
    visited = [False] * (max_node + 1)
    queue = deque([start])
    visited[start] = True

    while queue:
        node = queue.popleft()
        for neighbor in adj[node]:
            if not visited[neighbor]:
                visited[neighbor] = True
                queue.append(neighbor)

# --- Warm-up BFS ---
bfs(0)

# --- Timed BFS runs ---
runs = 10
total_time_ns = 0

for _ in range(runs):
    start_time = time.perf_counter_ns()
    bfs(0)
    end_time = time.perf_counter_ns()
    total_time_ns += (end_time - start_time)

avg_time_ms = total_time_ns / (runs * 1_000_000)
print(f"Average BFS execution time over {runs} runs: {avg_time_ms:.3f} ms")
