package com.egamorim.vechiclemanagement.application.vehicle.service;

import com.egamorim.vechiclemanagement.application.vehicle.Vehicle;
import com.egamorim.vechiclemanagement.application.vehicle.VehicleRepository;
import com.egamorim.vechiclemanagement.application.vehicle.exception.DuplicatedVehicleException;
import com.egamorim.vechiclemanagement.application.vehicle.exception.VehicleNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    @Override
    public Page<Vehicle> findAll(final int page, final int size, final String sortedBy) {

        Pageable pageConfig = PageRequest.of(page, size, Sort.by(sortedBy));
        return vehicleRepository.findAll(pageConfig);
    }

    @Override
    public Vehicle save(final Vehicle vehicle) {
        try{
            this.vehicleRepository.save(vehicle);
        } catch(DataIntegrityViolationException e) {
            throw new DuplicatedVehicleException("Duplicate entry for plate " + vehicle.getPlate());
        }
        return this.vehicleRepository.save(vehicle);
    }

    @Override
    public Vehicle findByPlate(final String plate) {
        return this.vehicleRepository.findByPlate(plate)
                .orElseThrow(() -> new VehicleNotFoundException("No records found for plate " + plate));
    }

    @Override
    public void delete(String plate) {
        Vehicle vehicle = this.vehicleRepository
                .findByPlate(plate)
                .orElseThrow(() -> new VehicleNotFoundException("Vehicle not found"));
        this.vehicleRepository.delete(vehicle);
    }

    @Override
    public Vehicle update(Vehicle vehicle) {
        return this.vehicleRepository
            .findByPlate(vehicle.getPlate())
                .map(v -> {
                    v.setManufacturer(vehicle.getManufacturer());
                    v.setModel(vehicle.getModel());
                    v.setStatus(vehicle.getStatus());
                    return this.vehicleRepository.save(v);
                })
            .orElseThrow(() -> new VehicleNotFoundException("Vehicle not found"));
    }
}
