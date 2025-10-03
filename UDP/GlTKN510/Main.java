package UDP.GlTKN510;

import java.io.*;
import java.net.*;

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
            String qCode = "GlTKN510";
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
                System.err.println("Invalid message format received");
                return;
            }
            
            String requestId = parts[0];
            String data = parts[1];
            System.out.println("RequestId: " + requestId);
            System.out.println("Data to process: " + data);
            
            // c. Normalize the string
            String normalizedData = normalizeString(data);
            System.out.println("Normalized data: " + normalizedData);
            
            // Send normalized string back to server
            String responseMessage = requestId + ";" + normalizedData;
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
    
    /**
     * Normalize string according to the rules:
     * - First character of each word is uppercase
     * - All other characters are lowercase
     */
    private static String normalizeString(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        
        StringBuilder result = new StringBuilder();
        boolean isFirstCharOfWord = true;
        
        for (char c : input.toCharArray()) {
            if (Character.isWhitespace(c)) {
                result.append(c);
                isFirstCharOfWord = true;
            } else if (Character.isLetter(c)) {
                if (isFirstCharOfWord) {
                    result.append(Character.toUpperCase(c));
                    isFirstCharOfWord = false;
                } else {
                    result.append(Character.toLowerCase(c));
                }
            } else {
                result.append(c);
                isFirstCharOfWord = false;
            }
        }
        
        return result.toString();
    }
}
