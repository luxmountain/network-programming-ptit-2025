package UDP.n0YMSeI7;

import java.io.*;
import java.net.*;

public class Main {
    public static void main(String[] args) {
        DatagramSocket socket = null;
        
        try {
            // Create UDP socket
            socket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName("203.162.10.109");
            int serverPort = 2209;
            
            // a. Send student code and question code
            String studentCode = "B22DCVT445";
            String qCode = "n0YMSeI7";
            String message = ";" + studentCode + ";" + qCode;
            
            byte[] sendData = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(
                sendData, sendData.length, serverAddress, serverPort
            );
            socket.send(sendPacket);
            System.out.println("Sent: " + message);
            
            // b. Receive message from server (requestId + serialized Student object)
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);
            
            // Extract requestId (first 8 bytes) and Student object (remaining bytes)
            byte[] requestIdBytes = new byte[8];
            System.arraycopy(receiveData, 0, requestIdBytes, 0, 8);
            String requestId = new String(requestIdBytes).trim();
            
            // Deserialize Student object from remaining bytes
            byte[] studentBytes = new byte[receivePacket.getLength() - 8];
            System.arraycopy(receiveData, 8, studentBytes, 0, studentBytes.length);
            
            ByteArrayInputStream bais = new ByteArrayInputStream(studentBytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            Student student = (Student) ois.readObject();
            ois.close();
            
            System.out.println("RequestId: " + requestId);
            System.out.println("Received Student: " + student);
            
            // c. Process the student object
            // Normalize name: First letter uppercase, rest lowercase
            String normalizedName = normalizeName(student.getName());
            student.setName(normalizedName);
            
            // Generate email from name
            String email = generateEmail(normalizedName);
            student.setEmail(email);
            
            System.out.println("Processed Student: " + student);
            
            // Serialize the modified student object
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(student);
            oos.close();
            byte[] serializedStudent = baos.toByteArray();
            
            // Create response: requestId (8 bytes) + serialized Student
            byte[] responseData = new byte[8 + serializedStudent.length];
            
            // Copy requestId to first 8 bytes (pad with spaces if needed)
            String paddedRequestId = String.format("%-8s", requestId);
            System.arraycopy(paddedRequestId.getBytes(), 0, responseData, 0, 8);
            
            // Copy serialized student to remaining bytes
            System.arraycopy(serializedStudent, 0, responseData, 8, serializedStudent.length);
            
            // Send response to server
            DatagramPacket responsePacket = new DatagramPacket(
                responseData, responseData.length, serverAddress, serverPort
            );
            socket.send(responsePacket);
            System.out.println("Sent processed student back to server");
            
        } catch (IOException | ClassNotFoundException e) {
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
    
    /**
     * Normalize name: First letter uppercase, rest lowercase for each word
     */
    private static String normalizeName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return name;
        }
        
        String[] words = name.trim().split("\\s+");
        StringBuilder normalized = new StringBuilder();
        
        for (int i = 0; i < words.length; i++) {
            if (i > 0) {
                normalized.append(" ");
            }
            String word = words[i].toLowerCase();
            if (word.length() > 0) {
                normalized.append(Character.toUpperCase(word.charAt(0)));
                if (word.length() > 1) {
                    normalized.append(word.substring(1));
                }
            }
        }
        
        return normalized.toString();
    }
    
    /**
     * Generate email from name: lastname + first letters of other names + @ptit.edu.vn
     * Example: nguyen van tuan nam -> namnvt@ptit.edu.vn
     */
    private static String generateEmail(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "";
        }
        
        String[] words = name.trim().toLowerCase().split("\\s+");
        if (words.length == 0) {
            return "";
        }
        
        StringBuilder email = new StringBuilder();
        
        // Last word (given name) comes first
        email.append(words[words.length - 1]);
        
        // First letters of all other words (family name and middle names)
        for (int i = 0; i < words.length - 1; i++) {
            if (words[i].length() > 0) {
                email.append(words[i].charAt(0));
            }
        }
        
        email.append("@ptit.edu.vn");
        
        return email.toString();
    }
}
