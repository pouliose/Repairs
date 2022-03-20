package org.Team1.technico.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
    private LocalDate registrationDate;
    @ManyToOne
    private Property property;
    private LocalDateTime completionDate;
    private RepairStatus repairStatus;
    private RepairType repairType;
    private BigDecimal cost;
    @ManyToOne
    private Owner owner;
    private String description;

}
