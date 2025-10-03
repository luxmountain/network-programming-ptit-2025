package KgEgIWvm;

import java.io.*;
import java.net.*;

public class KgEgIWvm {
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
            String studentCode = "B22DCVT445";
            String qCode = "KgEgIWvm";
            String message = studentCode + ";" + qCode;
            dos.writeUTF(message);
            dos.flush();
            System.out.println("Sent: " + message);
            
            // b. Receive array size and array elements
            int n = dis.readInt();
            System.out.println("Array size: " + n);
            
            int[] array = new int[n];
            System.out.print("Array elements: ");
            for (int i = 0; i < n; i++) {
                array[i] = dis.readInt();
                System.out.print(array[i] + " ");
            }
            System.out.println();
            
            // c. Calculate sum, average, and variance
            // Calculate sum
            int sum = 0;
            for (int value : array) {
                sum += value;
            }
            
            // Calculate average
            float average = (float) sum / n;
            
            // Calculate variance
            float variance = 0;
            for (int value : array) {
                variance += (value - average) * (value - average);
            }
            variance = variance / n;
            
            // Display results
            System.out.println("Sum: " + sum);
            System.out.println("Average: " + average);
            System.out.println("Variance: " + variance);
            
            // Send results to server
            dos.writeInt(sum);        // Send sum as integer
            dos.writeFloat(average);  // Send average as float
            dos.writeFloat(variance); // Send variance as float
            dos.flush();
            
            System.out.println("Successfully sent results to server!");
            
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
