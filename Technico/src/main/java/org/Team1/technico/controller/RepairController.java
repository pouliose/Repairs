package org.Team1.technico.controller;

import lombok.AllArgsConstructor;
import org.Team1.technico.model.Repair;
import org.Team1.technico.service.RepairService;
import org.Team1.technico.utils.ResponseResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor

@RestController
@RequestMapping("api/repairs")
public class RepairController {

    private RepairService service;

    @GetMapping("")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseResult<List<Repair>> get(@RequestParam(name = "startDate", required = false) String startDate, @RequestParam(name = "endDate", required = false) String endDate, @RequestParam(name = "ownerId", required = false) Integer ownerId) {
        if (startDate != null && endDate != null) {
            LocalDate registrationDateStart = LocalDate.parse(startDate);
            LocalDate registrationDateEnd = LocalDate.parse(endDate);
            return service.getByRegistrationDateIsBetween(registrationDateStart, registrationDateEnd);
        } else if (ownerId != null) {
            return service.getByOwnerId(ownerId);
        }
        return service.readRepair();
    }

    @GetMapping("/{repairId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseResult<Repair> get(@PathVariable("repairId") int repairId) {
        return service.readRepair(repairId);
    }

    @PutMapping("/{repairId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseResult<Repair> update(@PathVariable("repairId") int repairId, @RequestBody Repair repair) {
        return service.updateRepair(repairId, repair);
    }

    @DeleteMapping("/{repairId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseResult<Boolean> delete(@PathVariable("repairId") int repairId) {
        return service.deleteRepair(repairId);
    }

}
