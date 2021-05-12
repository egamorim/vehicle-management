package com.egamorim.vechiclemanagement.application.vehicle.service;

import com.egamorim.vechiclemanagement.application.vehicle.Vehicle;
import com.egamorim.vechiclemanagement.application.vehicle.VehicleRepository;
import com.egamorim.vechiclemanagement.application.vehicle.exception.DuplicatedVehicleException;
import com.egamorim.vechiclemanagement.application.vehicle.exception.VehicleNotFoundException;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class VehicleServiceTest {

    @Mock
    private VehicleRepository vehicleRepository;

    private VehicleService vehicleService;

    @BeforeEach
    void before() {
        this.vehicleService = new VehicleServiceImpl(vehicleRepository);
    }
    @Test
    void findAll_Success() {
        int pageSize = 5;
        List<Vehicle> vehicles = new ArrayList<>();
        for(int i = 0; i < pageSize; i++) {
            vehicles.add(new EasyRandom().nextObject(Vehicle.class));
        }
        Page<Vehicle> page = new PageImpl<>(vehicles);
        when(vehicleRepository.findAll(any(Pageable.class))).thenReturn(page);
        assertThat(vehicleService.findAll(1, pageSize, "plate").getSize()).isEqualTo(pageSize);
    }

    @Test
    void save_Success() {
        Vehicle mockVehicle = new EasyRandom().nextObject(Vehicle.class);
        when(vehicleRepository.save(any())).thenReturn(mockVehicle);
        assertThat(vehicleService.save(mockVehicle)).isNotNull();
    }

    @Test
    void save_DuplicatedVehicle_Error() {
        Vehicle mockVehicle = new EasyRandom().nextObject(Vehicle.class);
        when(vehicleRepository.save(any())).thenThrow(DataIntegrityViolationException.class);

        assertThatThrownBy(() -> {
            vehicleService.save(mockVehicle);
        }).isInstanceOf(DuplicatedVehicleException.class)
        .hasMessageContaining("Duplicate entry for plate");
    }

    @Test
    void findByPlate_Exists_Success() {
        Vehicle mockVehicle = new EasyRandom().nextObject(Vehicle.class);
        when(vehicleRepository.findByPlate(any())).thenReturn(Optional.of(mockVehicle));
        Vehicle byPlate = this.vehicleService.findByPlate("DER-5364");
        assertThat(byPlate).isNotNull();
    }

    @Test
    void findByPlate_NonExists_Error() {
        when(vehicleRepository.findByPlate(any())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> {
            vehicleService.findByPlate("DER-5364");
        }).isInstanceOf(VehicleNotFoundException.class)
                .hasMessageContaining("No records found for plate");
    }
}
