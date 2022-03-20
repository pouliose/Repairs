package org.Team1.technico.controller;


import org.Team1.technico.model.Property;
import org.Team1.technico.service.PropertyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/properties")


public class PropertyController {
    private PropertyService propertyService;
    @GetMapping({""})
    public String ping() {
        return "ok";
    }

    @PostMapping(value = "/property")
    public Property create(@RequestBody Property property) {
        return propertyService.createProperty(property);
    }


    @GetMapping(value = "/property")
    public List<Property> getAllProperties() {
        return propertyService.readProperty();
    }

    @PutMapping("/property")
    public Property update(@PathVariable("propertyId") int propertyId, @RequestBody Property property) {
        return propertyService.updateProperty(propertyId, property);
    }

    @DeleteMapping("/property")
    public boolean delete(@PathVariable("propertyId") int propertyId) {
        return propertyService.deleteProperty(propertyId);

   }
    public PropertyController(final PropertyService propertyService) {
        this.propertyService = propertyService;
    }
}
