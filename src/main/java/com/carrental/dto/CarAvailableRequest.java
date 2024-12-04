package com.carrental.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CarAvailableRequest {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
