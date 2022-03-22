package org.Team1.technico.controller;

import lombok.AllArgsConstructor;
import org.Team1.technico.model.Repair;
import org.Team1.technico.service.RepairService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor

@RestController
@RequestMapping("/repairs")
public class RepairController {
    private RepairService service;

    @PostMapping(value = "")
    public Repair create(@RequestBody Repair repair) {
        return service.createRepair(repair);
    }

    @GetMapping("")
    public List<Repair> get() {
        return service.readRepair();
    }

    @GetMapping("/{repairId}")
    public Repair get(@PathVariable("repairId") int repairId) {
        return service.readRepair(repairId);
    }

    @PutMapping("/{repairId}")
    public Repair update(@PathVariable("repairId") int repairId, @RequestBody Repair repair) {
        return service.updateRepair(repairId, repair);
    }

    @DeleteMapping("/{repairId}")
    public boolean delete(@PathVariable("repairId") int repairId) {
        return service.deleteRepair(repairId);
    }

    @PostMapping(value = "/{repairId}/properties/{propertyId}")
    public boolean addPropertyToOwner(@PathVariable("repairId") int repairId, @PathVariable("propertyId") int propertyId) {
        return service.addRepairToProperty(repairId, propertyId);
    }

}
