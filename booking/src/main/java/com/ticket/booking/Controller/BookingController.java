package com.ticket.booking.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticket.booking.CustomResponse.ResponseApi;
import com.ticket.booking.Models.BookingModel;
import com.ticket.booking.Service.BookingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/book")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseApi<List<BookingModel>>> getUserBookingHistory(@PathVariable Long userId){
        List<BookingModel> bookings = bookingService.getUserBookings(userId);
        if(bookings.isEmpty()){
            ResponseApi<List<BookingModel>> response = new ResponseApi<>("No bookings found for User", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ResponseApi<List<BookingModel>> response = new ResponseApi<List<BookingModel>>("All Bookings By User", bookings);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/admin/{eventId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseApi<List<BookingModel>>> getEventAllBookings(@PathVariable Long eventId){
        List<BookingModel> bookings = bookingService.getEventBookings(eventId);
        if(bookings.isEmpty()){
            ResponseApi<List<BookingModel>> response = new ResponseApi<>("No bookings found for Event", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ResponseApi<List<BookingModel>> response = new ResponseApi<List<BookingModel>>("All Event Bookings", bookings);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    } 

    @PostMapping("/booking")
    public ResponseEntity<ResponseApi<BookingModel>> bookEventTickets(@RequestBody BookingModel bookingrequest){
        try{
          Long userId = bookingrequest.getUser().getuserId();
          Long eventId = bookingrequest.getEvent().getEventId();
          int numberOfTickets = bookingrequest.getNumberOfTickets();
          BookingModel booking = bookingService.bookTickets(userId, eventId, numberOfTickets);
          
          ResponseApi<BookingModel> response = new ResponseApi<>("Booking Successful", booking);
          return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        catch(IllegalArgumentException e){
          ResponseApi<BookingModel> errorResponse = new ResponseApi<>( "Booking Failed:" + e.getMessage(), null);
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PutMapping("/cancel")
    public ResponseEntity<ResponseApi<BookingModel>> cancelBookings(@RequestBody BookingModel cancelRequest) {
        try {
            
            Long bookingId = cancelRequest.getBookingId();
            int canceledTickets = cancelRequest.getCanceledTickets();
            BookingModel updatedBooking = bookingService.cancelBooking(bookingId , canceledTickets);
            ResponseApi<BookingModel> response = new ResponseApi<>("Booking Canceled Successfully", updatedBooking);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } 
        catch (IllegalArgumentException e) {
            ResponseApi<BookingModel> errorResponse = new ResponseApi<>( "Cancilation Failed:" + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}
