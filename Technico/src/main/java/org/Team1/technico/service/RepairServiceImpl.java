package org.Team1.technico.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.Team1.technico.dto.ResponseResult;
import org.Team1.technico.dto.ResponseStatus;
import org.Team1.technico.model.Property;
import org.Team1.technico.model.Repair;
import org.Team1.technico.repository.PropertyRepository;
import org.Team1.technico.repository.RepairRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Service
public class RepairServiceImpl implements RepairService {

    private RepairRepository repairRepository;
    private PropertyRepository propertyRepository;





    @Override
    public ResponseResult<List<Repair>> readRepair() {
        try {
            return new ResponseResult<>(repairRepository.findAll(), ResponseStatus.SUCCESS, "ok");
        } catch (Exception e) {
            return new ResponseResult<>(null, ResponseStatus.REPAIR_NOT_FOUND, e.getMessage());
        }
    }

    @Override
    public ResponseResult<Repair> readRepair(int repairId) {
        Optional<Repair> repairDb = repairRepository.findById(repairId);
        try {

            return new ResponseResult<>(repairDb.get(), ResponseStatus.SUCCESS, "ok");
        } catch (Exception e) {
            return new ResponseResult<>(null, ResponseStatus.REPAIR_NOT_FOUND, e.getMessage());
        }

    }



@Override
    public ResponseResult<Repair> updateRepair(int repairId, Repair repair) {



            try {
                Optional<Repair> repairDb = repairRepository.findById(repairId);
                Repair match = repairDb.get();


                match.setRegistrationDate(repair.getRegistrationDate());
                match.setProperty(repair.getProperty());
                match.setCompletionDate(repair.getCompletionDate());
                match.setRepairStatus(repair.getRepairStatus());
                match.setRepairType(repair.getRepairType());
                match.setCost(repair.getCost());
                match.setDescription(repair.getDescription());
                repairRepository.save(match);
                return new ResponseResult<>(match, ResponseStatus.SUCCESS, "ok");

            } catch (Exception e) {
                return new ResponseResult<>(null, ResponseStatus.REPAIR_NOT_FOUND, e.getMessage());
            }



}

    @Override
    public ResponseResult<Boolean> deleteRepair(int repairId) {
       try{
           Optional<Repair> repairDb = repairRepository.findById(repairId);
           repairRepository.delete(repairDb.get());
           return new ResponseResult<>(true, ResponseStatus.SUCCESS, "ok");
       }
       catch (Exception e) {
           return new ResponseResult<>(null, ResponseStatus.REPAIR_NOT_FOUND, e.getMessage());
       }
    }

    @Override
    public ResponseResult<Boolean> addRepairToProperty(Repair repair, int propertyId) {
        Optional<Property> propertyOptional = propertyRepository.findById(propertyId);

        if (propertyOptional.isPresent()) {
            repair.setRegistrationDate(LocalDate.now());
            repair.setProperty(propertyOptional.get());

            repairRepository.save(repair);
            return new ResponseResult<>(true, ResponseStatus.SUCCESS, "ok");
        }
        return new ResponseResult<>(false, ResponseStatus.PROPERTY_NOT_FOUND, "The property cannot be found");
    }

//    @Override
//    public ResponseResult<Integer> createRepairToProperty(int propertyId) {
//        log.debug("Create repair", propertyId);
//        Optional<Property> propertyOptional = propertyRepository.findById(propertyId);
//
//        if (propertyOptional.isEmpty()) {
//
//            log.debug("Create repair method", ResponseStatus.PROPERTY_NOT_FOUND);
//            return new ResponseResult<>(-1,
//                    ResponseStatus.PROPERTY_NOT_FOUND, "The property cannot be found");
//        }
//        Repair repair = new Repair();
//
//        repair.setProperty(propertyOptional.get());
//
//        try {
//            repairRepository.save(repair);
//        } catch (Exception e) {
//            log.debug("Create repair method ", ResponseStatus.REPAIR_NOT_CREATED);
//            return new ResponseResult<>(-1, ResponseStatus.REPAIR_NOT_CREATED, "The repair has NOT been saved");
//        }
//
//        log.debug("Create repair method", ResponseStatus.SUCCESS);
//        return new ResponseResult<>(repair.getId(),
//                ResponseStatus.SUCCESS, "The repair has been created successfully");
//    }


    @Override
    public ResponseResult<List<Repair>> getByRegistrationDateIsBetween(LocalDate registrationDateStart, LocalDate registrationDateEnd) {

        return new ResponseResult<>(repairRepository.findByRegistrationDateIsBetween(registrationDateStart, registrationDateEnd),
                ResponseStatus.SUCCESS, "Ok");
    }

    @Override
    public ResponseResult<List<Repair>> getByOwner_Id(Integer id) {
        return new ResponseResult<>(repairRepository.findByProperty_Owner_Id(id), ResponseStatus.SUCCESS, "Ok");
    }
}
