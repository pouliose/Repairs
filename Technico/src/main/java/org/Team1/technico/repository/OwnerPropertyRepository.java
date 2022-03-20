package org.Team1.technico.repository;

import org.Team1.technico.model.Owner;
import org.Team1.technico.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Properties;


public interface OwnerPropertyRepository extends JpaRepository<Owner, Property> {




}
