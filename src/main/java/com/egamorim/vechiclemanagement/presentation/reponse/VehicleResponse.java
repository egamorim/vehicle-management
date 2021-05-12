package com.egamorim.vechiclemanagement.presentation.reponse;

import com.egamorim.vechiclemanagement.application.vehicle.Vehicle;
import com.egamorim.vechiclemanagement.application.vehicle.VehicleStatus;
import com.egamorim.vechiclemanagement.presentation.VehicleController;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import java.lang.reflect.Method;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Setter
@Getter
public class VehicleResponse extends RepresentationModel<VehicleResponse> {
    private String plate;
    private String model;
    private String manufacturer;
    private VehicleStatus status;

    public static VehicleResponse of(final Vehicle vehicle) {
        VehicleResponse vehicleResponse = new VehicleResponse();
        vehicleResponse.setManufacturer(vehicle.getManufacturer());
        vehicleResponse.setModel(vehicle.getModel());
        vehicleResponse.setPlate(vehicle.getPlate());
        vehicleResponse.setStatus(vehicle.getStatus());

        try {
            Method method = VehicleController.class.getMethod("getByPlate", String.class);
            Link selfLink = linkTo(method, vehicle.getPlate()).withSelfRel();
            vehicleResponse.add(selfLink);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return vehicleResponse;
    }
}
