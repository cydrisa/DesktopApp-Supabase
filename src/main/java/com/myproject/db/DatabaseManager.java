package com.myproject.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseManager {
    
    public static Connection getConnection() {
        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");

        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Database connection failed!");
            e.printStackTrace();
            return null;
        }
    }

    // NEW METHOD: This actually saves the data to the database
    public static boolean addStudent(String name, String grade) {
        String sql = "INSERT INTO students (name, grade) VALUES (?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            if (conn == null) return false;
            
            pstmt.setString(1, name);
            pstmt.setString(2, grade);
            pstmt.executeUpdate(); // Runs the SQL command
            return true;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}