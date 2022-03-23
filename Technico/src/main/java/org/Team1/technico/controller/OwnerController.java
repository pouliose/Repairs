package org.Team1.technico.controller;

import lombok.AllArgsConstructor;
import org.Team1.technico.dto.PropertyDto;
import org.Team1.technico.dto.ResponseResult;
import org.Team1.technico.model.Owner;
import org.Team1.technico.model.Property;
import org.Team1.technico.service.OwnerService;
import org.Team1.technico.service.PropertyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor

@RestController
@RequestMapping("/owners")
public class OwnerController {
    private OwnerService service;
    private PropertyService propertyService;

    @PostMapping(value = "")
    public Owner create(@RequestBody Owner owner) {
        return service.createOwner(owner);
    }

    @GetMapping("")
    public List<Owner> get(@RequestParam(name = "vatNumber", required = false) String vatNumber, @RequestParam(name = "email", required = false) String email) {
        if (vatNumber != null && vatNumber != "" || email !="" && email != null)
            return service.getOwnerByVatNumberOrEmail(vatNumber, email);
        return service.readOwner();
    }

    @GetMapping("/{ownerId}")
    public Owner get(@PathVariable("ownerId") int ownerId) {
        return service.readOwner(ownerId);
    }

    @PutMapping("/{ownerId}")
    public Owner update(@PathVariable("ownerId") int ownerId, @RequestBody Owner owner) {
        return service.updateOwner(ownerId, owner);
    }

    @DeleteMapping("/{ownerId}")
    public boolean delete(@PathVariable("ownerId") int ownerId) {
        return service.deleteOwner(ownerId);
    }

    @GetMapping("/{ownerId}/properties")
    public List<Property> getPropertiesOfOwner(@PathVariable("ownerId") int ownerId) {
        return service.getPropertiesOfOwner(ownerId);
    }

    @PostMapping(value = "/{ownerId}/properties")
    public ResponseResult<Boolean> addPropertyToOwner(@PathVariable("ownerId") int ownerId, @RequestBody Property property) {
        return propertyService.addPropertyToOwner(property, ownerId);
    }


}
