package com.ticket.booking.Models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class EventModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "Event_id" , nullable = false , unique = true)
    private Long eventId;

    @Column(name = "Event_name" , nullable = false)
    private String eventName;

    @Column(name = "Artist_name" , nullable = false)
    private String artistName;

    @Column(name = "Event_date" , nullable = false)
    private LocalDate eventDate;

    @Column(name = "Event_location" , nullable = false)
    private String location;

    @Column(name = "Total_tickets" , nullable = false)
    private int totalTickets;

    @Column(name = "Available_tickets" , nullable = false)
    private int availableTickets;

    @Column(name = "Price_per_Ticket" , nullable = false)
    private double pricePerTicket;

    public EventModel() {
    }

    public EventModel(Long eventId, String eventName, String artistName, LocalDate eventDate, String location, int totalTickets, int availableTickets, double pricePerTicket) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.artistName = artistName;
        this.eventDate = eventDate;
        this.location = location;
        this.totalTickets = totalTickets;
        this.availableTickets = availableTickets;
        this.pricePerTicket = pricePerTicket;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getAvailableTickets() {
        return availableTickets;
    }

    public void setAvailableTickets(int availableTickets) {
        this.availableTickets = availableTickets;
    }

    public double getPricePerTicket(){
        return pricePerTicket;
    }

    public void setPricePerTicket(double pricePerTicket){
        this.pricePerTicket = pricePerTicket;
    }

}
