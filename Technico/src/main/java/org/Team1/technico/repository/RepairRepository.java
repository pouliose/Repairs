package org.Team1.technico.repository;

import org.Team1.technico.model.Repair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepairRepository extends JpaRepository<Repair, Integer> {
}
