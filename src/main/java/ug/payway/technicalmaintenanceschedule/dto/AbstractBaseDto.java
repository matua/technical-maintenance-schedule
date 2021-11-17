package ug.payway.technicalmaintenanceschedule.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
@SuperBuilder
@NoArgsConstructor
public class AbstractBaseDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
}