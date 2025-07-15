import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

// ------------------------ Product Class ------------------------
class Product {
    int id;
    String name;
    double price;

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}

// ------------------------ Cart Node (Linked List) ------------------------
class CartNode {
    Product product;
    int quantity;
    CartNode next;

    public CartNode(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.next = null;
    }
}

// ------------------------ Shopping Cart Class ------------------------
class ShoppingCart {
    CartNode head = null;

    // Add item to cart
    void addToCart(Product[] products, int productId, int quantity) {
        Product selected = null;
        for (Product p : products) {
            if (p.id == productId) {
                selected = p;
                break;
            }
        }
        if (selected == null) {
            System.out.println("Product not found.");
            return;
        }

        CartNode current = head;
        while (current != null) {
            if (current.product.id == productId) {
                current.quantity += quantity;
                return;
            }
            current = current.next;
        }

        CartNode newNode = new CartNode(selected, quantity);
        newNode.next = head;
        head = newNode;
    }

    // Remove item from cart
    void removeFromCart(int productId) {
        CartNode current = head, prev = null;
        while (current != null && current.product.id != productId) {
            prev = current;
            current = current.next;
        }
        if (current == null) {
            System.out.println("Item not found in cart.");
            return;
        }
        if (prev == null) {
            head = current.next;
        } else {
            prev.next = current.next;
        }
    }

    // Update quantity
    void updateQuantity(int productId, int newQuantity) {
        CartNode current = head;
        while (current != null) {
            if (current.product.id == productId) {
                current.quantity = newQuantity;
                return;
            }
            current = current.next;
        }
        System.out.println("Item not found in cart.");
    }

    // Calculate total
    double calculateTotal() {
        double total = 0;
        CartNode current = head;
        while (current != null) {
            total += current.product.price * current.quantity;
            current = current.next;
        }
        return total;
    }

    // Display cart
    void displayCart() {
        if (head == null) {
            System.out.println("Cart is empty.");
            return;
        }
        System.out.println("------ Cart Items ------");
        CartNode current = head;
        while (current != null) {
            System.out.println(current.product.name + " (x" + current.quantity + ") = ₹" +
                    (current.product.price * current.quantity));
            current = current.next;
        }
        System.out.println("Total: ₹" + calculateTotal());
    }

    // Save to file
    void saveToFile() {
        try (FileWriter writer = new FileWriter("cart.txt")) {
            CartNode current = head;
            while (current != null) {
                writer.write(current.product.id + "," + current.product.name + "," +
                        current.product.price + "," + current.quantity + "\n");
                current = current.next;
            }
            System.out.println("Cart saved to cart.txt");
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }
}

// ------------------------ Main Class ------------------------
public class ShoppingCartSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ShoppingCart cart = new ShoppingCart();

        // Product array
        Product[] products = {
            new Product(1, "Apple", 30.5),
            new Product(2, "Banana", 10.0),
            new Product(3, "Milk", 45.0),
            new Product(4, "Bread", 25.0),
            new Product(5, "Eggs", 60.0)
        };

        int choice;
        do {
            System.out.println("\n--- Shopping Cart Menu ---");
            System.out.println("1. Add Item");
            System.out.println("2. Remove Item");
            System.out.println("3. Update Quantity");
            System.out.println("4. Show Cart");
            System.out.println("5. Save Cart");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            int id, qty;
            switch (choice) {
                case 1:
                    System.out.print("Enter Product ID and Quantity: ");
                    id = sc.nextInt();
                    qty = sc.nextInt();
                    cart.addToCart(products, id, qty);
                    break;
                case 2:
                    System.out.print("Enter Product ID to remove: ");
                    id = sc.nextInt();
                    cart.removeFromCart(id);
                    break;
                case 3:
                    System.out.print("Enter Product ID and New Quantity: ");
                    id = sc.nextInt();
                    qty = sc.nextInt();
                    cart.updateQuantity(id, qty);
                    break;
                case 4:
                    cart.displayCart();
                    break;
                case 5:
                    cart.saveToFile();
                    break;
                case 6:
                    System.out.println("Thank you for shopping!");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 6);

        sc.close();
    }
}
