package Bz6pCg9n;

import java.io.*;
import java.net.*;
import java.util.*;

public class Bz6pCg9n {
    public static void main(String[] args) {
        String serverAddress = "203.162.10.109";
        int port = 2206;
        String studentCode = "B22DCVT445"; // Replace with your actual student code
        String qCode = "Bz6pCg9n";

        Socket socket = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            // Establish connection to server
            socket = new Socket(serverAddress, port);
            System.out.println("Connected to server at " + serverAddress + ":" + port);

            // Get input and output streams
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            // a. Send student code and question code in format "studentCode;qCode"
            String message = studentCode + ";" + qCode;
            System.out.println("Sending: " + message);
            outputStream.write(message.getBytes());
            outputStream.flush();
            System.out.println("Sent successfully: " + message);

            // b. Receive data from server (comma-separated integers)
            System.out.println("Waiting to receive data from server...");
            byte[] buffer = new byte[1024];
            int bytesRead = inputStream.read(buffer);
            String receivedData = new String(buffer, 0, bytesRead);
            System.out.println("Received data: " + receivedData);

            // c. Find two numbers with sum closest to double the average
            String result = findClosestPairToDoubleAverage(receivedData);
            System.out.println("Sending result: " + result);
            outputStream.write(result.getBytes());
            outputStream.flush();
            System.out.println("Result sent successfully: " + result);

            System.out.println("Operations completed successfully!");

        } catch (IOException e) {
            System.err.println("IO Error during communication with server: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // d. Close connection and terminate
            try {
                if (inputStream != null)
                    inputStream.close();
                if (outputStream != null)
                    outputStream.close();
                if (socket != null)
                    socket.close();
                System.out.println("Connection closed.");
            } catch (IOException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    // Helper method to find two numbers with sum closest to double the average
    private static String findClosestPairToDoubleAverage(String data) {
        String[] numberStrings = data.split(",");
        List<Integer> numbers = new ArrayList<>();
        
        // Parse numbers and calculate sum
        long sum = 0;
        for (String numStr : numberStrings) {
            try {
                int num = Integer.parseInt(numStr.trim());
                numbers.add(num);
                sum += num;
            } catch (NumberFormatException e) {
                System.err.println("Invalid number format: " + numStr);
            }
        }
        
        // Calculate double the average
        double average = (double) sum / numbers.size();
        double target = 2 * average;
        System.out.println("Average: " + average);
        System.out.println("Double average (target): " + target);
        
        // Find pair with sum closest to target
        int bestNum1 = numbers.get(0);
        int bestNum2 = numbers.get(1);
        double bestDifference = Math.abs((bestNum1 + bestNum2) - target);
        
        for (int i = 0; i < numbers.size(); i++) {
            for (int j = i + 1; j < numbers.size(); j++) {
                int num1 = numbers.get(i);
                int num2 = numbers.get(j);
                double currentSum = num1 + num2;
                double difference = Math.abs(currentSum - target);
                
                if (difference < bestDifference) {
                    bestDifference = difference;
                    // Ensure num1 < num2 as required
                    if (num1 < num2) {
                        bestNum1 = num1;
                        bestNum2 = num2;
                    } else {
                        bestNum1 = num2;
                        bestNum2 = num1;
                    }
                }
            }
        }
        
        System.out.println("Best pair found: " + bestNum1 + " and " + bestNum2);
        System.out.println("Sum: " + (bestNum1 + bestNum2) + ", Target: " + target);
        System.out.println("Difference from target: " + bestDifference);
        
        return bestNum1 + "," + bestNum2;
    }
}
