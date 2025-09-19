package Nucf97Dj;
import java.io.*;
import java.net.*;

public class Nucf97Dj {
    public static void main(String[] args) {
        String serverAddress = "203.162.10.109";
        int port = 2207;
        String studentCode = "B22DCVT445"; // Replace with your actual student code
        String qCode = "Nucf97Dj";
        
        Socket socket = null;
        DataInputStream dis = null;
        DataOutputStream dos = null;
        
        try {
            // Establish connection to server
            socket = new Socket(serverAddress, port);
            System.out.println("Connected to server at " + serverAddress + ":" + port);
            
            // Create input and output streams
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            
            // a. Send student code and question code in format "studentCode;qCode"
            String message = studentCode + ";" + qCode;
            dos.writeUTF(message);
            dos.flush();
            System.out.println("Sent: " + message);
            
            // b. Receive two integers a and b from server
            int a = dis.readInt();
            int b = dis.readInt();
            System.out.println("Received a = " + a + ", b = " + b);
            
            // c. Calculate sum and product, then send them to server
            int sum = a + b;
            int product = a * b;
            
            // Send sum first
            dos.writeInt(sum);
            dos.flush();
            System.out.println("Sent sum: " + sum);
            
            // Send product second
            dos.writeInt(product);
            dos.flush();
            System.out.println("Sent product: " + product);
            
            System.out.println("Operations completed successfully!");
            
        } catch (IOException e) {
            System.err.println("Error during communication with server: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // d. Close connection and terminate
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
}
