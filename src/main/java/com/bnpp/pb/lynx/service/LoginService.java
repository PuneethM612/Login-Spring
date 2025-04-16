package com.bnpp.pb.lynx.service;

import com.bnpp.pb.lynx.model.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

@Service
public class LoginService {

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void initTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS login_details (id SERIAL PRIMARY KEY, username VARCHAR(255), password VARCHAR(255))";
        
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(createTableSQL);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create table", e);
        }
    }

    public void saveLoginDetails(LoginRequest loginRequest) {
        String insertSQL = "INSERT INTO login_details (username, password) VALUES (?, ?)";
        
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            
            preparedStatement.setString(1, loginRequest.getUsername());
            preparedStatement.setString(2, loginRequest.getPassword());
            preparedStatement.executeUpdate();
            
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save login details", e);
        }
    }
} 