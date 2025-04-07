package com.ticket.booking.Models;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class BookingModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long bookingId;
      
    @ManyToOne
    private AuthModel user;

    @ManyToOne
    private EventModel event;

    private LocalDate date;

    private int numberOfTickets;

    private int canceledTickets;

    private double paidAmount;

    private double refundableAmount;

    private String status;

    public BookingModel() {
    }

    public BookingModel(Long bookingId, AuthModel user, EventModel event, LocalDate date, int numberOfTickets, int canceledTickets, double paidAmount, double refundableAmount, String status) {
        this.bookingId = bookingId;
        this.user = user;
        this.event = event;
        this.date = date;
        this.numberOfTickets = numberOfTickets;
        this.canceledTickets = canceledTickets;
        this.paidAmount = paidAmount;
        this.refundableAmount = refundableAmount;
        this.status = status;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public AuthModel getUser() {
        return user;
    }

    public void setUser(AuthModel user) {
        this.user = user;
    }

    public EventModel getEvent() {
        return event;
    }

    public void setEvent(EventModel event) {
        this.event = event;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public int getCanceledTickets(){
        return canceledTickets;
    }

    public void setCanceledTickets(int canceledTickets){
        this.canceledTickets = canceledTickets;
    }

    public double getPaidAmount(){
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount){
        this.paidAmount = paidAmount;
    }

    public double getRefundabeAmount(){
        return refundableAmount;
    }

    public void setRefundableAmount(double refundableAmount){
        this.refundableAmount = refundableAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
