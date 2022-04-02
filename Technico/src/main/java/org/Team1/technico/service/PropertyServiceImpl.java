package org.Team1.technico.service;

import lombok.AllArgsConstructor;
import org.Team1.technico.model.Owner;
import org.Team1.technico.model.Property;
import org.Team1.technico.model.Repair;
import org.Team1.technico.repository.OwnerRepository;
import org.Team1.technico.repository.PropertyRepository;
import org.Team1.technico.utils.ResponseResult;
import org.Team1.technico.utils.ResponseStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service

public class PropertyServiceImpl implements PropertyService {

    private PropertyRepository propertyRepository;
    private OwnerRepository ownerRepository;

    /**
     * The method checkNewProperty ensures that the provided property param compromises with the Property model requirements
     *
     * @param property
     * @param ownerId
     * @return ResponseResult<Boolean>
     */
    @Override
    public ResponseResult<Boolean> addPropertyToOwner(Property property, int ownerId) {
        try {
            return checkNewProperty(property, ownerId);
        } catch (Exception e) {
            e.getMessage();
        }
        return new ResponseResult<Boolean>(false, ResponseStatus.PROPERTY_NOT_CREATED, "Property failed to be created.");
    }

    @Override
    public ResponseResult<List<Property>> readProperty() {
        try {
            List<Property> properties = propertyRepository.findAll();
            if (properties != null && !properties.isEmpty())
                return new ResponseResult<>(properties, ResponseStatus.SUCCESS, "Οκ");
            else
                return new ResponseResult<>(null, ResponseStatus.SUCCESS, "No properties to show.");
        } catch (Exception e) {
            return new ResponseResult<>(null, ResponseStatus.PROPERTY_NOT_FOUND, "Property not found");
        }
    }

    @Override
    public ResponseResult<Property> readProperty(int propertyId) {
        try {
            Property propertyDb = propertyRepository.findById(propertyId).get();
            if (propertyDb != null)
                return new ResponseResult<>(propertyDb, ResponseStatus.SUCCESS, "Ok");
            else
                return new ResponseResult<>(propertyDb, ResponseStatus.SUCCESS, "There is any property with id= " + propertyId);
        } catch (Exception e) {
            return new ResponseResult<>(null, ResponseStatus.PROPERTY_NOT_FOUND, "Property not found");
        }
    }

    /**
     * The method checkPropertyUpdatedValues ensures that the param property compromises with the Property model requirements
     *
     * @param propertyId
     * @param property
     * @return
     */
    @Override
    public ResponseResult<Property> updateProperty(int propertyId, Property property) {
        try {
            Optional<Property> propertyDb = propertyRepository.findById(propertyId);
            if (propertyDb.isEmpty())
                return new ResponseResult<>(null, ResponseStatus.OWNER_CANNOT_BE_UPDATED, "Property with id: " + propertyId + " does not exist.");
            return checkPropertyUpdatedValues(propertyId, property);
        } catch (Exception e) {
            e.getMessage();
        }
        return new ResponseResult<>(null, ResponseStatus.PROPERTY_CANNOT_BE_UPDATED, "Update has failed.");
    }

    @Override
    public ResponseResult<Boolean> deleteProperty(int propertyId) {
        try {
            Optional<Property> propertyDb = propertyRepository.findById(propertyId);
            if (propertyDb.isEmpty())
                return new ResponseResult<Boolean>(false, ResponseStatus.PROPERTY_NOT_FOUND, "The id: " + propertyId + " you have provided does not match any property.");
            else if (!propertyDb.get().getRepairs().isEmpty())
                return new ResponseResult<Boolean>(false, ResponseStatus.PROPERTY_CANNOT_BE_DELETED, "Firstly, delete the repairs that are connected with the property and then try again.");
            propertyRepository.delete(propertyDb.get());
            return new ResponseResult<Boolean>(true, ResponseStatus.SUCCESS, "Ok");
        } catch (Exception e) {
            return new ResponseResult<Boolean>(false, ResponseStatus.PROPERTY_CANNOT_BE_DELETED, "Delete has failed.");
        }
    }

