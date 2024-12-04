package com.carrental.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookingRequest {
    private Long carId;
    private Long userId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

}