package tYPJqM3F;

import java.io.*;
import java.net.*;

public class tYPJqM3F {
    public static void main(String[] args) {
        Socket socket = null;
        DataInputStream dis = null;
        DataOutputStream dos = null;
        
        try {
            // Connect to server at port 2207
            socket = new Socket("203.162.10.109", 2207);
            
            // Setup streams for data exchange
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            
            // a. Send student code and question code
            String studentCode = "B22DCVT445"; // Replace with actual student code
            String qCode = "tYPJqM3F";
            String message = studentCode + ";" + qCode;
            dos.writeUTF(message);
            dos.flush();
            System.out.println("Sent: " + message);
            
            // b. Receive number of dice rolls
            int n = dis.readInt();
            System.out.println("Number of dice rolls: " + n);
            
            // Array to count occurrences of each dice value (1-6)
            int[] count = new int[7]; // index 0 unused, index 1-6 for values 1-6
            
            // Receive n dice values
            System.out.print("Dice values: ");
            for (int i = 0; i < n; i++) {
                int diceValue = dis.readInt();
                System.out.print(diceValue + " ");
                count[diceValue]++; // Increment count for corresponding value
            }
            System.out.println();
            
            // c. Calculate probabilities and send to server
            System.out.println("Probabilities of each value:");
            for (int i = 1; i <= 6; i++) {
                float probability = (float) count[i] / n;
                dos.writeFloat(probability);
                System.out.printf("Value %d: %.8f (%d/%d)\n", i, probability, count[i], n);
            }
            dos.flush();
            
            System.out.println("Successfully sent probabilities to server!");
            
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
}
