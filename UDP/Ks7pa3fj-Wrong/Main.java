package UDP.Ks7pa3fj;

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
            int serverPort = 2208;
            
            // a. Send student code and question code
            String studentCode = "B22DCVT445";
            String qCode = "Ks7pa3fj";
            String message = ";" + studentCode + ";" + qCode;
            
            byte[] sendData = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(
                sendData, sendData.length, serverAddress, serverPort
            );
            socket.send(sendPacket);
            System.out.println("Sent: " + message);
            
            // b. Receive message from server
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);
            
            String receivedMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Received: " + receivedMessage);
            
            // Parse requestId and data
            String[] parts = receivedMessage.split(";", 2);
            if (parts.length != 2) {
                System.err.println("Invalid message format received. Expected format: requestId;data");
                return;
            }
            
            String requestId = parts[0];
            String data = parts[1];
            
            System.out.println("RequestId: " + requestId);
            System.out.println("Data: " + data);
            
            // c. Sort words in reverse alphabetical order (z to a)
            String[] words = data.split("\\s+"); // Split by whitespace
            
            // Convert to list for sorting
            List<String> wordList = new ArrayList<>(Arrays.asList(words));
            
            // Sort in reverse alphabetical order (z to a)
            Collections.sort(wordList, Collections.reverseOrder());
            
            System.out.println("Original words: " + Arrays.toString(words));
            System.out.println("Sorted words (z to a): " + wordList);
            
            // Join words with comma
            String sortedWords = String.join(",", wordList);
            
            // Send sorted words back to server
            String responseMessage = requestId + ";" + sortedWords;
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
