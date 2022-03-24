package org.Team1.technico.controller;


import lombok.AllArgsConstructor;
import org.Team1.technico.dto.ResponseResult;
import org.Team1.technico.model.Property;
import org.Team1.technico.model.Repair;
import org.Team1.technico.service.PropertyService;
import org.Team1.technico.service.RepairService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/properties")


public class PropertyController {

    private PropertyService propertyService;
    private RepairService repairService;

    @GetMapping(value = "")
    public ResponseResult<List<Property>> get(@RequestParam(name = "vatNumber", required = false) String vatNumber, @RequestParam(name = "identityE9", required = false) String identityE9) {
        if ( (identityE9!=null  && !identityE9.isBlank()) || (vatNumber != null && vatNumber != ""))
            return propertyService.getByOwnerVatNumberOrIdentityE9(vatNumber, identityE9);
        return propertyService.readProperty();
    }

    @GetMapping("/{propertyId}")
    public ResponseResult<Property> get(@PathVariable("propertyId") int propertyId) {
        return propertyService.readProperty(propertyId);
    }

    @PutMapping(value = "/{propertyId}")
    public ResponseResult<Property> update(@PathVariable("propertyId") int propertyId, @RequestBody Property property) {
        return propertyService.updateProperty(propertyId, property);
    }

    @DeleteMapping("/{propertyId}")
    public ResponseResult<Boolean> delete(@PathVariable("propertyId") int propertyId) {
        return propertyService.deleteProperty(propertyId);
    }

    @GetMapping("/{propertyId}/repairs")
    public ResponseResult<List<Repair>> getRepairsOfProperty(@PathVariable("propertyId") int propertyId) {
        return propertyService.getRepairsByPropertyId(propertyId);
    }

    @PostMapping("/{propertyId}/repairs")
    public ResponseResult<Boolean> addRepairOfProperty(@PathVariable("propertyId") int propertyId, @RequestBody Repair repair) {
        return repairService.addRepairToProperty(repair, propertyId);
    }
}
