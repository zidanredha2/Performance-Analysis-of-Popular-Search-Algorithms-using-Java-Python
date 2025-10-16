import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String filePath = "src/main/resources/large_dataset_5000.txt"; // make sure this path is correct
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

        int target = dataset.get(3999); // 4000th element

        // --- Warm up JIT (run a few dummy searches before timing) ---
        for (int i = 0; i < 1000; i++) {
            linearSearch(dataset, target);
        }

        // --- Actual timing measurement ---
        long startTime = System.nanoTime();
        int resultIndex = linearSearch(dataset, target);
        long endTime = System.nanoTime();

        double durationInMillis = (endTime - startTime) / 1_000_000.0; // convert ns → ms
        
        System.out.printf("Linear search took %.3f ms%n", durationInMillis);
    }

    // Linear search function
    public static int linearSearch(List<Integer> data, int target) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) == target) {
                return i;
            }
        }
        return -1;
    }
}
