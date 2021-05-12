package com.egamorim.vechiclemanagement.presentation.request;

import com.egamorim.vechiclemanagement.application.vehicle.VehicleStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class UpdateVehicleRequest {
    @NotNull(message = "Model is mandatory")
    private String model;
    @NotNull(message = "Manufacturer is mandatory")
    private String manufacturer;
    @NotNull(message = "Status is mandatory")
    private VehicleStatus status;
}
