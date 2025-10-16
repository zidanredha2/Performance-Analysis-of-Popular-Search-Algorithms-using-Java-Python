import time


def binary_search(data, target):
    left, right = 0, len(data) - 1
    while left <= right:
        mid = left + (right - left) // 2
        if data[mid] == target:
            return mid
        elif data[mid] < target:
            left = mid + 1
        else:
            right = mid - 1
    return -1  # Not found


def main():
    file_path = "sorted_dataset_5000.txt"  # Make sure path is correct

    # Load dataset from file
    with open(file_path, "r") as f:
        dataset = [int(line.strip()) for line in f]

    # Ensure dataset has enough elements
    if len(dataset) < 4000:
        print("Dataset has fewer than 4000 elements!")
        return

    target = dataset[2999]  # 4000th element

    # --- Warm-up loop (to reduce first-call overhead) ---
    for _ in range(1000):
        binary_search(dataset, target)

    # --- Actual timing ---
    start_time = time.perf_counter_ns()
    result_index = binary_search(dataset, target)
    end_time = time.perf_counter_ns()

    duration_ms = (end_time - start_time) / 1_000_000  # ns → ms

    # Output

    print(f"Binary search took {duration_ms:.4f} ms")


if __name__ == "__main__":
    main()