    @Override
    public ResponseResult<List<Property>> getByOwnerVatNumberOrIdentityE9(String ownerVatNumber, String identityE9) {
        try {
            List<Property> properties = propertyRepository.findByOwnerVatNumberOrIdentityE9(ownerVatNumber, identityE9);
            if (!properties.isEmpty())
                return new ResponseResult<>(properties, ResponseStatus.SUCCESS, "Οκ");
            else
                return new ResponseResult<>(null, ResponseStatus.SUCCESS, "No search results.");
        } catch (Exception e) {
            e.getMessage();
            return new ResponseResult<>(null, ResponseStatus.PROPERTY_NOT_FOUND, "Failed to find property.");
        }
    }

    @Override
    public ResponseResult<List<Repair>> getRepairsByPropertyId(Integer propertyId) {
        try {
            Optional<Property> property = propertyRepository.findById(propertyId);
            if (property.isEmpty())
                return new ResponseResult<>(null, ResponseStatus.PROPERTY_NOT_FOUND, "There is not any property with id:  " + propertyId);
            List<Repair> repairs = propertyRepository.getRepairsByPropertyId(propertyId);
            if (!repairs.isEmpty())
                return new ResponseResult<>(repairs, ResponseStatus.SUCCESS, "Οκ");
            return new ResponseResult<>(null, ResponseStatus.REPAIR_NOT_FOUND, "There is not any repair for property with id:  " + propertyId);
        } catch (Exception e) {
            return new ResponseResult<>(null, ResponseStatus.REPAIR_NOT_FOUND, "Failed to find property.");
        }
    }

    /**
     * The method ensures that the provided property param compromises with the Property model requirements
     *
     * @param property
     * @param ownerId
     * @return
     */
    public ResponseResult<Boolean> checkNewProperty(Property property, int ownerId) {
        String message;
        //IdentityE9 validation
        if (property.getIdentityE9() == null || property.getIdentityE9().length() < 2) {
            message = "Identity number E9 is mandatory field.";
            return new ResponseResult<>(false, ResponseStatus.PROPERTY_NOT_CREATED, message);
        } else {
            if (propertyRepository.findAll().stream().filter(propertyVar -> propertyVar.getIdentityE9().equals(property.getIdentityE9())).count() == 1) {
                message = "The identity number E9: " + property.getIdentityE9() + " ,you provided is already used from another property.";
                return new ResponseResult<>(false, ResponseStatus.PROPERTY_NOT_CREATED, message);
            }
        }
        //Address validation
        if (property.getAddress() == null || property.getAddress().length() < 2) {
            message = "Address is mandatory field.";
            return new ResponseResult<>(false, ResponseStatus.PROPERTY_NOT_CREATED, message);
        }
        Optional<Owner> ownerOptional = ownerRepository.findById(ownerId);
        if (ownerOptional.isPresent()) {
            property.setOwner(ownerOptional.get());
            property.setOwnerVatNumber(ownerOptional.get().getVatNumber());
            propertyRepository.save(property);
            return new ResponseResult<>(true, ResponseStatus.SUCCESS, "Ok");
        } else
            return new ResponseResult<Boolean>(false, ResponseStatus.OWNER_NOT_FOUND, "There is no owner with id: " + ownerId);
    }

    /**
     * The method ensures that the provided property param compromises with the Property model requirements
     *
     * @param propertyId
     * @param property
     * @return
     */
    public ResponseResult<Property> checkPropertyUpdatedValues(int propertyId, Property property) {
        String message;
        //IdentityE9 validation
        if (property.getIdentityE9() != null) {
            if (!propertyRepository.findById(propertyId).get().getIdentityE9().equals(property.getIdentityE9())) {
                if (propertyRepository.findAll().stream().filter(propertyVar -> propertyVar.getIdentityE9().equals(property.getIdentityE9())).count() == 1) {
                    message = "The identity number E9 you provided is already used from another property.";
                    return new ResponseResult<>(null, ResponseStatus.PROPERTY_CANNOT_BE_UPDATED, message);
                }
            }
            if (property.getAddress() == null || property.getAddress().length() < 2) {
                message = "Address is mandatory field.";
                return new ResponseResult<>(null, ResponseStatus.PROPERTY_CANNOT_BE_UPDATED, message);
            }

        }
        Optional<Property> propertyDb = propertyRepository.findById(propertyId);
        Property updated = propertyDb
                .map(match -> {
                    match.setIdentityE9(property.getIdentityE9());
                    match.setAddress(property.getAddress());
                    match.setConstructionYear(property.getConstructionYear());
                    match.setPropertyType(property.getPropertyType());
                    return propertyRepository.save(match);
                }).get();
        return new ResponseResult<>(updated, ResponseStatus.SUCCESS, "Οκ");
    }
}
