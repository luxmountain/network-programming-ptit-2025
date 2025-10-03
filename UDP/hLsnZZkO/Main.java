package UDP.hLsnZZkO;

import java.io.*;
import java.math.BigInteger;
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
            String qCode = "hLsnZZkO";
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
            
            // Parse requestId and the two large numbers
            String[] parts = receivedMessage.split(";");
            if (parts.length != 3) {
                System.err.println("Invalid message format received. Expected format: requestId;a;b");
                return;
            }
            
            String requestId = parts[0];
            String numberA = parts[1];
            String numberB = parts[2];
            
            System.out.println("RequestId: " + requestId);
            System.out.println("Number A: " + numberA);
            System.out.println("Number B: " + numberB);
            
            // c. Calculate sum and difference using BigInteger for large numbers
            try {
                BigInteger bigA = new BigInteger(numberA);
                BigInteger bigB = new BigInteger(numberB);
                
                // Calculate sum: a + b
                BigInteger sum = bigA.add(bigB);
                
                // Calculate difference: a - b
                BigInteger difference = bigA.subtract(bigB);
                
                System.out.println("Sum (A + B): " + sum);
                System.out.println("Difference (A - B): " + difference);
                
                // Send sum and difference back to server
                String responseMessage = requestId + ";" + sum + "," + difference;
                byte[] responseData = responseMessage.getBytes();
                DatagramPacket responsePacket = new DatagramPacket(
                    responseData, responseData.length, serverAddress, serverPort
                );
                socket.send(responsePacket);
                System.out.println("Sent response: " + responseMessage);
                
            } catch (NumberFormatException e) {
                System.err.println("Error parsing numbers: " + e.getMessage());
                return;
            }
            
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
