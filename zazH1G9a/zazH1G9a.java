// zazH1G9a - TCP - Byte Stream (InputStream/OutputStream)
import java.io.*;
import java.net.*;

public class zazH1G9a {
    public static void main(String[] args) {
        String host = "203.162.10.109"; // Server host
        int port = 2206; // Port as required
        String studentCode = "B22DCVT445"; // Replace with your student code
        String qCode = "zazH1G9a"; // Question code

        try (Socket socket = new Socket()) {
            // Connect to server with 5 second timeout
            socket.connect(new InetSocketAddress(host, port), 5000);
            socket.setSoTimeout(5000); // Timeout for reading data

            // Create byte streams for reading/writing (InputStream/OutputStream)
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            // (a) Send student code and question code in format "studentCode;qCode"
            String message = studentCode + ";" + qCode;
            outputStream.write(message.getBytes());
            outputStream.flush();
            System.out.println("Sent: " + message);

            // (b) Receive data from server as string of integers separated by "|"
            byte[] buffer = new byte[1024];
            int bytesRead = inputStream.read(buffer);
            String receivedData = new String(buffer, 0, bytesRead).trim();
            System.out.println("Received: " + receivedData);

            // (c) Calculate sum of integers in the string
            String[] numbers = receivedData.split("\\|");
            int sum = 0;
            
            System.out.print("Numbers: ");
            for (String numberStr : numbers) {
                try {
                    int number = Integer.parseInt(numberStr.trim());
                    sum += number;
                    System.out.print(number + " ");
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing number: " + numberStr);
                }
            }
            System.out.println();
            System.out.println("Sum: " + sum);

            // Send result sum to server
            String result = String.valueOf(sum);
            outputStream.write(result.getBytes());
            outputStream.flush();
            System.out.println("Sent sum: " + result);

            // (d) Close connection
            inputStream.close();
            outputStream.close();
            socket.close();
            System.out.println("Connection closed successfully.");

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}