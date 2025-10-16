---
title: "Performance Analysis of Popular Search Algorithms using Java & Python"
tags:
  - Java
  - Python
  - Algorithms
  - Performance Analysis
  - Data Structures
authors:
  - name: Redha Zidan
    affiliation: 1
  - name: Nabeel urRehman
    affiliation: 1
affiliations:
  - name: Department of Computer Science & Engineering, Shadan College of Engineering & Technology, Affiliated to JNTUH
    index: 1
date: 2025-10-16
---

# Summary
This study presents a comparative performance analysis of widely used search algorithms—Linear Search, Binary Search, Depth-First Search (DFS), Breadth-First Search (BFS), and A* Search—implemented in two popular programming languages: **Java** and **Python**.  
The objective is to empirically evaluate the runtime behavior, scalability, and efficiency of these algorithms under identical experimental conditions. Results reveal that while Java generally outperforms Python in computation-intensive tasks due to Just-In-Time (JIT) optimizations, Python offers flexibility and ease of implementation for prototyping and smaller datasets.  
The findings highlight that algorithmic performance depends not only on theoretical complexity but also on language-level execution models, memory management, and runtime characteristics.

# Statement of need
Search algorithms are fundamental to modern computing applications such as information retrieval, artificial intelligence, and network optimization.  
While theoretical complexities are well established, real-world performance often deviates due to language design and runtime differences.  
This research bridges the gap between **algorithmic theory** and **practical implementation**, providing reproducible benchmarks that help students, educators, and developers choose suitable languages for algorithm-intensive tasks.  
By releasing this study as open-source software, it supports reproducibility and future comparative studies in performance-critical computing.

# Functionality
The repository provides:
- Implementations of Linear, Binary, DFS, BFS, and A* search algorithms in both Java and Python.  
- Benchmarking scripts that measure execution time using `System.nanoTime()` (Java) and `time.perf_counter()` (Python).  
- Dataset generators for numerical and graph-based inputs, scaling up to 500,000 nodes.  
- Automated comparison and visualization of runtime trends and scalability.  
Each algorithm was executed ten times per dataset size, and averaged results were plotted to illustrate performance differences between both languages.

# Example usage
Users can reproduce the experiments using the included benchmarking scripts/ instructions:
```bash
# For Python
python {Algorithm directory}/{Algorithm Name} Python/{algorithm}.py
```
# For Java, collaborators should:

1. Open the /{algorithm name} java directory as a Maven project in IntelliJ IDEA.

2. Let IntelliJ automatically import dependencies from pom.xml.

3. Build the project (⌘F9 on macOS or Ctrl+F9 on Windows/Linux).

4. Run the benchmarking class from src/main/java/com/example/search/{algorithm}.java.
