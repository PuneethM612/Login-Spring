package com.bnpp.pb.lynx.controller;

import com.bnpp.pb.lynx.model.LoginRequest;
import com.bnpp.pb.lynx.model.LoginResponse;
import com.bnpp.pb.lynx.model.RegisterResponse;
import com.bnpp.pb.lynx.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    // Web API endpoints for REST clients like Postman
    @PostMapping("/api/login")
    @ResponseBody
    public ResponseEntity<?> loginApi(@RequestBody LoginRequest loginRequest) {
        if (loginService.authenticateUser(loginRequest)) {
            return ResponseEntity.ok(new LoginResponse("Successful login"));
        } else {
            return ResponseEntity.status(401).body(new LoginResponse("Invalid username or password"));
        }
    }
    
    @PostMapping("/api/register")
    @ResponseBody
    public ResponseEntity<?> registerApi(@RequestBody LoginRequest loginRequest) {
        if (loginService.userExists(loginRequest.getUsername())) {
            return ResponseEntity.status(409).body(new RegisterResponse("Username already exists"));
        }
        
        boolean registered = loginService.registerUser(loginRequest);
        if (registered) {
            return ResponseEntity.ok(new RegisterResponse("Registration successful"));
        } else {
            return ResponseEntity.status(500).body(new RegisterResponse("Registration failed"));
        }
    }
    
    // Web UI endpoints
    @GetMapping("/")
    public String index() {
        return "index";
    }
    
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }
    
    @PostMapping("/login")
    public ModelAndView processLogin(@RequestParam String username, @RequestParam String password) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(username);
        loginRequest.setPassword(password);
        
        if (loginService.authenticateUser(loginRequest)) {
            ModelAndView modelAndView = new ModelAndView("welcome");
            modelAndView.addObject("username", username);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("login");
            modelAndView.addObject("error", "Invalid username or password");
            return modelAndView;
        }
    }
    
    @PostMapping("/register")
    public ModelAndView processRegistration(@RequestParam String username, @RequestParam String password) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(username);
        loginRequest.setPassword(password);
        
        if (loginService.userExists(username)) {
            ModelAndView modelAndView = new ModelAndView("register");
            modelAndView.addObject("error", "Username already exists");
            return modelAndView;
        }
        
        boolean registered = loginService.registerUser(loginRequest);
        if (registered) {
            ModelAndView modelAndView = new ModelAndView("register-success");
            modelAndView.addObject("username", username);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("register");
            modelAndView.addObject("error", "Registration failed");
            return modelAndView;
        }
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<LoginResponse> handleException(Exception e) {
        return ResponseEntity.status(500).body(new LoginResponse("Operation failed: " + e.getMessage()));
    }
} 