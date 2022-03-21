package org.Team1.technico.repository;

import org.Team1.technico.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Integer> {
    List<Owner> findOwnersByVatNumberOrEmail(String vatNumber, String email);

}
