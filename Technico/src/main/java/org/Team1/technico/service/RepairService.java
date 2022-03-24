package org.Team1.technico.service;

import org.Team1.technico.dto.ResponseResult;
import org.Team1.technico.model.Repair;

import java.time.LocalDate;
import java.util.List;

public interface RepairService {

    ResponseResult<List<Repair>> readRepair();
    ResponseResult<Repair> readRepair(int repairId);

    ResponseResult<Repair> updateRepair(int repairId, Repair repair);
    ResponseResult<Boolean> deleteRepair(int repairId);
    ResponseResult<Boolean> addRepairToProperty(Repair repair, int propertyId);
    ResponseResult<List<Repair>> getByRegistrationDateIsBetween(LocalDate registrationDateStart, LocalDate registrationDateEnd);
    ResponseResult<List<Repair>> getByOwner_Id(Integer id);

}
