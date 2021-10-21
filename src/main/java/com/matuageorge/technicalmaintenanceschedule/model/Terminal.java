package com.matuageorge.technicalmaintenanceschedule.model;

import com.google.maps.model.LatLng;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@SuperBuilder
@Table(name = "terminals")
@Entity
public class Terminal extends AbstractBaseEntity {
    @Enumerated(EnumType.STRING)
    private TerminalType type;
    @Size(min = 1, max = 10,
            message = "Terminal name must be between 1 and 10 characters")
    private String name;
    private String location;
    private Boolean disabled;
    private Boolean deleted;
    private Double longitude;
    private Double latitude;

    public LatLng getLatLngLocation() {
        if (latitude == null) {
            latitude = 0.0;
        }
        if (longitude == null) {
            longitude = 0.0;
        }
        return new LatLng(this.getLatitude(), this.getLongitude());
    }

}
