package com.carrental.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.carrental.exception.ResourceNotFoundException;
import com.carrental.model.Bill;
import com.carrental.model.Booking;
import com.carrental.repository.BillRepository;
import com.carrental.repository.BookingRepository;

@Service
public class BillService {

    private final BookingRepository bookingRepo;
    private final BillRepository billRepo;

    public BillService(BookingRepository bookingRepo, BillRepository billRepo) {
        this.bookingRepo = bookingRepo;
        this.billRepo = billRepo;
    }

    public Bill generateBill(Long bookingId, Double damageCharges) {
        // Find the booking
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + bookingId));

        if (!"Confirmed".equals(booking.getStatus())) {
            throw new IllegalStateException("Cannot generate bill for booking with status: " + booking.getStatus());
        }

        long totalDays = java.time.temporal.ChronoUnit.DAYS.between(booking.getStartDate(), booking.getEndDate());
        Double baseAmount = totalDays * booking.getCar().getPricePerDay();

        LocalDateTime now = LocalDateTime.now();
        booking.setReturnDate(now);

        // Calculate late fee
        long lateDays = java.time.temporal.ChronoUnit.DAYS.between(booking.getEndDate(), now);
        double lateFee = (lateDays > 0) ? lateDays * booking.getCar().getPricePerDay() * 1.5 : 0.0; // Pay 1.5 times for late days

        // Create a bill
        Bill bill = new Bill();
        bill.setBooking(booking);
        bill.setBaseAmount(baseAmount); // Example base amount
        bill.setLateFee(lateFee);
        bill.setDamageCharges(damageCharges);
        bill.setTotalAmount(baseAmount + lateFee + damageCharges);
        bill.setCreatedDate(LocalDateTime.now());

        // Save the bill
        bill = billRepo.save(bill);

        // Update booking status
        booking.setStatus("Returned");
        bookingRepo.save(booking);

        return bill;
    }
}
