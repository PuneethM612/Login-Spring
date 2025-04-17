package com.bnpp.pb.lynx.service;

import com.bnpp.pb.lynx.model.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.*;

@Service
public class LoginService {

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void initTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS login_details (id SERIAL PRIMARY KEY, username VARCHAR(255) UNIQUE, password VARCHAR(255))";
        
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(createTableSQL);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create table", e);
        }
    }
    
    public boolean userExists(String username) {
        String checkUserSQL = "SELECT COUNT(*) FROM login_details WHERE username = ?";
        
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(checkUserSQL)) {
            
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Failed to check if user exists", e);
        }
        
        return false;
    }
    
    public boolean registerUser(LoginRequest loginRequest) {
        if (userExists(loginRequest.getUsername())) {
            return false;
        }
        
        String insertSQL = "INSERT INTO login_details (username, password) VALUES (?, ?)";
        
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            
            preparedStatement.setString(1, loginRequest.getUsername());
            preparedStatement.setString(2, loginRequest.getPassword());
            preparedStatement.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            throw new RuntimeException("Failed to register user", e);
        }
    }
    
    public boolean authenticateUser(LoginRequest loginRequest) {
        String authenticateSQL = "SELECT COUNT(*) FROM login_details WHERE username = ? AND password = ?";
        
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(authenticateSQL)) {
            
            preparedStatement.setString(1, loginRequest.getUsername());
            preparedStatement.setString(2, loginRequest.getPassword());
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Failed to authenticate user", e);
        }
        
        return false;
    }

    // This is kept for backward compatibility
    public void saveLoginDetails(LoginRequest loginRequest) {
        registerUser(loginRequest);
    }
} 