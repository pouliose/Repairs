package org.Team1.technico.controller;

import lombok.AllArgsConstructor;
import org.Team1.technico.dto.ResponseResult;
import org.Team1.technico.model.Repair;
import org.Team1.technico.service.RepairService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor

@RestController
@RequestMapping("/repairs")
public class RepairController {

    private RepairService service;

    @GetMapping("")
    public ResponseResult<List<Repair>> get(@RequestParam(name = "startDate", required = false) String startDate, @RequestParam(name = "endDate", required = false) String endDate, @RequestParam(name = "ownerId", required = false) Integer ownerID) {
        if (startDate != null && endDate != null) {
            LocalDate registrationDateStart = LocalDate.parse(startDate);
            LocalDate registrationDateEnd = LocalDate.parse(endDate);
            return service.getByRegistrationDateIsBetween(registrationDateStart, registrationDateEnd);
        } else if (ownerID != null) {
            return service.getByOwner_Id(ownerID);
        }
        return service.readRepair();
    }

    @GetMapping("/{repairId}")
    public ResponseResult<Repair> get(@PathVariable("repairId") int repairId) {
        return service.readRepair(repairId);
    }

    @PutMapping("/{repairId}")
    public ResponseResult<Repair> update(@PathVariable("repairId") int repairId, @RequestBody Repair repair) {
        return service.updateRepair(repairId, repair);
    }

    @DeleteMapping("/{repairId}")
    public ResponseResult<Boolean> delete(@PathVariable("repairId") int repairId) {
        return service.deleteRepair(repairId);
    }


}
