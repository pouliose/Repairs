package org.Team1.technico.service;

import org.Team1.technico.dto.ResponseResult;
import org.Team1.technico.model.Owner;
import org.Team1.technico.model.Property;

import java.util.List;

public interface OwnerService {
    ResponseResult<Owner> createOwner(Owner owner);
    ResponseResult<List<Owner>> readOwner();
    ResponseResult<Owner> readOwner(int ownerId);
    ResponseResult<List<Owner>> getOwnerByVatNumberOrEmail(String vatNumber, String email);
    ResponseResult<Owner> updateOwner(int ownerId, Owner owner);
    ResponseResult<Boolean> deleteOwner(int ownerId);
    ResponseResult<List<Property>> getPropertiesOfOwner(int ownerId);
}
