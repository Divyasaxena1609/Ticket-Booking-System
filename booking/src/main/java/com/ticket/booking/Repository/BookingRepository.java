package com.ticket.booking.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.ticket.booking.Models.BookingModel;

@Repository
public interface BookingRepository extends JpaRepository<BookingModel , Long>{

    List<BookingModel> findByUser_UserId(Long userId);

    List<BookingModel> findByEvent_EventId(Long eventId);

}
