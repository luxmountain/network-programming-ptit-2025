import java.io.*;
import java.net.*;

class Product implements Serializable {
    private static final long serialVersionUID = 20231107L;
    private int id;
    private String name;
    private double price;
    private int discount;

    // Default constructor
    public Product() {
    }

    // Constructor with parameters
    public Product(int id, String name, double price, int discount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.discount = discount;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getDiscount() {
        return discount;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                '}';
    }
}

public class t7Qz34dx {
    public static void main(String[] args) {
        String serverAddress = "203.162.10.109";
        int port = 2209;
        String studentCode = "B22DCVT445"; // Replace with your actual student code
        String qCode = "t7Qz34dx";

        Socket socket = null;
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;

        try {
            // Establish connection to server
            socket = new Socket(serverAddress, port);
            System.out.println("Connected to server at " + serverAddress + ":" + port);

            // Create object output and input streams (output first to avoid blocking)
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.flush(); // Ensure header is sent immediately
            ois = new ObjectInputStream(socket.getInputStream());

            // a. Send student code and question code in format "studentCode;qCode"
            String message = studentCode + ";" + qCode;
            System.out.println("Sending: " + message);
            oos.writeObject(message);
            oos.flush();
            System.out.println("Sent successfully: " + message);

            // b. Receive a Product object from server
            System.out.println("Waiting to receive product from server...");
            Product product = (Product) ois.readObject();
            System.out.println("Received product: " + product);
            System.out.println("Product price: " + product.getPrice());

            // c. Calculate discount based on sum of digits in integer part of price
            int integerPart = (int) product.getPrice();
            int discount = calculateSumOfDigits(integerPart);

            System.out.println("Integer part of price: " + integerPart);
            System.out.println("Calculated discount (sum of digits): " + discount);

            // Set the discount value and send the product back to server
            product.setDiscount(discount);
            System.out.println("Sending product back with discount: " + product);
            oos.writeObject(product);
            oos.flush();
            System.out.println("Product sent successfully with discount: " + discount);

            System.out.println("Operations completed successfully!");

        } catch (IOException e) {
            System.err.println("IO Error during communication with server: " + e.getMessage());
            System.err.println("Error type: " + e.getClass().getSimpleName());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("Class not found error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // d. Close connection and terminate
            try {
                if (ois != null)
                    ois.close();
                if (oos != null)
                    oos.close();
                if (socket != null)
                    socket.close();
                System.out.println("Connection closed.");
            } catch (IOException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    // Helper method to calculate sum of digits in a number
    private static int calculateSumOfDigits(int number) {
        int sum = 0;
        number = Math.abs(number); // Handle negative numbers
        while (number > 0) {
            sum += number % 10;
            number /= 10;
        }
        return sum;
    }
}
