package org.Team1.technico.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Repair {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate registrationDate;
    @JsonIgnore
    @ManyToOne
    private Property property;
    private LocalDate completionDate;
    private RepairStatus repairStatus;
    private RepairType repairType;
    private BigDecimal cost;
    private String description;

    public Repair(LocalDate registrationDate, LocalDate completionDate, RepairStatus repairStatus, RepairType repairType, BigDecimal cost, String description) {
        this.registrationDate = registrationDate;
        this.completionDate = completionDate;
        this.repairStatus = repairStatus;
        this.repairType = repairType;
        this.cost = cost;
        this.description = description;
    }
}
