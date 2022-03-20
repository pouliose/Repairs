package org.Team1.technico.repository;

import org.Team1.technico.model.Owner;
import org.Team1.technico.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Properties;


public interface OwnerPropertyRepository extends JpaRepository<Owner, Property> {

    @Query("Select bp from BasketProduct bp where bp.basket.id = :basketId and bp.product.id= :productId")
    Optional<Properties> findByOwnerProperty(int ownerId, int propertyId);


}
