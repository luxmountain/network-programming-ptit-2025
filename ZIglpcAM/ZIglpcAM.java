package ZIglpcAM;

import java.io.*;
import java.net.*;

public class ZIglpcAM {
    public static void main(String[] args) {
        String serverAddress = "203.162.10.109";
        int port = 2206;
        String studentCode = "B22DCVT445"; // Replace with your actual student code
        String qCode = "ZIglpcAM";

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

            // c. Calculate sum of prime numbers and send result to server
            int sumOfPrimes = calculateSumOfPrimes(receivedData);
            System.out.println("Sum of prime numbers: " + sumOfPrimes);
            
            String result = String.valueOf(sumOfPrimes);
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

    // Helper method to calculate sum of prime numbers from comma-separated string
    private static int calculateSumOfPrimes(String data) {
        String[] numbers = data.split(",");
        int sum = 0;

        for (String numStr : numbers) {
            try {
                int num = Integer.parseInt(numStr.trim());
                if (isPrime(num)) {
                    System.out.println("Prime number found: " + num);
                    sum += num;
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid number format: " + numStr);
            }
        }

        return sum;
    }

    // Helper method to check if a number is prime
    private static boolean isPrime(int n) {
        if (n < 2) {
            return false;
        }
        if (n == 2) {
            return true;
        }
        if (n % 2 == 0) {
            return false;
        }
        
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
}
