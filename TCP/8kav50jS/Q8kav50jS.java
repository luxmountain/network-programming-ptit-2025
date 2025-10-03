import java.io.*;
import java.net.*;

public class Q8kav50jS {
    public static void main(String[] args) {
        String serverAddress = "203.162.10.109";
        int port = 2207;
        String studentCode = "B22DCVT445"; // Replace with your actual student code
        String qCode = "8kav50jS";

        Socket socket = null;
        DataInputStream dataInputStream = null;
        DataOutputStream dataOutputStream = null;

        try {
            // Establish connection to server
            socket = new Socket(serverAddress, port);
            System.out.println("Connected to server at " + serverAddress + ":" + port);

            // Create data input and output streams
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());

            // a. Send student code and question code in format "studentCode;qCode"
            String message = studentCode + ";" + qCode;
            System.out.println("Sending: " + message);
            dataOutputStream.writeUTF(message);
            dataOutputStream.flush();
            System.out.println("Sent successfully: " + message);

            // b. Receive array of integers as string from server
            System.out.println("Waiting to receive array string from server...");
            String receivedData = dataInputStream.readUTF();
            System.out.println("Received array: " + receivedData);

            // c. Calculate direction changes and total variation
            int[] results = calculateDirectionChangesAndVariation(receivedData);
            int directionChanges = results[0];
            int totalVariation = results[1];

            System.out.println("Direction changes: " + directionChanges);
            System.out.println("Total variation: " + totalVariation);

            // Send direction changes first
            System.out.println("Sending direction changes: " + directionChanges);
            dataOutputStream.writeInt(directionChanges);
            dataOutputStream.flush();
            System.out.println("Direction changes sent successfully: " + directionChanges);

            // Send total variation second
            System.out.println("Sending total variation: " + totalVariation);
            dataOutputStream.writeInt(totalVariation);
            dataOutputStream.flush();
            System.out.println("Total variation sent successfully: " + totalVariation);

            System.out.println("Operations completed successfully!");

        } catch (IOException e) {
            System.err.println("IO Error during communication with server: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // d. Close connection and terminate
            try {
                if (dataInputStream != null)
                    dataInputStream.close();
                if (dataOutputStream != null)
                    dataOutputStream.close();
                if (socket != null)
                    socket.close();
                System.out.println("Connection closed.");
            } catch (IOException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    // Helper method to calculate direction changes and total variation
    private static int[] calculateDirectionChangesAndVariation(String data) {
        String[] numberStrings = data.split(",");
        int[] numbers = new int[numberStrings.length];
        
        // Parse numbers
        for (int i = 0; i < numberStrings.length; i++) {
            numbers[i] = Integer.parseInt(numberStrings[i].trim());
        }
        
        System.out.println("Parsed array: " + java.util.Arrays.toString(numbers));
        
        int directionChanges = 0;
        int totalVariation = 0;
        
        if (numbers.length < 2) {
            return new int[]{0, 0};
        }
        
        // Calculate total variation (sum of absolute differences)
        for (int i = 1; i < numbers.length; i++) {
            totalVariation += Math.abs(numbers[i] - numbers[i-1]);
        }
        
        // Calculate direction changes
        if (numbers.length >= 3) {
            boolean lastDirection = numbers[1] > numbers[0]; // true = increasing, false = decreasing
            
            for (int i = 2; i < numbers.length; i++) {
                boolean currentDirection = numbers[i] > numbers[i-1];
                
                // If direction changes, increment counter
                if (currentDirection != lastDirection) {
                    directionChanges++;
                    System.out.println("Direction change at position " + i + 
                                     ": from " + (lastDirection ? "increasing" : "decreasing") + 
                                     " to " + (currentDirection ? "increasing" : "decreasing"));
                }
                
                lastDirection = currentDirection;
            }
        }
        
        System.out.println("Analysis complete:");
        System.out.println("- Direction changes: " + directionChanges);
        System.out.println("- Total variation: " + totalVariation);
        
        return new int[]{directionChanges, totalVariation};
    }
}
