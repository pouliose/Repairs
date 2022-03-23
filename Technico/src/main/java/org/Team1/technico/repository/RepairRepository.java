package org.Team1.technico.repository;

import org.Team1.technico.model.Repair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RepairRepository extends JpaRepository<Repair, Integer> {
    @Query("select r from Repair r where r.registrationDate between ?1 and ?2")
    List<Repair> findByRegistrationDateIsBetween(LocalDate registrationDateStart, LocalDate registrationDateEnd);


}
