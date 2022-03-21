package org.Team1.technico.repository;

import org.Team1.technico.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer> {
    List<Property> findPropertyByIdOrOwnerVatNumber(Integer propertyId, String varNumber);
    List<Property> findPropertyByOwnerId(int ownerId);
}
