package org.Team1.technico.service;

import org.Team1.technico.model.Repair;
import org.Team1.technico.repository.RepairRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class RepairServiceImp implements RepairService {
    private RepairRepository repository;
    @Override
    public Repair createRepair(Repair repair) {
        return repository.save(repair);
    }

    @Override
    public List<Repair> readRepair() {
        return repository.findAll();
    }

    @Override
    public Repair readRepair(int repairId) {
        Optional<Repair> repairDb = repository.findById(repairId);
        if (repairDb.isEmpty())
            return null;
        return repairDb.get();

    }

    @Override
    public List<Repair> searchRepair(int ownerId) {
        return null;
    }

    @Override
    public List<Repair> searchRepair(LocalDate firstDateInRange, LocalDate lastDateInRange) {
        return null;
    }

    @Override
    public Repair updateRepair(int repairId, Repair repair) {
        try {
            Optional<Repair> repairDb = Optional.ofNullable(readRepair(repairId));
            return repairDb.
                    map(match -> {
                        try {
                            match.setRegistrationDate(repair.getRegistrationDate());
                            match.setProperty(repair.getProperty());
                            match.setCompletionDate(repair.getCompletionDate());
                            match.setRepairStatus(repair.getRepairStatus());
                            match.setRepairType(repair.getRepairType());
                            match.setCost(repair.getCost());
                            match.setOwner(repair.getOwner());
                            match.setDescription(repair.getDescription());
                            return repository.save(match);

                        } catch (Exception e) {
                            e.getMessage();
                            return null;
                        }
                    }).get();
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    @Override
    public boolean deleteRepair(int repairId) {
        Optional<Repair> repairDb = Optional.ofNullable(readRepair(repairId));
        if (repairDb.isEmpty())
            return false;
        repository.delete(repairDb.get());
        return true;
    }
}
