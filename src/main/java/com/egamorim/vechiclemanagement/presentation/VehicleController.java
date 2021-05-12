package com.egamorim.vechiclemanagement.presentation;

import com.egamorim.vechiclemanagement.application.vehicle.Vehicle;
import com.egamorim.vechiclemanagement.application.vehicle.service.VehicleService;
import com.egamorim.vechiclemanagement.presentation.reponse.VehicleListResponse;
import com.egamorim.vechiclemanagement.presentation.reponse.VehicleResponse;
import com.egamorim.vechiclemanagement.presentation.request.UpdateVehicleRequest;
import com.egamorim.vechiclemanagement.presentation.request.VehicleRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vehicle")
@RefreshScope
@Validated
public class VehicleController extends BaseController{

    private final VehicleService vehicleService;

    private final int defaultPageSize;

    public VehicleController(VehicleService vehicleService, @Value("${default.page.size}") int defaultPageSize) {
        this.vehicleService = vehicleService;
        this.defaultPageSize = defaultPageSize;
    }

    @PostMapping
    public ResponseEntity<VehicleResponse> save(@Valid @RequestBody VehicleRequest request) {
        Vehicle vehicle = Vehicle.builder()
                .manufacturer(request.getManufacturer())
                .model(request.getModel())
                .plate(request.getPlate())
                .status(request.getStatus())
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(VehicleResponse.of(this.vehicleService.save(vehicle)));
    }

    @GetMapping
    public ResponseEntity<VehicleListResponse> getAll(
            @RequestParam("page")
            @Min(value = 1, message = "Page must be greater than 0") int page,
            @RequestParam("sorted-by") String sortedBy) {
        int currentPage = page - 1;

        Page<Vehicle> content = this.vehicleService.findAll(currentPage, defaultPageSize, sortedBy);
        VehicleListResponse listResponse = new VehicleListResponse();

        List<VehicleResponse> vehicles = content
                .getContent()
                .stream()
                .map(VehicleResponse::of)
                .collect(Collectors.toList());

        listResponse.setData(vehicles);
        listResponse.addLinks(sortedBy, page, content.getTotalPages());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(listResponse);
    }

    @GetMapping("/plate/{plate}")
    public VehicleResponse getByPlate(@PathVariable String plate) {
        return VehicleResponse.of(this.vehicleService.findByPlate(plate));
    }

    @DeleteMapping("/plate/{plate}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String plate) {
        this.vehicleService.delete(plate);
    }

    @PutMapping("/plate/{plate}")
    public VehicleResponse update(@PathVariable String plate, @Valid @RequestBody final UpdateVehicleRequest request) {
        Vehicle vehicle = Vehicle.builder()
                .manufacturer(request.getManufacturer())
                .model(request.getModel())
                .plate(plate)
                .status(request.getStatus())
                .build();
        return VehicleResponse.of(this.vehicleService.update(vehicle));
    }

}
