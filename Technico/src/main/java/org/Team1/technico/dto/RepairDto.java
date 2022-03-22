package org.Team1.technico.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.Team1.technico.model.RepairStatus;
import org.Team1.technico.model.RepairType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class RepairDto {
    private int id;
    private LocalDateTime registrationDate;
    private LocalDate completionDate;
    private RepairStatus repairStatus;
    private RepairType repairType;
    private BigDecimal cost;
    private String description;
}
