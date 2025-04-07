package com.ticket.booking.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticket.booking.Models.EventModel;
import com.ticket.booking.Repository.EventRepository;

@Service
public class EventService {
    @Autowired
    private EventRepository eventrepo;

    public List<EventModel> getEventList(){
        return eventrepo.findAll();
    }

    public EventModel addEvent(EventModel newEvent){
        return eventrepo.save(newEvent);
    }

    public void addEventList(List<EventModel> newEventList){
        for(EventModel events : newEventList){
            eventrepo.save(events);
        }
    }

    public EventModel updateEvent(Long EventId , EventModel EventDetails){
        Optional<EventModel> existEvents = eventrepo.findById(EventId);

        if(!existEvents.isPresent()){
            throw new RuntimeException("Event not found with " + EventId);
        }

        EventModel existingEvent = existEvents.get();
        existingEvent.setEventName(EventDetails.getEventName());
        existingEvent.setArtistName(EventDetails.getArtistName());
        existingEvent.setEventDate(EventDetails.getEventDate());
        existingEvent.setLocation(EventDetails.getLocation());
        existingEvent.setTotalTickets(EventDetails.getTotalTickets());
        existingEvent.setAvailableTickets(EventDetails.getAvailableTickets());
        existingEvent.setPricePerTicket(EventDetails.getPricePerTicket());

        return eventrepo.save(existingEvent);
    }

    public void deleteEvent(Long EventId){
        
        if(!eventrepo.existsById(EventId)){
            throw new RuntimeException("Event not found with " + EventId);
        }

        eventrepo.deleteById(EventId);
    }
}
