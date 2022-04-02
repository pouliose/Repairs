package org.Team1.technico.service;

import org.Team1.technico.model.Property;
import org.Team1.technico.model.Repair;
import org.Team1.technico.utils.ResponseResult;

import java.util.List;

public interface PropertyService {
    /**
     * @param property
     * @param ownerId
     * @return ResponseResult<Boolean>
     */
    ResponseResult<Boolean> addPropertyToOwner(Property property, int ownerId);

    /**
     * @return ResponseResult<List < Property>>
     */
    ResponseResult<List<Property>> readProperty();

    /**
     * @param propertyId
     * @return ResponseResult<Property>
     */
    ResponseResult<Property> readProperty(int propertyId);

    /**
     * @param propertyId
     * @param property
     * @return ResponseResult<Property>
     */
    ResponseResult<Property> updateProperty(int propertyId, Property property);

    /**
     * @param propertyId
     * @return ResponseResult<Boolean>
     */
    ResponseResult<Boolean> deleteProperty(int propertyId);

    /**
     * @param ownerVatNumber
     * @param identityE9
     * @return ResponseResult<List < Property>>
     */
    ResponseResult<List<Property>> getByOwnerVatNumberOrIdentityE9(String ownerVatNumber, String identityE9);

    /**
     * @param propertyId
     * @return ResponseResult<List < Repair>>
     */
    ResponseResult<List<Repair>> getRepairsByPropertyId(Integer propertyId);
}

