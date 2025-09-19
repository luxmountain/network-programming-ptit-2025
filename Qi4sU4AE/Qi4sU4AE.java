package Qi4sU4AE;

import java.io.*;
import java.net.*;

public class Qi4sU4AE {
    public static void main(String[] args) {
        Socket socket = null;
        BufferedReader reader = null;
        BufferedWriter writer = null;
        
        try {
            // Connect to server at port 2208
            socket = new Socket("203.162.10.109", 2208);
            socket.setSoTimeout(5000); // 5 second timeout
            
            // Create character streams
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            
            // a. Send student code and question code
            String studentCode = "B22DCVT445"; // Change to your student code
            String qCode = "Qi4sU4AE";
            String message = studentCode + ";" + qCode;
            writer.write(message);
            writer.newLine();
            writer.flush();
            
            System.out.println("Sent: " + message);
            
            // b. Receive string containing words from server
            String wordsString = reader.readLine();
            System.out.println("Received: " + wordsString);
            
            // c. Process the string: reverse words and apply RLE encoding
            String processedString = processString(wordsString);
            System.out.println("Processed result: " + processedString);
            
            // Send processed result to server
            writer.write(processedString);
            writer.newLine();
            writer.flush();
            
            System.out.println("Result sent successfully!");
            
        } catch (IOException e) {
            System.err.println("Connection error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // d. Close connection
            try {
                if (reader != null) reader.close();
                if (writer != null) writer.close();
                if (socket != null) socket.close();
                System.out.println("Connection closed.");
            } catch (IOException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
    
    // Method to process string: reverse words and apply RLE encoding
    private static String processString(String input) {
        String[] words = input.split("\\s+");
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            // Reverse the word
            String reversedWord = new StringBuilder(word).reverse().toString();
            // Apply RLE encoding to the reversed word
            String encodedWord = applyRLE(reversedWord);
            
            result.append(encodedWord);
            if (i < words.length - 1) {
                result.append(" ");
            }
        }
        
        return result.toString();
    }
    
    // Method to apply Run-Length Encoding (RLE)
    private static String applyRLE(String input) {
        if (input.length() == 0) {
            return input;
        }
        
        StringBuilder result = new StringBuilder();
        char currentChar = input.charAt(0);
        int count = 1;
        
        for (int i = 1; i < input.length(); i++) {
            if (input.charAt(i) == currentChar) {
                count++;
            } else {
                // Add the character and its count to result
                result.append(currentChar);
                if (count > 1) {
                    result.append(count);
                }
                
                // Update for next character
                currentChar = input.charAt(i);
                count = 1;
            }
        }
        
        // Add the last character and its count
        result.append(currentChar);
        if (count > 1) {
            result.append(count);
        }
        
        return result.toString();
    }
}
