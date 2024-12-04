package com.carrental.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = true)
    private Booking booking;

    @ManyToOne
    @JoinColumn(name = "bill_id", nullable= true)
    private Bill bill;

    private Double amount;

    private String paymentType; // Registration, Final

    private String paymentMethod;
    private LocalDateTime paymentDate;


}
