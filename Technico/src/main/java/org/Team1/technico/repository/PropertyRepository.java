package org.Team1.technico.repository;

import org.Team1.technico.model.Property;
import org.Team1.technico.model.Repair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer> {
    List<Property> findPropertyByIdOrOwnerVatNumber(Integer propertyId, String vatNumber);
    List<Property> findPropertyByOwnerId(int ownerId);

    @Query(value ="Select p.repairs from Property p where p.id = : propertyId")
    List<Repair> getRepairsByPropertyId(int propertyId);
}
