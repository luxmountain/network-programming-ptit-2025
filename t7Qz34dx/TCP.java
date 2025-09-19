package t7Qz34dx;
import java.io.Serializable;

public class TCP {
    public static class Product implements Serializable {
        private static final long serialVersionUID = 20231107L;
        private int id;
        private String name;
        private double price;
        private int discount;
        
        // Default constructor
        public Product() {}
        
        // Constructor with parameters
        public Product(int id, String name, double price, int discount) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.discount = discount;
        }
        
        // Getters
        public int getId() { return id; }
        public String getName() { return name; }
        public double getPrice() { return price; }
        public int getDiscount() { return discount; }
        
        // Setters
        public void setId(int id) { this.id = id; }
        public void setName(String name) { this.name = name; }
        public void setPrice(double price) { this.price = price; }
        public void setDiscount(int discount) { this.discount = discount; }
        
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
}