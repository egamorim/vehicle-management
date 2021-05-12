package com.egamorim.vechiclemanagement.application.vehicle.service;

import com.egamorim.vechiclemanagement.application.vehicle.Vehicle;
import org.springframework.data.domain.Page;

public interface VehicleService {

    Page<Vehicle> findAll(int page, int size, String sortedBy);
    Vehicle save(Vehicle vehicle);
    Vehicle findByPlate(String plate);
    void delete(String plate);
    Vehicle update(Vehicle vehicle);
}
