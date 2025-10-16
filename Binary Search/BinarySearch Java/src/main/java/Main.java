import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String filePath = "src/main/resources/sorted_dataset_5000.txt"; // Make sure path is correct
        List<Integer> dataset = new ArrayList<>();

        // Load dataset from file
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                dataset.add(Integer.parseInt(line.trim()));
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        // Ensure dataset has enough elements
        if (dataset.size() < 4000) {
            System.out.println("Dataset has fewer than 4000 elements!");
            return;
        }

        int target = dataset.get(2999); // 4000th element

        // --- Warm up JIT by running dummy searches ---
        for (int i = 0; i < 1000; i++) {
            binarySearch(dataset, target);
        }

        // --- Actual timing measurement ---
        long startTime = System.nanoTime();
        int resultIndex = binarySearch(dataset, target);
        long endTime = System.nanoTime();

        double durationMs = (endTime - startTime) / 1_000_000.0; // ns -> ms


        System.out.printf("Binary search took %.4f ms%n", durationMs);
    }

    // Binary search function
    public static int binarySearch(List<Integer> data, int target) {
        int left = 0, right = data.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (data.get(mid) == target) {
                return mid;
            } else if (data.get(mid) < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1; // not found
    }
}
