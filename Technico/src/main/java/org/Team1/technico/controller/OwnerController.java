package org.Team1.technico.controller;

import lombok.AllArgsConstructor;
import org.Team1.technico.model.Owner;
import org.Team1.technico.model.Property;
import org.Team1.technico.service.OwnerService;
import org.Team1.technico.service.PropertyService;
import org.Team1.technico.utils.ResponseResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/owners")
public class OwnerController {
    private OwnerService service;
    private PropertyService propertyService;

    @PostMapping(value = "")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseResult<Owner> create(@RequestBody Owner owner) {
        return service.createOwner(owner);
    }

    @GetMapping("")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public  ResponseResult<List<Owner>> get(@RequestParam(name = "vatNumber", required = false) String vatNumber, @RequestParam(name = "email", required = false) String email) {
        if (vatNumber != null && vatNumber != "" || email !="" && email != null)
            return service.getOwnerByVatNumberOrEmail(vatNumber, email);
        return service.readOwner();
    }

    @GetMapping("/{ownerId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseResult<Owner> get(@PathVariable("ownerId") int ownerId) {
        return service.readOwner(ownerId);
    }

    @PutMapping("/{ownerId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseResult<Owner> update(@PathVariable("ownerId") int ownerId, @RequestBody Owner owner) {
        return service.updateOwner(ownerId, owner);
    }

    @DeleteMapping("/{ownerId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseResult<Boolean> delete(@PathVariable("ownerId") int ownerId) {
        return service.deleteOwner(ownerId);
    }

    @GetMapping("/{ownerId}/properties")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseResult<List<Property>> getPropertiesOfOwner(@PathVariable("ownerId") int ownerId) {
        return service.getPropertiesOfOwner(ownerId);
    }

    @PostMapping(value = "/{ownerId}/properties")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseResult<Boolean> addPropertyToOwner(@PathVariable("ownerId") int ownerId, @RequestBody Property property) {
        return propertyService.addPropertyToOwner(property, ownerId);
    }
}
