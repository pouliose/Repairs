package org.Team1.technico.service;

import org.Team1.technico.model.Repair;
import org.Team1.technico.utils.ResponseResult;

import java.time.LocalDate;
import java.util.List;

public interface RepairService {

    /**
     * @return ResponseResult<List < Repair>>
     */
    ResponseResult<List<Repair>> readRepair();

    /**
     * @param repairId
     * @return ResponseResult<Repair>
     */
    ResponseResult<Repair> readRepair(int repairId);

    /**
     * @param repairId
     * @param repair
     * @return ResponseResult<Repair>
     */
    ResponseResult<Repair> updateRepair(int repairId, Repair repair);

    /**
     * @param repairId
     * @return ResponseResult<Boolean>
     */
    ResponseResult<Boolean> deleteRepair(int repairId);

    /**
     * @param repair
     * @param propertyId
     * @return ResponseResult<Boolean>
     */
    ResponseResult<Boolean> addRepairToProperty(Repair repair, int propertyId);

    /**
     * @param registrationDateStart
     * @param registrationDateEnd
     * @return ResponseResult<List < Repair>>
     */
    ResponseResult<List<Repair>> getByRegistrationDateIsBetween(LocalDate registrationDateStart, LocalDate registrationDateEnd);

    /**
     * @param id
     * @return ResponseResult<List < Repair>>
     */
    ResponseResult<List<Repair>> getByOwnerId(Integer id);

}
