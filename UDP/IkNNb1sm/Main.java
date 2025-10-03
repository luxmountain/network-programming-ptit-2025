package UDP.IkNNb1sm;

import java.io.*;
import java.net.*;

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
            String qCode = "IkNNb1sm";
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
            
            // c. Find max and min values
            String[] numberStrings = numbersString.split(",");
            if (numberStrings.length == 0) {
                System.err.println("No numbers found in the message");
                return;
            }
            
            int max = Integer.MIN_VALUE;
            int min = Integer.MAX_VALUE;
            
            System.out.print("Numbers: ");
            for (String numStr : numberStrings) {
                try {
                    int num = Integer.parseInt(numStr.trim());
                    System.out.print(num + " ");
                    
                    if (num > max) {
                        max = num;
                    }
                    if (num < min) {
                        min = num;
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Invalid number format: " + numStr);
                    return;
                }
            }
            System.out.println();
            
            System.out.println("Max: " + max);
            System.out.println("Min: " + min);
            
            // Send max and min back to server
            String responseMessage = requestId + ";" + max + "," + min;
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
