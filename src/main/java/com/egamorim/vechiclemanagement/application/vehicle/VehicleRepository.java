package com.egamorim.vechiclemanagement.application.vehicle;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface VehicleRepository extends PagingAndSortingRepository<Vehicle, Long> {

    Optional<Vehicle> findByPlate(String plate);

}
