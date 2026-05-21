package com.myproject.ui;

import com.myproject.db.DatabaseManager;
import javax.swing.*;
import java.awt.*;

public class MainApp {
    private JFrame frame;
    private JPanel cardPanel;
    private CardLayout cardLayout;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                MainApp window = new MainApp();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public MainApp() {
        frame = new JFrame("Student Directory App");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); 

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        frame.getContentPane().add(cardPanel, BorderLayout.CENTER);

        cardPanel.add(createHomeView(), "HomeView");
        cardPanel.add(createDataEntryView(), "DataEntryView");
    }

    private JPanel createHomeView() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JLabel welcomeLabel = new JLabel("Welcome to the Student Directory!");
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 22));
        
        JButton goToAddStudentBtn = new JButton("Add New Student");
        goToAddStudentBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        goToAddStudentBtn.addActionListener(e -> cardLayout.show(cardPanel, "DataEntryView"));

        panel.add(Box.createVerticalStrut(100));
        panel.add(welcomeLabel);
        panel.add(Box.createVerticalStrut(30));
        panel.add(goToAddStudentBtn);

        return panel;
    }

    private JPanel createDataEntryView() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Register New Student");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        // Create a sub-panel for the form so it aligns nicely
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        formPanel.setMaximumSize(new Dimension(300, 80));
        
        JLabel nameLabel = new JLabel("Student Name:");
        JTextField nameField = new JTextField();
        JLabel gradeLabel = new JLabel("Grade / Class:");
        JTextField gradeField = new JTextField();

        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(gradeLabel);
        formPanel.add(gradeField);

        JButton saveButton = new JButton("Save Student to Database");
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel resultLabel = new JLabel(" ");
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton backButton = new JButton("Back to Home");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // The Magic Happens Here!
        saveButton.addActionListener(e -> {
            String name = nameField.getText();
            String grade = gradeField.getText();

            if (name.isEmpty() || grade.isEmpty()) {
                resultLabel.setText("Please fill out all fields!");
                resultLabel.setForeground(Color.RED);
            } else {
                boolean success = DatabaseManager.addStudent(name, grade);
                if (success) {
                    resultLabel.setText("SUCCESS: " + name + " added to database!");
                    resultLabel.setForeground(new Color(0, 150, 0));
                    nameField.setText(""); // Clear the boxes for the next person
                    gradeField.setText("");
                } else {
                    resultLabel.setText("FAILED to save. Check console.");
                    resultLabel.setForeground(Color.RED);
                }
            }
        });

        backButton.addActionListener(e -> {
            cardLayout.show(cardPanel, "HomeView");
            resultLabel.setText(" "); // Reset label when leaving
        });

        panel.add(Box.createVerticalStrut(40));
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(formPanel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(saveButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(resultLabel);
        panel.add(Box.createVerticalStrut(30));
        panel.add(backButton);

        return panel;
    }
}