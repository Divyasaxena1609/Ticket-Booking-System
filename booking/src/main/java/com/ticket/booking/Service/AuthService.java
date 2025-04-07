package com.ticket.booking.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ticket.booking.Models.AuthModel;
import com.ticket.booking.Repository.AuthRepository;

@Service
public class AuthService {

    @Autowired
    private AuthRepository authRepo;

    public AuthModel FindUserByUsername(String username) {
        return authRepo.findByUsername(username);
    }

    public boolean doesEmailExists(String email) {
        return authRepo.findByEmail(email) != null;
    }

    @Transactional
    public AuthModel RegisterUser(AuthModel newUser) {
        if (newUser.getuserId() != null) {
            AuthModel existingUser = authRepo.findById(newUser.getuserId()).orElse(null);
            if (existingUser != null) {
                existingUser.setusername(newUser.getusername());
                existingUser.setEmail(newUser.getEmail());
                existingUser.setPassword(newUser.getPassword());
                existingUser.setPhone(newUser.getPhone());
                existingUser.setRole(newUser.getRole());
                return authRepo.save(existingUser);
            }
        }
        return authRepo.save(newUser);
    }

    public AuthModel LoginUser(String email, String password) {
        AuthModel loginUser = authRepo.findByEmail(email);
        if (loginUser != null && loginUser.getPassword().equals(password)) {
            System.out.println("Password in DB: " + loginUser.getPassword());
           System.out.println("Password entered: " + password);
            return loginUser;
        }
        System.out.println("Password in DB: " + loginUser.getPassword());
        System.out.println("Password entered: " + password);
        return null;
    }
}

