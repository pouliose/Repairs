package org.Team1.technico.service;

import org.Team1.technico.model.Repair;

import java.time.LocalDate;
import java.util.List;

public interface RepairService {
    Repair createRepair(Repair repair);
    List<Repair> readRepair();
    Repair readRepair(int repairId);
    List<Repair> searchRepair(int ownerId);
    List<Repair> searchRepair(LocalDate firstDateInRange, LocalDate lastDateInRange);
    Repair updateRepair(int repairId, Repair repair);
    boolean deleteRepair(int repairId);
    boolean addRepairToProperty(Repair repair, int propertyId);
}
