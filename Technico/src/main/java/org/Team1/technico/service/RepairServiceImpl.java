package org.Team1.technico.service;

import lombok.AllArgsConstructor;
import org.Team1.technico.model.Property;
import org.Team1.technico.model.Repair;
import org.Team1.technico.repository.OwnerRepository;
import org.Team1.technico.repository.PropertyRepository;
import org.Team1.technico.repository.RepairRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor

@Service
public class RepairServiceImpl implements RepairService {
    private RepairRepository repairRepository;
    private PropertyRepository propertyRepository;
    private OwnerRepository ownerRepository;

    @Override
    public Repair createRepair(Repair repair) {
        repair.setRegistrationDate(LocalDateTime.now());
        return repairRepository.save(repair);
    }

    @Override
    public List<Repair> readRepair() {
        return repairRepository.findAll();
    }

    @Override
    public Repair readRepair(int repairId) {
        Optional<Repair> repairDb = repairRepository.findById(repairId);
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
                            return repairRepository.save(match);

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
        repairRepository.delete(repairDb.get());
        return true;
    }

    @Override
    public boolean addRepairToProperty(int repairId, int propertyId) {
        Optional<Property> propertyOptional = propertyRepository.findById(propertyId);
        Optional<Repair> repairOptional = repairRepository.findById(repairId);
        if (propertyOptional.isPresent() && repairOptional.isPresent()){
            Repair repairToUpdate = repairOptional.get();
            Property propertyToAdd = propertyOptional.get();
            repairToUpdate.setProperty(propertyToAdd);
            repairRepository.save(repairToUpdate);
            return true;
        }
        return false;

    }
}
