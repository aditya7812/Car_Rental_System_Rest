package com.carrental.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCarDTO {

    @NotNull(message = "Model Name Missing")
    private String carModel;
    private String company;
    private String licencePlate;
    private Double pricePerDay;
    private String status;


}
