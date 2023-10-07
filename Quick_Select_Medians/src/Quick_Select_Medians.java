import java.util.Scanner;
import java.util.Random;

public class Quick_Select_Medians {
    public static void main(String[] args) {
        // Create a Scanner object to read user input
        Scanner inputScanner = new Scanner(System.in);

        // Get user input for the size of the array
        System.out.print("Enter the size of the array: ");
        int arraySize = inputScanner.nextInt();

        // Generate an array with the specified size and random elements
        int[] randomArray = random_array_generator(arraySize);

        // Get user input for the value of k
        System.out.print("Enter the value of k (0-based index): ");
        int kValue = inputScanner.nextInt();

        // Record the start time to measure execution time
        long startTime = System.nanoTime();

        // Call the Quick_Select_Medians function to find the k-th smallest element
        int result = Quick_Select_Medians(randomArray, kValue);

        // Record the end time
        long endTime = System.nanoTime();

        // Print the result
        System.out.printf("The %d-th smallest element is %d%n", kValue, result);

        // Calculate and print the execution time in nanoseconds
        long executionTime = endTime - startTime;
        System.out.printf("Execution time: %d nanoseconds%n", executionTime);
    }

    // Function to generate an array with random elements
    public static int[] random_array_generator(int size) {
        int[] randomArr = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            randomArr[i] = random.nextInt(100); // Change 100 to the desired range of random values
        }
        return randomArr;
    }

    // Function to find the k-th smallest element in an array
    public static int Quick_Select_Medians(int[] array, int k) {
        // Check if the input array is empty
        if (array.length == 0) {
            return -1; // Return a default value for an empty array
        }

        // Partition the array using the median-of-medians pivot selection method
        int pivot = pivot_selection(array);

        // Partition the array into three parts: less than, equal to, and greater than the pivot
        int[] lower_value_than_pivot = new int[array.length];
        int[] equalToPivot = new int[array.length];
        int[] higher_value_than_pivot = new int[array.length];
        int lessSize = 0;
        int equalSize = 0;
        int greaterSize = 0;

        // Populate the partitions based on the pivot value
        for (int x : array) {
            if (x < pivot) {
                lower_value_than_pivot[lessSize++] = x;
            } else if (x == pivot) {
                equalToPivot[equalSize++] = x;
            } else {
                higher_value_than_pivot[greaterSize++] = x;
            }
        }

        // Determine which partition contains the k-th smallest element
        if (k < lessSize) {
            return Quick_Select_Medians(trimArray(lower_value_than_pivot, lessSize), k);
        } else if (k < lessSize + equalSize) {
            return pivot;
        } else {
            return Quick_Select_Medians(trimArray(higher_value_than_pivot, greaterSize), k - lessSize - equalSize);
        }
    }

    // Function to trim an array to a specified size
    public static int[] trimArray(int[] array, int size) {
        int[] trimmedArray = new int[size];
        System.arraycopy(array, 0, trimmedArray, 0, size);
        return trimmedArray;
    }

    // Function to select the pivot element using the median of medians method
    public static int pivot_selection(int[] array) {
        // Divide the input array into groups of five
        int groupSize = 5;
        int numGroups = (int) Math.ceil((double) array.length / groupSize);

        int[] medians = new int[numGroups];
        int medianIndex = 0;

        // Calculate the median of each group using insertion sort
        for (int group_initialize = 0; group_initialize < array.length; group_initialize += groupSize) {
            int groupEnd = Math.min(group_initialize + groupSize, array.length);
            int groupLength = groupEnd - group_initialize;
            int[] group = new int[groupLength];
            System.arraycopy(array, group_initialize, group, 0, groupLength);
            Insertion_sort(group);
            medians[medianIndex++] = group[groupLength / 2];
        }

        // Recursively select the pivot by finding the median of medians
        if (medians.length <= 5) {
            Insertion_sort(medians);
            return medians[medians.length / 2];
        } else {
            return Quick_Select_Medians(medians, medians.length / 2);
        }
    }

    // Function to perform insertion sort on an array
    public static void Insertion_sort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= 0 && key < array[j]) {
                array[j + 1] = array[j];
                j -= 1;
            }
            array[j + 1] = key;
        }
    }
}
