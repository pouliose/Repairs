package org.Team1.technico.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.Team1.technico.dto.PropertyDto;
import org.Team1.technico.dto.ResponseResult;
import org.Team1.technico.dto.ResponseStatus;
import org.Team1.technico.model.Owner;
import org.Team1.technico.model.Property;
import org.Team1.technico.model.Repair;
import org.Team1.technico.repository.OwnerRepository;
import org.Team1.technico.repository.PropertyRepository;
import org.Team1.technico.repository.RepairRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Slf4j

public class PropertyServiceImpl implements PropertyService {

    private PropertyRepository propertyRepository;
    private OwnerRepository ownerRepository;
    private RepairRepository repairRepository;


    @Override
    public Property createProperty(Property property) {
        return propertyRepository.save(property);
    }

    @Override
    public List<Property> readProperty() {
        return propertyRepository.findAll();
    }

    @Override
    public Property readProperty(int propertyId) {
        Optional<Property> propertyDb = propertyRepository.findById(propertyId);
        if (propertyDb.isEmpty())
            return null;

        return propertyDb.get();
    }

    @Override
    public Property updateProperty(int propertyId, Property property) {
        Optional<Property> propertyDb = propertyRepository.findById(propertyId);
        if (propertyDb.isEmpty())
            return null;
        try {
            return propertyDb
                    .map(match -> {
                        try {
                            match.setIdentityE9(property.getIdentityE9());
                            match.setAddress(property.getAddress());
                            match.setConstructionYear(property.getConstructionYear());
                            match.setPropertyType(property.getPropertyType());
                            return propertyRepository.save(match);
                        } catch (Exception e) {
                            e.getMessage();
                            return null;
                        }
                    }).get();
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    @Override
    public boolean deleteProperty(int propertyId) {
        Optional<Property> propertyDb = propertyRepository.findById(propertyId);
        if (propertyDb.isEmpty() || !propertyDb.get().getRepairs().isEmpty())
            return false;
        propertyRepository.delete(propertyDb.get());
        return true;
    }

    @Override
    public boolean addPropertyToOwner(Property property, int ownerId) {
        return false;
    }

    @Override
    public ResponseResult<Integer> addPropertyToOwner(int ownerId) {
        Optional<Owner> ownerOptional = ownerRepository.findById(ownerId);
        log.debug("Create property method ", ownerId);

        if (ownerOptional.isPresent()) {

            log.debug("Create property method ", ResponseStatus.SUCCESS);
            Property property = new Property();
            property.setOwner(ownerOptional.get());
            property.setOwnerVatNumber(ownerOptional.get().getVatNumber());
            try {
                propertyRepository.save(property);
            } catch (Exception e) {
                log.debug("Create property method ", ResponseStatus.PROPERTY_NOT_CREATED);
                return new ResponseResult<>(-1, ResponseStatus.PROPERTY_NOT_CREATED, "The property has NOT been saved");
            }
            return new ResponseResult<>(property.getId(),
                    ResponseStatus.SUCCESS, "The property has been created successfully");
        }
        log.debug("Create property method ", ResponseStatus.OWNER_NOT_FOUND);
        return new ResponseResult<>(-1, ResponseStatus.OWNER_NOT_FOUND, "The owner cannot be found");

    }

    @Override
    public List<Property> getPropertiesByPropertyIdOrOwnerVatNumber(Integer propertyId, String ownerVatNumber) {
        if (propertyId != null || ownerVatNumber != null) {
            List<Property> properties = propertyRepository.findPropertyByIdOrOwnerVatNumber(propertyId, ownerVatNumber);

            return properties;
        }
        return null;
    }

    @Override
    public List<Repair> getRepairsByPropertyId(Integer propertyId) {
        return propertyRepository.getRepairsByPropertyId(propertyId);
    }

    @Override
    public ResponseResult<List<PropertyDto>> ownerPropertyRepairs(int ownerId) {
        Optional<Owner> ownerOptional = ownerRepository.findById(ownerId);
        if (ownerOptional.isEmpty()) {

            log.debug("Create basket method ", ResponseStatus.OWNER_NOT_FOUND);
            return new ResponseResult<>(null,
                    ResponseStatus.OWNER_NOT_FOUND, "The customer cannot be found");
        }
        List<Property> properties = propertyRepository.findPropertyByOwnerId(ownerId);

        List<PropertyDto> propertyDtoList = properties
                .stream()
                .map(property -> new PropertyDto().addList()).toList();
        return new ResponseResult<>(propertyDtoList, ResponseStatus.SUCCESS, "All OK");

    }

}
