package org.Team1.technico.service;

import org.Team1.technico.dto.PropertyDto;
import org.Team1.technico.dto.ResponseResult;
import org.Team1.technico.model.Property;
import org.Team1.technico.model.Repair;

import java.util.List;

public interface PropertyService {
    Property createProperty(Property property);

    List<Property> readProperty();

    Property readProperty(int propertyId);

    Property updateProperty(int propertyId, Property property);

    boolean deleteProperty(int propertyId);

    ResponseResult<Boolean> addPropertyToOwner(Property property, int ownerId);

//    ResponseResult<Integer> addPropertyToOwner(int ownerId);

//    List<Property> getPropertiesByPropertyIdOrOwnerVatNumber(Integer propertyId, String ownerVatNumber);
    List<Property> getByOwnerVatNumberOrIdentityE9(String ownerVatNumber, String identityE9);

    List<Repair> getRepairsByPropertyId(Integer propertyId);

    ResponseResult<List<PropertyDto>> ownerPropertyRepairs(int customerId);
}

