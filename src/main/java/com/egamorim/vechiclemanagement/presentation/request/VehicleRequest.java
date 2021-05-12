package com.egamorim.vechiclemanagement.presentation.request;

import com.egamorim.vechiclemanagement.application.vehicle.VehicleStatus;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class VehicleRequest {
    @NotNull(message = "Plate is mandatory")
    private String plate;
    @NotNull(message = "Model is mandatory")
    private String model;
    @NotNull(message = "Manufacturer is mandatory")
    private String manufacturer;
    @NotNull(message = "Status is mandatory")
    private VehicleStatus status;
}
