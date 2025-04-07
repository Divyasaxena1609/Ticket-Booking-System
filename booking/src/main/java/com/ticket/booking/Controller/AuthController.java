package com.ticket.booking.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ticket.booking.CustomResponse.ResponseApi; 
import com.ticket.booking.Models.AuthModel;
import com.ticket.booking.Service.AuthService;

@RestController
@RequestMapping("/api/")
public class AuthController {

    @Autowired
    private AuthService authservice;

    // Fetch user by username
    @GetMapping("/register/{username}")
    public ResponseEntity<ResponseApi<AuthModel>> getUser(@PathVariable String username) {
        AuthModel registerUser = authservice.FindUserByUsername(username);
        if (registerUser != null) {
            ResponseApi<AuthModel> response = new ResponseApi<>("User Found", registerUser);
            return ResponseEntity.ok(response);
        } else {
            ResponseApi<AuthModel> response = new ResponseApi<>("User Not Found", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // Register a new user
    @PostMapping("/postuser")
    public ResponseEntity<ResponseApi<String>> registerUser(@RequestBody AuthModel postdata) {
        // Use service to check if the email already exists
        if (authservice.doesEmailExists(postdata.getEmail())) {
            ResponseApi<String> response = new ResponseApi<>("Email already exists.", null);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        try {
            authservice.RegisterUser(postdata);
            ResponseApi<String> response = new ResponseApi<>("Registration Successful", null);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            e.printStackTrace(); 
            ResponseApi<String> response = new ResponseApi<>("Failed to register user", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Login existing user
    @PostMapping("/login")
    public ResponseEntity<ResponseApi<String>> login(@RequestBody AuthModel loginrequest) {
        try {
            AuthModel loginUser = authservice.LoginUser(loginrequest.getEmail(), loginrequest.getPassword());
            if (loginUser != null && loginUser.getPassword().equals(loginrequest.getPassword())) {
                ResponseApi<String> response = new ResponseApi<>("Login Successful", null);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                ResponseApi<String> response = new ResponseApi<>("Invalid Email Or Password", null);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } catch (Exception e) {
            ResponseApi<String> response = new ResponseApi<>("An error occurred during login", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

