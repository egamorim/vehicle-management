package com.egamorim.vechiclemanagement.presentation.reponse;

import com.egamorim.vechiclemanagement.presentation.VehicleController;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Setter
@Getter
public class VehicleListResponse extends RepresentationModel<VehicleListResponse> {

    private List<VehicleResponse> data;

    public VehicleListResponse addLinks(final String sortedBy, final int page, final int totalPages) {
        int previousPage = page - 1;
        int nextPage = page + 1;

        if(previousPage > 0) {
            add(linkTo(methodOn(VehicleController.class).getAll(previousPage, sortedBy)).withRel(IanaLinkRelations.PREVIOUS));
        }
        if(nextPage <= totalPages) {
            add(linkTo(methodOn(VehicleController.class).getAll(nextPage, sortedBy)).withRel(IanaLinkRelations.NEXT));
        }
        return this;
    }

}
