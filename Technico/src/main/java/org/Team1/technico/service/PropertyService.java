package org.Team1.technico.service;

import org.Team1.technico.dto.ResponseResult;
import org.Team1.technico.model.Property;
import org.Team1.technico.model.Repair;

import java.util.List;

public interface PropertyService {

    ResponseResult<List<Property>> readProperty();
    ResponseResult<Property> readProperty(int propertyId);
    ResponseResult<Property> updateProperty(int propertyId, Property property);
    ResponseResult<Boolean> deleteProperty(int propertyId);
    ResponseResult<Boolean> addPropertyToOwner(Property property, int ownerId);

    ResponseResult<List<Property>> getByOwnerVatNumberOrIdentityE9(String ownerVatNumber, String identityE9);
    ResponseResult<List<Repair>> getRepairsByPropertyId(Integer propertyId);
}

