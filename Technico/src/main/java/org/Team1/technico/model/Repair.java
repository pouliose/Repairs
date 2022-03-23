package org.Team1.technico.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Future;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Repair {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Future
    private LocalDateTime registrationDate;
    @JsonIgnore
    @ManyToOne
    private Property property;
    @Future
    private LocalDate completionDate;
    private RepairStatus repairStatus;
    private RepairType repairType;
    private BigDecimal cost;
    @JsonIgnore
    @ManyToOne
    private Owner owner;
    private String description;

    public Repair(LocalDateTime registrationDate, LocalDate completionDate, RepairStatus repairStatus, RepairType repairType, BigDecimal cost, String description) {
        this.registrationDate = registrationDate;
        this.completionDate = completionDate;
        this.repairStatus = repairStatus;
        this.repairType = repairType;
        this.cost = cost;
        this.description = description;
    }
}
