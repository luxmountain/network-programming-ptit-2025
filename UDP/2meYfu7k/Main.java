import java.io.*;
import java.net.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        DatagramSocket socket = null;
        
        try {
            // Create UDP socket
            socket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName("203.162.10.109");
            int serverPort = 2207;
            
            // a. Send student code and question code
            String studentCode = "B22DCVT445";
            String qCode = "2meYfu7k";
            String message = ";" + studentCode + ";" + qCode;
            
            byte[] sendData = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(
                sendData, sendData.length, serverAddress, serverPort
            );
            socket.send(sendPacket);
            System.out.println("Sent: " + message);
            
            // b. Receive message from server
            byte[] receiveData = new byte[2048]; // Increased buffer size for 50 numbers
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);
            
            String receivedMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Received: " + receivedMessage);
            
            // Parse requestId and numbers
            String[] parts = receivedMessage.split(";", 2);
            if (parts.length != 2) {
                System.err.println("Invalid message format received");
                return;
            }
            
            String requestId = parts[0];
            String numbersString = parts[1];
            System.out.println("RequestId: " + requestId);
            System.out.println("Numbers string: " + numbersString);
            
            // c. Find second max and second min values
            String[] numberStrings = numbersString.split(",");
            if (numberStrings.length < 2) {
                System.err.println("Need at least 2 numbers to find second max and min");
                return;
            }
            
            // Parse all numbers into a set to handle duplicates, then sort
            Set<Integer> uniqueNumbers = new TreeSet<>();
            System.out.print("Numbers: ");
            for (String numStr : numberStrings) {
                try {
                    int num = Integer.parseInt(numStr.trim());
                    uniqueNumbers.add(num);
                    System.out.print(num + " ");
                } catch (NumberFormatException e) {
                    System.err.println("Invalid number format: " + numStr);
                    return;
                }
            }
            System.out.println();
            
            // Convert to sorted list
            List<Integer> sortedNumbers = new ArrayList<>(uniqueNumbers);
            
            if (sortedNumbers.size() < 2) {
                System.err.println("Need at least 2 unique numbers to find second max and min");
                return;
            }
            
            // Get second max and second min
            int secondMax = sortedNumbers.get(sortedNumbers.size() - 2); // Second largest
            int secondMin = sortedNumbers.get(1); // Second smallest
            
            System.out.println("Sorted unique numbers: " + sortedNumbers);
            System.out.println("Second Max: " + secondMax);
            System.out.println("Second Min: " + secondMin);
            
            // Send second max and second min back to server
            String responseMessage = requestId + ";" + secondMax + "," + secondMin;
            byte[] responseData = responseMessage.getBytes();
            DatagramPacket responsePacket = new DatagramPacket(
                responseData, responseData.length, serverAddress, serverPort
            );
            socket.send(responsePacket);
            System.out.println("Sent response: " + responseMessage);
            
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // d. Close socket
            if (socket != null) {
                socket.close();
                System.out.println("Socket closed.");
            }
        }
    }
}
