package com.carrental.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.carrental.dto.PaymentRequest;
import com.carrental.exception.ResourceNotFoundException;
import com.carrental.model.Bill;
import com.carrental.model.Booking;
import com.carrental.model.Payment;
import com.carrental.repository.BillRepository;
import com.carrental.repository.BookingRepository;
import com.carrental.repository.PaymentRepository;

@Service
public class PaymentService {
    private final BookingRepository bookingRepo;
    private final BillRepository billRepo;
    private final PaymentRepository paymentRepo;

    public PaymentService(BookingRepository bookingRepo, BillRepository billRepo, PaymentRepository paymentRepo) {
        this.bookingRepo = bookingRepo;
        this.billRepo = billRepo;
        this.paymentRepo = paymentRepo;
    }

    public Payment processPayment(PaymentRequest paymentRequest) {
        if ("REGISTRATION".equalsIgnoreCase(paymentRequest.getPaymentType())) {
            return processRegistrationPayment(paymentRequest);
        } else if ("FINAL".equalsIgnoreCase(paymentRequest.getPaymentType())) {
            return processFinalPayment(paymentRequest);
        } else {
            throw new IllegalArgumentException("Invalid Payment Type :" + paymentRequest.getPaymentType());
        }
    }

    public Payment processRegistrationPayment(PaymentRequest paymentRequest) {
        Booking booking = bookingRepo.findById(paymentRequest.getBookingId())
                            .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID :" + paymentRequest.getBookingId()));
        Double registrationFee = 100.0;
        if (!paymentRequest.getAmount().equals(registrationFee)) {
            throw new IllegalArgumentException("Invalid payment amount :" + paymentRequest.getAmount());
        }
        // Fake Payment Processing
        boolean paymentSuccess = simulatePaymentProcessing(paymentRequest);

        if (!paymentSuccess) {
            throw new IllegalArgumentException("Payment Unsuccessful");
        }

        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setAmount(paymentRequest.getAmount());
        payment.setPaymentType("REGISTRATION");
        payment.setPaymentMethod(paymentRequest.getPaymentMethod());
        payment.setPaymentDate(LocalDateTime.now());

        return paymentRepo.save(payment);

    }

    private Payment processFinalPayment(PaymentRequest paymentRequest) {
        // Find bill
        Bill bill = billRepo.findById(paymentRequest.getBillId())
                        .orElseThrow(() -> new ResourceNotFoundException("Bill not found with ID: " + paymentRequest.getBillId()));

        if (!paymentRequest.getAmount().equals(bill.getTotalAmount())) {
            throw new IllegalArgumentException("Invalid final payment amount");
        }

        boolean paymentSuccess = simulatePaymentProcessing(paymentRequest);

        if (!paymentSuccess) {
            throw new IllegalArgumentException("Payment Unsuccessful");   
        }

        Payment payment = new Payment();
        payment.setBill(bill);
        payment.setAmount(paymentRequest.getAmount());
        payment.setPaymentType("FINAL");
        payment.setPaymentMethod(paymentRequest.getPaymentMethod());
        payment.setPaymentDate(LocalDateTime.now());

        bill.setStatus("Paid");
        billRepo.save(bill);

        return paymentRepo.save(payment);
    }

    public boolean simulatePaymentProcessing(PaymentRequest paymentRequest) {
        return true;
    }

}
