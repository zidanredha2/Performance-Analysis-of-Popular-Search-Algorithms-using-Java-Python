import time


def linear_search(data, target):
    for i, val in enumerate(data):
        if val == target:
            return i
    return -1


def main():
    file_path = "large_dataset_5000.txt"  # make sure this path is correct

    # Load dataset from file
    with open(file_path, "r") as f:
        dataset = [int(line.strip()) for line in f]

    # Ensure dataset has enough elements
    if len(dataset) < 4000:
        print("Dataset has fewer than 4000 elements!")
        return

    target = dataset[3999]  # 4000th element

    # --- Warm-up (to reduce first-run overhead) ---
    for _ in range(1000):
        linear_search(dataset, target)

    # --- Actual timing ---
    start_time = time.perf_counter_ns()
    result_index = linear_search(dataset, target)
    end_time = time.perf_counter_ns()

    duration_ms = (end_time - start_time) / 1_000_000  # ns → ms



    print(f"Linear search took {duration_ms:.3f} ms")


main()