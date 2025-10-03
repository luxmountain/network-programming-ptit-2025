package P6PnE4gz;

public class TestExample {
    public static void main(String[] args) {
        // Test example: k = 3, array = "1,2,3,4,5,6,7,8"
        int k = 3;
        String arrayStr = "1,2,3,4,5,6,7,8";
        
        System.out.println("Input:");
        System.out.println("k = " + k);
        System.out.println("Array: " + arrayStr);
        
        // Process array
        String[] elements = arrayStr.split(",");
        int[] numbers = new int[elements.length];
        
        // Convert string to integer array
        for (int i = 0; i < elements.length; i++) {
            numbers[i] = Integer.parseInt(elements[i].trim());
        }
        
        System.out.println("\nOriginal array: ");
        printArray(numbers);
        
        // Divide array into segments of length k and reverse each segment
        for (int i = 0; i < numbers.length; i += k) {
            int end = Math.min(i + k - 1, numbers.length - 1);
            System.out.println("Reversing segment from " + i + " to " + end);
            reverseSegment(numbers, i, end);
        }
        
        System.out.println("\nAfter processing: ");
        printArray(numbers);
        
        // Convert to result string
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < numbers.length; i++) {
            result.append(numbers[i]);
            if (i < numbers.length - 1) {
                result.append(",");
            }
        }
        
        String resultStr = result.toString();
        System.out.println("Result string: " + resultStr);
        System.out.println("Expected: 3,2,1,6,5,4,8,7");
    }
    
    private static void reverseSegment(int[] arr, int start, int end) {
        while (start < end) {
            int temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            start++;
            end--;
        }
    }
    
    private static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) {
                System.out.print(",");
            }
        }
        System.out.println();
    }
}