package com.ticket.booking.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.ticket.booking.Models.AuthModel;
import com.ticket.booking.Models.BookingModel;
import com.ticket.booking.Models.EventModel;
import com.ticket.booking.Repository.AuthRepository;
import com.ticket.booking.Repository.BookingRepository;
import com.ticket.booking.Repository.EventRepository;

@Service
public class BookingService {
    
    @Autowired
    private AuthRepository authrepo;

    @Autowired
    private EventRepository eventrepo;

    @Autowired
    private BookingRepository bookingrepo;

    // Booking
    public BookingModel bookTickets(Long userId , Long eventId , int numberOfTickets){
        Optional<AuthModel> optionalUser = authrepo.findById(userId);
        Optional<EventModel> optionalEvent = eventrepo.findById(eventId);

        if(!optionalUser.isPresent() || !optionalEvent.isPresent()){
            throw new IllegalArgumentException("Event or user not present with the given Id.");
        }
        
        EventModel event = optionalEvent.get();

        if(event.getAvailableTickets() >= numberOfTickets){
            event.setAvailableTickets(event.getAvailableTickets() - numberOfTickets);
            eventrepo.save(event);
            
            double totalPrice = numberOfTickets * event.getPricePerTicket();

            BookingModel booking = new BookingModel();
            booking.setUser(optionalUser.get());
            booking.setEvent(event);
            booking.setDate(LocalDate.now());
            booking.setNumberOfTickets(numberOfTickets);
            booking.setCanceledTickets(0);
            booking.setPaidAmount(totalPrice);
            booking.setRefundableAmount(0);
            booking.setStatus("CONFIRMED");

            return bookingrepo.save(booking);
        }
        else{
            throw new IllegalArgumentException(numberOfTickets + " tickets are not available");
        }
    }

    // Cancel Booking
    public BookingModel cancelBooking(Long bookingId , int canceledTickets){
         Optional<BookingModel> optionalBooking = bookingrepo.findById(bookingId);

        if(!optionalBooking.isPresent()){
            throw new IllegalArgumentException("User booking is not present.");
        }
         
        BookingModel booking = optionalBooking.get();

        EventModel event = booking.getEvent();

        if ("CANCELED".equals(booking.getStatus())) {
            throw new IllegalStateException("This booking has already been canceled.");
        }
    

        if (canceledTickets <= 0) {
            throw new IllegalArgumentException("Invalid number of tickets to cancel.");
        }
    
        
        //If Cancel all tickets of specific booking

        if(canceledTickets >= booking.getNumberOfTickets()){
            // Update available tickets
            event.setAvailableTickets(event.getAvailableTickets() + booking.getNumberOfTickets());
            eventrepo.save(event);
            
            double refundableAmount = booking.getPaidAmount(); 
            booking.setCanceledTickets(booking.getCanceledTickets() + booking.getNumberOfTickets());
            booking.setNumberOfTickets(0);
            booking.setRefundableAmount(refundableAmount);
            booking.setStatus("CANCELED");
            bookingrepo.save(booking);
        }
        // Cancel Specific Number Of tickets in a booking
        else{
            event.setAvailableTickets(event.getAvailableTickets() + canceledTickets);
            eventrepo.save(event);
            
            double refundableAmount = canceledTickets * event.getPricePerTicket();

            booking.setCanceledTickets(booking.getCanceledTickets() + canceledTickets);
            booking.setNumberOfTickets(booking.getNumberOfTickets() - canceledTickets);
            booking.setRefundableAmount(booking.getRefundabeAmount() + refundableAmount); 
            booking.setStatus("CONFIRMED");
            bookingrepo.save(booking);
        }

        return booking;
    }

    // User Bookings History

    public List<BookingModel> getUserBookings(Long userId){
        return bookingrepo.findByUser_UserId(userId);
    }
    
    // All Bookings of Event
    public List<BookingModel> getEventBookings(Long eventId){
        return bookingrepo.findByEvent_EventId(eventId);
    }
}
