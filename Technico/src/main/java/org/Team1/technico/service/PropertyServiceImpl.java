package org.Team1.technico.service;


import lombok.AllArgsConstructor;
import org.Team1.technico.dto.PropertyDto;
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
        Optional<Property> propertyDb = Optional.ofNullable(readProperty(propertyId));
        if (propertyDb.isEmpty())
            return false;
        propertyRepository.delete(propertyDb.get());
        return true;
    }

    //    @Override
//    public List<PropertyDto> properties(int ownerId) {
//        return null;
//    }
    public boolean addPropertyToOwner(Property property, int ownerId) {
        Optional<Owner> ownerOptional = ownerRepository.findById(ownerId);

        if (ownerOptional.isPresent() ) {

            property.setOwner(ownerOptional.get());
            property.setOwnerVatNumber(ownerOptional.get().getVatNumber());
            propertyRepository.save(property);
            return true;
        }
        return false;
    }

    @Override
    public List<PropertyDto> getPropertiesByPropertyIdOrOwnerVatNumber(Integer propertyId, String ownerVatNumber) {
        if (propertyId != null || ownerVatNumber != null){
            List<Property> properties =  propertyRepository.findPropertyByIdOrOwnerVatNumber(propertyId, ownerVatNumber);
            List<PropertyDto> propertyDtos = properties.stream().map(property -> new PropertyDto(property.getId(), property.getIdentityE9(), property.getAddress(), property.getConstructionYear(), property.getPropertyType())).toList();
            return propertyDtos;
        }
        return null;
    }

    @Override
    public List<Repair> getRepairsByPropertyId(Integer propertyId) {
        return propertyRepository.getRepairsByPropertyId(propertyId);
    }
}

