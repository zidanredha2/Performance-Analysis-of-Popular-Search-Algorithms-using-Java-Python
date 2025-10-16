import heapq
import time
def load_graph(graph_file, heuristic_file, num_nodes):
    # Initialize adjacency list
    adj = [[] for _ in range(num_nodes)]
    # Load edges
    with open(graph_file, "r") as f:
        for line in f:
            if not line.strip():
                continue
            u, v, cost = line.split()
            u, v, cost = int(u), int(v), float(cost)
            adj[u].append((v, cost))
            adj[v].append((u, cost))  # undirected graph
    # Load heuristics
    heuristic = [0.0] * num_nodes
    with open(heuristic_file, "r") as f:
        for line in f:
            if not line.strip():
                continue
            node, h = line.split()
            node, h = int(node), float(h)
            heuristic[node] = h
    return adj, heuristic
def a_star(start, goal, adj, heuristic):
    num_nodes = len(adj)
    g_score = [float('inf')] * num_nodes
    g_score[start] = 0.0
    visited = [False] * num_nodes
    pq = []
    heapq.heappush(pq, (heuristic[start], start))
    while pq:
        f_score, u = heapq.heappop(pq)
        if visited[u]:
            continue
        visited[u] = True
        if u == goal:
            return g_score[goal]
        for v, cost in adj[u]:
            tentative_g = g_score[u] + cost
            if tentative_g < g_score[v]:
                g_score[v] = tentative_g
                heapq.heappush(pq, (tentative_g + heuristic[v], v))
    return float('inf')  # Goal unreachable
def main():
    num_nodes = 10000
    graph_file = "astar_graph_10000_nodes.txt"
    heuristic_file = "astar_heuristic_10000_nodes.txt"
    adj, heuristic = load_graph(graph_file, heuristic_file, num_nodes)
    start_node = 0
    goal_node = num_nodes - 1
    # Warm-up (100 runs) for more stable timing
    for _ in range(100):
        a_star(start_node, goal_node, adj, heuristic)
    # Timed run
    start_time = time.perf_counter_ns()
    shortest_distance = a_star(start_node, goal_node, adj, heuristic)
    end_time = time.perf_counter_ns()
    duration_ms = (end_time - start_time) / 1_000_000.0
    print(f"A* execution time: {duration_ms:.3f} ms")
    print(f"Shortest distance from node {start_node} to node {goal_node} = {shortest_distance:.3f}")
if __name__ == "__main__":
    main()