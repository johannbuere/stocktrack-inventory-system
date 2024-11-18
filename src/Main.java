import java.io.*;
import java.util.*;

public class Main {

    private static final String FILE_NAME = "inventory.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        while (true) {
            System.out.println("\n=== StockTrack Supermarket Inventory System ===");
            System.out.println("1. Add Item");
            System.out.println("2. View Inventory");
            System.out.println("3. Update Item");
            System.out.println("4. Delete Item");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> addItem(scanner);
                case 2 -> viewInventory();
                case 3 -> updateItem(scanner);
                case 4 -> deleteItem(scanner);
                case 5 -> {
                    System.out.println("Exiting... Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addItem(Scanner scanner) {
        try (FileWriter fw = new FileWriter(FILE_NAME, true)) {
            System.out.print("Enter item name: ");
            String name = scanner.nextLine();
            System.out.print("Enter quantity: ");
            int quantity = scanner.nextInt();
            System.out.print("Enter price: ");
            double price = scanner.nextDouble();
            scanner.nextLine(); // Consume newline

            fw.write(name + "," + quantity + "," + price + "\n");
            System.out.println("Item added successfully!");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    private static void viewInventory() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            System.out.println("\n=== Inventory List ===");
            System.out.printf("%-20s %-10s %-10s\n", "Item Name", "Quantity", "Price");
            System.out.println("----------------------------------------");
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                System.out.printf("%-20s %-10s %-10s\n", data[0], data[1], data[2]);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Inventory file not found. Add items to create the file.");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private static void updateItem(Scanner scanner) {
        List<String> inventory = new ArrayList<>();
        boolean found = false;

        System.out.print("Enter the item name to update: ");
        String itemName = scanner.nextLine();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equalsIgnoreCase(itemName)) {
                    System.out.print("Enter new quantity: ");
                    int newQuantity = scanner.nextInt();
                    System.out.print("Enter new price: ");
                    double newPrice = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    inventory.add(data[0] + "," + newQuantity + "," + newPrice);
                    found = true;
                } else {
                    inventory.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        if (found) {
            try (FileWriter fw = new FileWriter(FILE_NAME)) {
                for (String record : inventory) {
                    fw.write(record + "\n");
                }
                System.out.println("Item updated successfully!");
            } catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
            }
        } else {
            System.out.println("Item not found.");
        }
    }

    private static void deleteItem(Scanner scanner) {
        List<String> inventory = new ArrayList<>();
        boolean found = false;

        System.out.print("Enter the item name to delete: ");
        String itemName = scanner.nextLine();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (!data[0].equalsIgnoreCase(itemName)) {
                    inventory.add(line);
                } else {
                    found = true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        if (found) {
            try (FileWriter fw = new FileWriter(FILE_NAME)) {
                for (String record : inventory) {
                    fw.write(record + "\n");
                }
                System.out.println("Item deleted successfully!");
            } catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
            }
        } else {
            System.out.println("Item not found.");
        }
    }
}
