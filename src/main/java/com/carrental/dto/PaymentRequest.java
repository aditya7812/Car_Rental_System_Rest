package com.carrental.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentRequest {
    private Long bookingId;
    private Long billId;
    private String paymentType;
    private String paymentMethod;
    private Double amount;
}
