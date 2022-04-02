package org.Team1.technico.service;

import lombok.AllArgsConstructor;
import org.Team1.technico.model.Property;
import org.Team1.technico.model.Repair;
import org.Team1.technico.repository.PropertyRepository;
import org.Team1.technico.repository.RepairRepository;
import org.Team1.technico.utils.ResponseResult;
import org.Team1.technico.utils.ResponseStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class RepairServiceImpl implements RepairService {

    private RepairRepository repairRepository;
    private PropertyRepository propertyRepository;

    @Override
    public ResponseResult<Boolean> addRepairToProperty(Repair repair, int propertyId) {
        Optional<Property> propertyOptional = propertyRepository.findById(propertyId);
        try {
            if (propertyOptional.isPresent()) {
                repair.setRegistrationDate(LocalDate.now());
                repair.setProperty(propertyOptional.get());
                repairRepository.save(repair);
                return new ResponseResult<>(true, ResponseStatus.SUCCESS, "Ok");
            } else
                return new ResponseResult<>(false, ResponseStatus.PROPERTY_NOT_FOUND, "There is any property with id= " + propertyId);
        } catch (Exception e) {
            e.getMessage();
        }
        return new ResponseResult<>(false, ResponseStatus.REPAIR_NOT_CREATED, "Failed to create repair.");
    }

    @Override
    public ResponseResult<List<Repair>> readRepair() {
        try {
            List<Repair> repairs = repairRepository.findAll();
            if (repairs != null && !repairs.isEmpty())
                return new ResponseResult<>(repairs, ResponseStatus.SUCCESS, "Ok");
            else
                return new ResponseResult<>(repairs, ResponseStatus.SUCCESS, "No repairs to show.");
        } catch (Exception e) {
            e.getMessage();
            return new ResponseResult<>(null, ResponseStatus.REPAIR_NOT_FOUND, "Failed to find repairs.");
        }
    }

    @Override
    public ResponseResult<Repair> readRepair(int repairId) {
        try {
            Optional<Repair> repairDb = repairRepository.findById(repairId);
            if (repairDb != null && repairDb.isPresent())
                return new ResponseResult<>(repairDb.get(), ResponseStatus.SUCCESS, "Ok");
            else
                return new ResponseResult<>(null, ResponseStatus.SUCCESS, "There is not any repair with id: " + repairId);
        } catch (Exception e) {
            e.getMessage();
            return new ResponseResult<>(null, ResponseStatus.REPAIR_NOT_FOUND, "Failed to find repair.");
        }
    }


    @Override
    public ResponseResult<Repair> updateRepair(int repairId, Repair repair) {
        try {
            Optional<Repair> repairDb = repairRepository.findById(repairId);
            if(!repairDb.isEmpty()){
                Repair match = repairDb.get();
                match.setRegistrationDate(repair.getRegistrationDate());
                // The UI will not need to specify the property in which the repair already belongs
                //match.setProperty(repair.getProperty());
                match.setCompletionDate(repair.getCompletionDate());
                match.setRepairStatus(repair.getRepairStatus());
                match.setRepairType(repair.getRepairType());
                match.setCost(repair.getCost());
                match.setDescription(repair.getDescription());
                repairRepository.save(match);
                return new ResponseResult<>(match, ResponseStatus.SUCCESS, "Ok");
            } else
                return new ResponseResult<>(null, ResponseStatus.REPAIR_NOT_FOUND, "Repair with id: " + repairId + " does not exist.");
        } catch (Exception e) {
            e.getMessage();
            return new ResponseResult<>(null, ResponseStatus.REPAIR_CANNOT_BE_UPDATED, "Update has failed.");
        }
    }

    @Override
    public ResponseResult<Boolean> deleteRepair(int repairId) {
        try {
            Optional<Repair> repairDb = repairRepository.findById(repairId);
            if (repairDb.isEmpty())
                return new ResponseResult<>(false, ResponseStatus.REPAIR_NOT_FOUND, "There is not any repair with id: " + repairId);
            repairRepository.delete(repairDb.get());
            return new ResponseResult<>(true, ResponseStatus.SUCCESS, "Ok");
        } catch (Exception e) {
            e.getMessage();
            return new ResponseResult<>(null, ResponseStatus.REPAIR_CANNOT_BE_DELETED, "Delete has failed.");
        }
    }

    @Override
    public ResponseResult<List<Repair>> getByRegistrationDateIsBetween(LocalDate registrationDateStart, LocalDate registrationDateEnd) {
        try {
            List<Repair> repairs = repairRepository.findByRegistrationDateIsBetween(registrationDateStart, registrationDateEnd);
            if (!repairs.isEmpty())
                return new ResponseResult<>(repairs, ResponseStatus.SUCCESS, "Ok");
            else
                return new ResponseResult<>(null, ResponseStatus.REPAIR_NOT_FOUND, "No search result for the given time period.");
        } catch (Exception e) {
            return new ResponseResult<>(null, ResponseStatus.REPAIR_NOT_FOUND, "Failed to find repair.");
        }
    }

    @Override
    public ResponseResult<List<Repair>> getByOwnerId(Integer ownerId) {
        try {
            List<Repair> repairs = repairRepository.findByProperty_Owner_Id(ownerId);
            if(repairs.isEmpty())
                return new ResponseResult<>(null, ResponseStatus.REPAIR_NOT_FOUND, "There are not any repairs for owner with id: " + ownerId);
            return new ResponseResult<>(repairs, ResponseStatus.SUCCESS, "Ok");
        } catch (Exception e) {
            return new ResponseResult<>(null, ResponseStatus.REPAIR_NOT_FOUND, "Failed to find repairs.");
        }
    }
}
