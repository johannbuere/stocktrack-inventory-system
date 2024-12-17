/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reception;

/**
 *
 * @author Joy De Castro
 */
import javax.swing.*;

public class Reception extends JFrame {

    public Reception() {
        // Set frame properties
        setTitle("Reception");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Example content
        JLabel welcomeLabel = new JLabel("Welcome to Reception!", SwingConstants.CENTER);
        welcomeLabel.setFont(new java.awt.Font("Google Sans Display", java.awt.Font.BOLD, 24));
        
        // Add to frame
        add(welcomeLabel);
    }
}


