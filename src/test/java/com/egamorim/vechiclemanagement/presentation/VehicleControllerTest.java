package com.egamorim.vechiclemanagement.presentation;

import com.egamorim.vechiclemanagement.application.vehicle.Vehicle;
import com.egamorim.vechiclemanagement.application.vehicle.VehicleRepository;
import com.egamorim.vechiclemanagement.application.vehicle.VehicleStatus;
import com.egamorim.vechiclemanagement.presentation.reponse.VehicleListResponse;
import com.egamorim.vechiclemanagement.presentation.reponse.VehicleResponse;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VehicleControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Test
    void findAll_Success() {

        EasyRandom generator = new EasyRandom();
        this.vehicleRepository.save(generator.nextObject(Vehicle.class));
        this.vehicleRepository.save(generator.nextObject(Vehicle.class));
        assertThat(
                this.restTemplate.getForObject("http://localhost:" + port + "/vehicle?page=1&sorted-by=model", VehicleListResponse.class)
                    .getData()
                    .size())
                    .isEqualTo(2);
    }

    @Test
    void save_Success() {
        Vehicle request = Vehicle.builder()
                .status(VehicleStatus.ACTIVE)
                .plate("QEQ-8098")
                .model("Fusca")
                .manufacturer("Volks")
                .build();

        ResponseEntity<VehicleResponse> response = this.restTemplate
                .postForEntity("http://localhost:" + port + "/vehicle", request, VehicleResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    }

    @Test
    void save_Duplicity_Error() {
        Vehicle vehicle = Vehicle.builder()
                .id(1L)
                .status(VehicleStatus.ACTIVE)
                .plate("QEQ-8098")
                .model("Fusca")
                .manufacturer("Volks")
                .build();

        this.vehicleRepository.save(vehicle);

        Vehicle request = Vehicle.builder()
                .status(VehicleStatus.ACTIVE)
                .plate("QEQ-8098")
                .model("Fusca")
                .manufacturer("Volks")
                .build();

        String response = this.restTemplate
                .postForObject("http://localhost:" + port + "/vehicle", request, String.class);
        assertThat(response).contains("Duplicate");

    }
}
