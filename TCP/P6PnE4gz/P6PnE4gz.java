package P6PnE4gz;

import java.io.*;
import java.net.*;

public class P6PnE4gz {
    public static void main(String[] args) {
        Socket socket = null;
        DataInputStream dis = null;
        DataOutputStream dos = null;
        
        try {
            // Connect to server at localhost:2207
            socket = new Socket("203.162.10.109", 2207);
            socket.setSoTimeout(5000); // 5 second timeout
            
            // Create input/output streams
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            
            // a. Send student code and question code
            String studentCode = "B22DCVT445"; // Change to your student code
            String qCode = "P6PnE4gz";
            String message = studentCode + ";" + qCode;
            dos.writeUTF(message);
            dos.flush();
            
            // b. Receive k and array string
            int k = dis.readInt(); // Receive segment length k
            String arrayStr = dis.readUTF(); // Receive array string
            
            System.out.println("Received k = " + k);
            System.out.println("Received array: " + arrayStr);
            
            // c. Process array
            String[] elements = arrayStr.split(",");
            int[] numbers = new int[elements.length];
            
            // Convert string to integer array
            for (int i = 0; i < elements.length; i++) {
                numbers[i] = Integer.parseInt(elements[i].trim());
            }
            
            // Divide array into segments of length k and reverse each segment
            for (int i = 0; i < numbers.length; i += k) {
                int end = Math.min(i + k - 1, numbers.length - 1);
                // Reverse segment from i to end
                reverseSegment(numbers, i, end);
            }
            
            // Convert processed array to string
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < numbers.length; i++) {
                result.append(numbers[i]);
                if (i < numbers.length - 1) {
                    result.append(",");
                }
            }
            
            String resultStr = result.toString();
            System.out.println("Result after processing: " + resultStr);
            
            // Send result to server
            dos.writeUTF(resultStr);
            dos.flush();
            
            System.out.println("Result sent successfully!");
            
        } catch (IOException e) {
            System.err.println("Connection error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // d. Close connection
            try {
                if (dis != null) dis.close();
                if (dos != null) dos.close();
                if (socket != null) socket.close();
                System.out.println("Connection closed.");
            } catch (IOException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
    
    // Method to reverse a segment in the array
    private static void reverseSegment(int[] arr, int start, int end) {
        while (start < end) {
            int temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            start++;
            end--;
        }
    }
}
