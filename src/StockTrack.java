import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class StockTrack{
    //Attributes
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

    //Constructor
    public StockTrack(){
        //logo
        logo = new ImageIcon("logo.jpg");                               //change this for logo

        //frame stuff
        mainFrame = new JFrame("StockTrack: Supermarket Iventory System");
        mainFrame.setSize(700,500);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setIconImage(logo.getImage());                                
        mainFrame.setLayout(new BorderLayout());                                //N,S,E,W,C 
        mainFrame.getContentPane().setBackground(new Color(0xf5f0e1));
        
        //panel north (TOP)
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(0xf5f0e1));
        topPanel.setLayout(new GridLayout(4,2));

        //panel south (bottom)
        JPanel botPanel = new JPanel();
        botPanel.setBackground(new Color(0xf5f0e1));
        
        topPanel.add(new JLabel("\tItem Name:"));
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

        //butts stuff
        addButton = new JButton("Add Item");
        updateButton = new JButton("Update Item");
        deleteButton = new JButton("Delete Item");
        viewButton = new JButton("View Inventory");
        
        topPanel.add(addButton);
        topPanel.add(updateButton);
        botPanel.add(viewButton);
        botPanel.add(deleteButton);

        //add panel to main frame
        mainFrame.add(topPanel, BorderLayout.NORTH);
        mainFrame.add(botPanel, BorderLayout.SOUTH);

        //inventory table
        String column[] = {"Item Name", "Quantity", "Price"};
        tableModel = new DefaultTableModel(column, 0);
        invTable = new JTable(tableModel);
        invTable.setEnabled(false);                             //sets the table to view only, bawal touch 
        JScrollPane tablePane = new JScrollPane(invTable);
        

        //add table to main frame
        mainFrame.add(tablePane);

        //buttun actions (gamit nalang nung lambda  expression kesa nung traditional)
        addButton.addActionListener(click -> addItem());
        updateButton.addActionListener(click -> updateItem());
        deleteButton.addActionListener(click -> deleteItem());
        viewButton.addActionListener(click -> viewInventory());
        
        //make the main frame visible
        mainFrame.setVisible(true);
    }

    //method for adding item
    private void addItem(){
        try(FileWriter file = new FileWriter(FILE, true)){
            String itemName = productField.getText();
            int quantity = Integer.parseInt(quantityField.getText());
            double price = Double.parseDouble(priceField.getText());

            file.write(itemName + "," + quantity + "," + price + "\n");
            JOptionPane.showMessageDialog(mainFrame, "Item added successfully!");
        }
        catch(IOException exc){
            JOptionPane.showMessageDialog(mainFrame, "Error writing to file: " + exc.getMessage());
        }
    }
    //method for updating item
    private void updateItem(){
        java.util.List<String> inventory = new ArrayList<>();
        boolean found = false;
        String itemName = productField.getText();

        try(BufferedReader br = new BufferedReader(new FileReader(FILE))){
            String line;

            while((line = br.readLine()) != null){
                String data[] = line.split(",");

                if(data[0].equalsIgnoreCase(itemName)){
                    int newQuantity = Integer.parseInt(quantityField.getText());
                    double newPrice = Double.parseDouble(priceField.getText());
                    inventory.add(data[0] + "," + newQuantity + "," + newPrice);
                    found = true;
                }
                else{
                    inventory.add(line);
                }
            }
        }
        catch(IOException exc){
            JOptionPane.showMessageDialog(mainFrame, "Error reading file: " + exc.getMessage());
        }

        if(found){
            try(FileWriter fw = new FileWriter(FILE)){
                for(String record : inventory){
                    fw.write(record + "\n");
                }

                JOptionPane.showMessageDialog(mainFrame, "Item updated successfully!");

            }
            catch(IOException exc){
                JOptionPane.showMessageDialog(mainFrame, "Error writing to file: " + exc.getMessage());
            }
        }
        else{
            JOptionPane.showMessageDialog(mainFrame, "Item not found.");
        }
    }   

    //method for deleting item
    private void deleteItem(){
        java.util.List<String> inventory = new ArrayList<>();
        boolean found = false;
        String item = productField.getText();

        try (BufferedReader read = new BufferedReader(new FileReader(FILE))){
            String line;

            while((line = read.readLine()) != null){
                String data[] = line.split(",");

                if(!data[0].equalsIgnoreCase(item)){
                    inventory.add(line);
                }else{
                    found = true;
                }
            }
        }
        catch(IOException exc){
            JOptionPane.showMessageDialog(mainFrame, "Error reading file: " + exc.getMessage());
        }

        if(found){
            try(FileWriter file = new FileWriter(FILE)){
                for(String record : inventory){
                    file.write(record + "\n");
                }
                JOptionPane.showMessageDialog(mainFrame, "Item deleted successfully!");
            }
            catch(IOException exc){
                JOptionPane.showMessageDialog(mainFrame, "Error writing to file: " + exc.getMessage());
            }
        }
        else{
            JOptionPane.showMessageDialog(mainFrame, "Item not found.");
        }
    }

    //method for viewing item
    private void viewInventory(){
        tableModel.setRowCount(0);          

        try(BufferedReader read = new BufferedReader(new FileReader(FILE))){
            String line;
            while((line = read.readLine()) != null){
                String data[] = line.split(",");
                if(data.length == 3){
                    tableModel.addRow(data);                    //add stuff into table
                }
            }
        } 
        catch(FileNotFoundException exc){
            JOptionPane.showMessageDialog(mainFrame, "Inventory file not found. Add items to create the file.");
        }
        catch(IOException exc){
            JOptionPane.showMessageDialog(mainFrame, "Error reading file: " + exc.getMessage());
        }
    }

    //Main method
    public static void main(String[] args) {
        new StockTrack();
    }
}