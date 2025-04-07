package com.ticket.booking.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ticket.booking.Models.AuthModel;

@Repository
public interface AuthRepository extends JpaRepository<AuthModel , Long>{

    AuthModel findByUsername(String username);

    AuthModel findByEmail(String email);
    boolean existsByEmail(String email);
    
}
