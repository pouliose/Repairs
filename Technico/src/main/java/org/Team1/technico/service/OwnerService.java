package org.Team1.technico.service;

import org.Team1.technico.model.Owner;

import java.util.List;

public interface OwnerService {
    Owner createOwner(Owner owner);
    List<Owner> readOwner();
    Owner readOwner(int ownerId);
    Owner updateOwner(int ownerId, Owner owner);
    boolean deleteOwner(int ownerId);
}
