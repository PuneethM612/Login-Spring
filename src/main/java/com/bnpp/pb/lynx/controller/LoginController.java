package com.bnpp.pb.lynx.controller;

import com.bnpp.pb.lynx.model.LoginRequest;
import com.bnpp.pb.lynx.model.LoginResponse;
import com.bnpp.pb.lynx.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        // Store the login details in the database
        loginService.saveLoginDetails(loginRequest);
        
        // Return the success response
        return ResponseEntity.ok(new LoginResponse("Successful login"));
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<LoginResponse> handleException(Exception e) {
        return ResponseEntity.status(500).body(new LoginResponse("Login failed: " + e.getMessage()));
    }
} 