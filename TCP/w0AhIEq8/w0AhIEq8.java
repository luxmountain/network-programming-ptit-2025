import java.io.*;
import java.net.*;

public class w0AhIEq8 {
    public static void main(String[] args) {
        String serverAddress = "203.162.10.109";
        int port = 2207;
        String studentCode = "B22DCVT445"; // Replace with your actual student code
        String qCode = "w0AhIEq8";

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

            // b. Receive an integer from server
            System.out.println("Waiting to receive integer from server...");
            int receivedNumber = dataInputStream.readInt();
            System.out.println("Received decimal number: " + receivedNumber);

            // c. Convert to octal (base 8) and hexadecimal (base 16)
            String octalResult = Integer.toOctalString(receivedNumber);
            String hexResult = Integer.toHexString(receivedNumber).toUpperCase();
            
            System.out.println("Decimal: " + receivedNumber);
            System.out.println("Octal (base 8): " + octalResult);
            System.out.println("Hexadecimal (base 16): " + hexResult);

            // Send both results together in format "octal;hexadecimal"
            String combinedResult = octalResult + ";" + hexResult;
            System.out.println("Sending combined result: " + combinedResult);
            dataOutputStream.writeUTF(combinedResult);
            dataOutputStream.flush();
            System.out.println("Combined result sent successfully: " + combinedResult);

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
}
