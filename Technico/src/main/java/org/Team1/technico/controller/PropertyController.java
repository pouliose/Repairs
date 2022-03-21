package org.Team1.technico.controller;


import lombok.AllArgsConstructor;
import org.Team1.technico.dto.PropertyDto;
import org.Team1.technico.model.Property;
import org.Team1.technico.service.PropertyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/properties")


public class PropertyController {
    private PropertyService service;


    @PostMapping(value = "")
    public Property create(@RequestBody Property property) {
        return service.createProperty(property);
    }


    @GetMapping(value = "")
    public List<Property> getAllProperties() {
        return service.readProperty();
    }

    @GetMapping("/{propertyId}")
    public Property get(@PathVariable("propertyId") int propertyId) {
        return service.readProperty(propertyId);
    }

    @PutMapping(value = "/{propertyId}")
    public Property update(@PathVariable("propertyId") int propertyId, @RequestBody Property property) {
        return service.updateProperty(propertyId, property);
    }

    @DeleteMapping("/{propertyId}")
    public boolean delete(@PathVariable("propertyId") int propertyId) {
        return service.deleteProperty(propertyId);
    }

    @PostMapping(value = "/{propertyId}/owners/{ownerId}")
    public boolean addPropertyToOwner(@PathVariable("propertyId") int propertyId, @PathVariable("ownerId") int ownerId) {
        return service.addPropertyToOwner(ownerId, propertyId);
    }

    @GetMapping("/search")
    public List<PropertyDto> getPropertiesPropertiesByPropertyIdOrOwnerVatNumber(@RequestParam(name = "propertyId", required = false) Integer propertyId, @RequestParam(name = "vatNumber", required = false) String vatNumber) {
        if (propertyId != null || vatNumber != null && vatNumber != "")
            return service.getPropertiesByPropertyIdOrOwnerVatNumber(propertyId, vatNumber);
        return null;
    }
}
