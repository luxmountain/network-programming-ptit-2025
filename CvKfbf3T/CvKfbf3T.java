package CvKfbf3T;

import java.io.*;
import java.net.*;
import java.util.*;

public class CvKfbf3T {
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
            String studentCode = "B22DCVT445";
            String qCode = "CvKfbf3T";
            String message = studentCode + ";" + qCode;
            writer.write(message);
            writer.newLine();
            writer.flush();
            
            System.out.println("Sent: " + message);
            
            // b. Receive string containing words from server
            String wordsString = reader.readLine();
            System.out.println("Received: " + wordsString);
            
            // c. Sort words by length, then by order of appearance
            String[] words = wordsString.split("\\s+");
            
            // Create a list of words with their original indices for stable sorting
            List<WordWithIndex> wordList = new ArrayList<>();
            for (int i = 0; i < words.length; i++) {
                wordList.add(new WordWithIndex(words[i], i));
            }
            
            // Sort by length first, then by original index for stable sorting
            Collections.sort(wordList, new Comparator<WordWithIndex>() {
                @Override
                public int compare(WordWithIndex w1, WordWithIndex w2) {
                    // First compare by length
                    int lengthCompare = Integer.compare(w1.word.length(), w2.word.length());
                    if (lengthCompare != 0) {
                        return lengthCompare;
                    }
                    // If lengths are equal, compare by original index (order of appearance)
                    return Integer.compare(w1.originalIndex, w2.originalIndex);
                }
            });
            
            // Create result string with comma separation
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < wordList.size(); i++) {
                result.append(wordList.get(i).word);
                if (i < wordList.size() - 1) {
                    result.append(", ");
                }
            }
            
            String resultString = result.toString();
            System.out.println("Sorted result: " + resultString);
            
            // Send sorted result to server
            writer.write(resultString);
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
    
    // Helper class to store word with its original index
    static class WordWithIndex {
        String word;
        int originalIndex;
        
        public WordWithIndex(String word, int originalIndex) {
            this.word = word;
            this.originalIndex = originalIndex;
        }
    }
}
