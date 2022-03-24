package org.Team1.technico.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

public class PropertyServiceImpl implements PropertyService {

    private PropertyRepository propertyRepository;
    private OwnerRepository ownerRepository;
    private RepairRepository repairRepository;

    @Override
    public ResponseResult<List<Property>> readProperty() {
        try{
            return new ResponseResult<>(propertyRepository.findAll(), ResponseStatus.SUCCESS, "Οκ");
        } catch(Exception e){
            return new ResponseResult<>(null, ResponseStatus.PROPERTY_NOT_FOUND, "Property not found");
        }
    }

    @Override
    public ResponseResult<Property> readProperty(int propertyId) {
        try{
            Optional<Property> propertyDb = propertyRepository.findById(propertyId);
            return new ResponseResult<>(propertyDb.get(), ResponseStatus.SUCCESS, "Ok");
        } catch(Exception e){
            return new ResponseResult<>(null, ResponseStatus.PROPERTY_NOT_FOUND, "Property not found");
        }
    }

    @Override
    public ResponseResult<Property> updateProperty(int propertyId, Property property) {
        Optional<Property> propertyDb = propertyRepository.findById(propertyId);
        if (propertyDb.isEmpty())
            return new ResponseResult<>(null, ResponseStatus.PROPERTY_NOT_FOUND, "Property id not found");
        try {
            Property updated = propertyDb
                    .map(match -> {
                            match.setIdentityE9(property.getIdentityE9());
                            match.setAddress(property.getAddress());
                            match.setConstructionYear(property.getConstructionYear());
                            match.setPropertyType(property.getPropertyType());
                            return propertyRepository.save(match);
                    }).get();
            return new ResponseResult<>(updated, ResponseStatus.SUCCESS, "Οκ");
        } catch (Exception e) {
            return new ResponseResult<>(null, ResponseStatus.PROPERTY_NOT_FOUND, "Save has failed.");
        }
    }

    @Override
    public ResponseResult<Boolean> deleteProperty(int propertyId) {
        try{
            Optional<Property> propertyDb = propertyRepository.findById(propertyId);
            if (propertyDb.isEmpty() || !propertyDb.get().getRepairs().isEmpty())
                return new ResponseResult<Boolean>(false, ResponseStatus.PROPERTY_CAN_NOT_BE_DELETED, "Delete has failed.");
            propertyRepository.delete(propertyDb.get());
            return new ResponseResult<Boolean>(true, ResponseStatus.SUCCESS, "Ok");
        } catch(Exception e){
            return new ResponseResult<Boolean>(false, ResponseStatus.PROPERTY_CAN_NOT_BE_DELETED, "Delete has failed.");
        }
    }


    @Override
    public  ResponseResult<Boolean> addPropertyToOwner(Property property, int ownerId) {
        try{
            Optional<Owner> ownerOptional = ownerRepository.findById(ownerId);
            if (ownerOptional.isPresent()) {
                property.setOwner(ownerOptional.get());
                property.setOwnerVatNumber(ownerOptional.get().getVatNumber());
                propertyRepository.save(property);
                return new ResponseResult<>(true, ResponseStatus.SUCCESS, "Ok");
            }
        } catch(Exception e){
            e.getMessage();
        }
        return new ResponseResult<Boolean>(false, ResponseStatus.OWNER_NOT_FOUND, "The owner cannot be found");
    }

    @Override
    public ResponseResult<List<Property>> getByOwnerVatNumberOrIdentityE9(String ownerVatNumber, String identityE9) {
        try{
            if (identityE9 != null || ownerVatNumber != null) {
                return new ResponseResult<>(propertyRepository.findByOwnerVatNumberOrIdentityE9(ownerVatNumber, identityE9), ResponseStatus.SUCCESS, "Οκ");
            }
        } catch(Exception e){
            e.getMessage();
        }
        return new ResponseResult<>(null, ResponseStatus.PROPERTY_NOT_FOUND, "Property not found for the given search values.");
    }

    @Override
    public ResponseResult<List<Repair>> getRepairsByPropertyId(Integer propertyId) {
        try{
            return new ResponseResult<>(propertyRepository.getRepairsByPropertyId(propertyId), ResponseStatus.SUCCESS, "Οκ");
        } catch(Exception e){
            return new ResponseResult<>(null, ResponseStatus.REPAIR_NOT_FOUND, "Repairs cannot be found");
        }
    }
}
