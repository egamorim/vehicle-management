package com.egamorim.vechiclemanagement.application.vehicle.exception;

public class DuplicatedVehicleException extends RuntimeException{
    public DuplicatedVehicleException(String message) {
        super(message);
    }
}
