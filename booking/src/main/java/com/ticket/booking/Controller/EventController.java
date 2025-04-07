package com.ticket.booking.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticket.booking.CustomResponse.ResponseApi;
import com.ticket.booking.Models.EventModel;
import com.ticket.booking.Service.EventService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/events")
public class EventController {
    
    @Autowired
    private EventService eventService;

    @GetMapping("/list")
    public ResponseEntity<List<EventModel>> getAllEvents() {
        List<EventModel> events = eventService.getEventList();
        return ResponseEntity.ok(events);
    }

    @PostMapping("/addevent")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity <ResponseApi<EventModel>> addEvents(@RequestBody EventModel newevent) {
        EventModel createdEvent = eventService.addEvent(newevent);
        ResponseApi<EventModel> response = new ResponseApi<>("Event added successfully", createdEvent);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PutMapping("/updateevent/{eventId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseApi<EventModel>> updateEvents(@PathVariable Long eventId, @RequestBody EventModel eventdetails) {
        EventModel updatedEvent = eventService.updateEvent(eventId , eventdetails);
        ResponseApi<EventModel> response = new ResponseApi<>("Event Updated successfully", updatedEvent);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/deleteevent/{eventId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseApi<String>> deleteEvents(@PathVariable Long eventId) {
         eventService.deleteEvent(eventId);
         ResponseApi<String> response = new ResponseApi<>("Event Deleted successfully", null);
         return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
