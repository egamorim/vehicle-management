package com.egamorim.vechiclemanagement.application.vehicle;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Builder
@Table(indexes = {
        @Index(name = "uniqueIndex", columnList = "plate", unique = true)
})
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String plate;
    private String model;
    private String manufacturer;
    @Enumerated(EnumType.STRING)
    private VehicleStatus status;
}
