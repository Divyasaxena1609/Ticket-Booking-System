package com.ticket.booking.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ticket.booking.Models.EventModel;

@Repository
public interface EventRepository extends JpaRepository<EventModel , Long>{
    
}
