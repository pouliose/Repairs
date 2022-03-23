package org.Team1.technico.service;

import org.Team1.technico.model.Owner;
import org.Team1.technico.model.Property;

import java.util.List;

public interface OwnerService {
    Owner createOwner(Owner owner);
    List<Owner> readOwner();
    Owner readOwner(int ownerId);
    List<Owner> getOwnerByVatNumberOrEmail(String vatNumber, String email);
    Owner updateOwner(int ownerId, Owner owner);
    boolean deleteOwner(int ownerId);
    List<Property> getPropertiesOfOwner(int ownerId);
}
