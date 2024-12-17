import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class StockTrack {
    //attributes
    private JFrame mainFrame;
    private ImageIcon logo;
    private static final String FILE = "inventory.txt";

    private JTextField productField;
    private JTextField quantityField;
    private JTextField priceField;

    private JButton addButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton viewButton;

    private DefaultTableModel tableModel;
    private JTable invTable;

    //constructor
    public StockTrack() {
        //logo
        logo = new ImageIcon("logo.jpg"); //change this for your logo image

        //frame setup
        mainFrame = new JFrame("StockTrack: Supermarket Inventory System");
        mainFrame.setSize(700, 500);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setIconImage(logo.getImage());
        mainFrame.setLayout(new BorderLayout());

        //top panel 
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(4, 2));

        topPanel.add(new JLabel("Item Name:"));
        productField = new JTextField();
        productField.setBorder(BorderFactory.createLineBorder(new Color(0x1e3d59), 2));
        topPanel.add(productField);

        topPanel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        quantityField.setBorder(BorderFactory.createLineBorder(new Color(0x1e3d59), 2));
        topPanel.add(quantityField);

        topPanel.add(new JLabel("Price:"));
        priceField = new JTextField();
        priceField.setBorder(BorderFactory.createLineBorder(new Color(0x1e3d59), 2));
        topPanel.add(priceField);

        //buttons
        addButton = new JButton("Add Item");
        updateButton = new JButton("Update Item");
        deleteButton = new JButton("Delete Item");
        viewButton = new JButton("View Inventory");

        topPanel.add(addButton);
        topPanel.add(updateButton);

        JPanel botPanel = new JPanel();
        botPanel.add(viewButton);
        botPanel.add(deleteButton);

        //table for inventory
        String column[] = {"Item Name", "Quantity", "Price", "Product Key"};
        tableModel = new DefaultTableModel(column, 0);
        invTable = new JTable(tableModel);
        invTable.setEnabled(false); // View-only table
        JScrollPane tablePane = new JScrollPane(invTable);

        //add stuff to frame
        mainFrame.add(topPanel, BorderLayout.NORTH);
        mainFrame.add(botPanel, BorderLayout.SOUTH);
        mainFrame.add(tablePane, BorderLayout.CENTER);

        //button actions lambdas nalang
        addButton.addActionListener(click -> addItem());
        updateButton.addActionListener(click -> updateItem());
        deleteButton.addActionListener(click -> deleteItem());
        viewButton.addActionListener(click -> viewInventory());

        //make the frame visible
        mainFrame.setVisible(true);
    }

    //method to add item
    private void addItem() {
        try (FileWriter file = new FileWriter(FILE, true)) {
            String itemName = productField.getText();
            int quantity = Integer.parseInt(quantityField.getText());
            double price = Double.parseDouble(priceField.getText());

            String productKey = generateProductKey(); // Generate 6-digit product key

            // Write the data to the file
            file.write(itemName + "," + quantity + "," + price + "," + productKey + "\n");

            JOptionPane.showMessageDialog(mainFrame, "Item added successfully!");
        } catch (IOException | NumberFormatException exc) {
            JOptionPane.showMessageDialog(mainFrame, "Error: " + exc.getMessage());
        }
    }

    //method to view inventory
    private void viewInventory() {
        tableModel.setRowCount(0); // Clear table

        try (BufferedReader read = new BufferedReader(new FileReader(FILE))) {
            String line;

            while ((line = read.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) { // Ensure all fields are present
                    tableModel.addRow(data);
                }
            }
        } catch (FileNotFoundException exc) {
            JOptionPane.showMessageDialog(mainFrame, "Inventory file not found. Add items to create the file.");
        } catch (IOException exc) {
            JOptionPane.showMessageDialog(mainFrame, "Error reading file: " + exc.getMessage());
        }
    }

    //method to update item
    private void updateItem() {
        List<String> inventory = new ArrayList<>();
        boolean found = false;
        String itemName = productField.getText();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if (data[0].equalsIgnoreCase(itemName)) {
                    int newQuantity = Integer.parseInt(quantityField.getText());
                    double newPrice = Double.parseDouble(priceField.getText());
                    inventory.add(data[0] + "," + newQuantity + "," + newPrice + "," + data[3]); // Keep product key
                    found = true;
                } else {
                    inventory.add(line);
                }
            }
        } catch (IOException exc) {
            JOptionPane.showMessageDialog(mainFrame, "Error reading file: " + exc.getMessage());
        }

        if (found) {
            try (FileWriter fw = new FileWriter(FILE)) {
                for (String record : inventory) {
                    fw.write(record + "\n");
                }
                JOptionPane.showMessageDialog(mainFrame, "Item updated successfully!");
            } catch (IOException exc) {
                JOptionPane.showMessageDialog(mainFrame, "Error writing to file: " + exc.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(mainFrame, "Item not found.");
        }
    }

    //method to delete item
    private void deleteItem() {
        List<String> inventory = new ArrayList<>();
        boolean found = false;
        String itemName = productField.getText();

        try (BufferedReader read = new BufferedReader(new FileReader(FILE))) {
            String line;

            while ((line = read.readLine()) != null) {
                String[] data = line.split(",");
                if (!data[0].equalsIgnoreCase(itemName)) {
                    inventory.add(line);
                } else {
                    found = true;
                }
            }
        } catch (IOException exc) {
            JOptionPane.showMessageDialog(mainFrame, "Error reading file: " + exc.getMessage());
        }

        if (found) {
            try (FileWriter file = new FileWriter(FILE)) {
                for (String record : inventory) {
                    file.write(record + "\n");
                }
                JOptionPane.showMessageDialog(mainFrame, "Item deleted successfully!");
            } catch (IOException exc) {
                JOptionPane.showMessageDialog(mainFrame, "Error writing to file: " + exc.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(mainFrame, "Item not found.");
        }
    }

    //method to generate a unique 6-digit product key
    private String generateProductKey() {
        Random rand = new Random();
        return String.format("%06d", rand.nextInt(1000000)); //generates a 6-digit random number
    }

    //main method for testing
    public static void main(String[] args) {
        new StockTrack();
    }
}
