package org.Team1.technico.controller;


import lombok.AllArgsConstructor;
import org.Team1.technico.model.Property;
import org.Team1.technico.model.Repair;
import org.Team1.technico.service.PropertyService;
import org.Team1.technico.service.RepairService;
import org.Team1.technico.utils.ResponseResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/properties")

public class PropertyController {

    private PropertyService propertyService;
    private RepairService repairService;

    @GetMapping(value = "")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseResult<List<Property>> get(@RequestParam(name = "vatNumber", required = false) String vatNumber, @RequestParam(name = "identityE9", required = false) String identityE9) {
        if ( (identityE9!=null  && !identityE9.equals("") || (vatNumber != null && !vatNumber.equals(""))))
            return propertyService.getByOwnerVatNumberOrIdentityE9(vatNumber, identityE9);
        return propertyService.readProperty();
    }

    @GetMapping("/{propertyId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseResult<Property> get(@PathVariable("propertyId") int propertyId) {
        return propertyService.readProperty(propertyId);
    }

    @PutMapping(value = "/{propertyId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseResult<Property> update(@PathVariable("propertyId") int propertyId, @RequestBody Property property) {
        return propertyService.updateProperty(propertyId, property);
    }

    @DeleteMapping("/{propertyId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseResult<Boolean> delete(@PathVariable("propertyId") int propertyId) {
        return propertyService.deleteProperty(propertyId);
    }

    @GetMapping("/{propertyId}/repairs")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseResult<List<Repair>> getRepairsOfProperty(@PathVariable("propertyId") int propertyId) {
        return propertyService.getRepairsByPropertyId(propertyId);
    }

    @PostMapping("/{propertyId}/repairs")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseResult<Boolean> addRepairOfProperty(@PathVariable("propertyId") int propertyId, @RequestBody Repair repair) {
        return repairService.addRepairToProperty(repair, propertyId);
    }
}
