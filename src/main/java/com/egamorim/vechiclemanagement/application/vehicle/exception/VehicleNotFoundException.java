package com.egamorim.vechiclemanagement.application.vehicle.exception;

public class VehicleNotFoundException extends RuntimeException{
    public VehicleNotFoundException(String message) {
        super(message);
    }
}
