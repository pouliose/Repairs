package org.Team1.technico.service;


import lombok.AllArgsConstructor;
import org.Team1.technico.model.Property;
import org.Team1.technico.model.Repair;
import org.Team1.technico.repository.PropertyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service

public class PropertyServiceImpl implements PropertyService {

    private PropertyRepository repository;



    @Override
    public Property createProperty(Property property) {
        return repository.save(property);
    }

//    private boolean validateIdentityE9(int identityE9) {
//        try {
//            String regexPattern = " ";
//            boolean b = Pattern.matches(regexPattern, identityE9);
//            if (!b)
//                throw new IdentityE9Exception("Invalid E9: " + identityE9);
//            return true;
//        } catch (IdentityE9Exception e) {
//            e.getMessage();
//            logger.info("Invalid E9: " + identityE9);
//        }
//        return false;
//    }

    @Override
    public List<Property> readProperty() {
        return repository.findAll();
    }

    @Override
    public Property readProperty(int propertyId) {
        Optional<Property> propertyDb = repository.findById(propertyId);
        if (propertyDb.isEmpty())
                return null;

        return propertyDb.get();
    }

    @Override
    public Property updateProperty(int propertyId, Property property) {
        Optional<Property> propertyDb = repository.findById(propertyId);
        if (propertyDb.isEmpty())
            return null;
        try {
            return propertyDb
                    .map(match -> {
                        try {
                            match.setIdentityE9(property.getIdentityE9());
                            match.setAddress(property.getAddress());
                            match.setConstructionYear(property.getConstructionYear());
                            match.setPropertyType(property.getPropertyType());
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
    public boolean deleteProperty(int propertyId) {
        Optional<Property> propertyDb = Optional.ofNullable(readProperty(propertyId));
        if (propertyDb.isEmpty())
            return false;
        repository.delete(propertyDb.get());
        return true;


    }

//    @Override
//    public boolean addProperty(int ownerId, Property property) {
//
//        Optional<Owner> ownerOptional = Optional.of(ownerRepository.findById(ownerId).get());
//        if (ownerOptional.isEmpty())
//            return false;
//
//        createProperty(property);
//        List<Property> properties = ownerOptional.get().getProperties();
//        properties.add(property);
//        ownerOptional.get().setProperties(properties);
//        return true;
//
//
//    }
//
//
//    @Override
//    public boolean createProperties(int ownerId) {
//        Optional<Owner> ownerOpt = ownerRepository.findById(ownerId);
//        Optional<Property> propertyOpt = propertyRepository.findById(ownerId);
//        if (ownerOpt.isEmpty() || propertyOpt.isEmpty())
//            return false;
//
//        Optional<Properties> propertiesOpt
//                = ownerPropertyRepository.findByOwnerProperty(ownerId, propertyId);
//
//        if (propertiesOpt.isEmpty()) {
//            Properties properties = new Properties();
//            properties.setOwner(ownerOpt.get());
//            properties.setProperty(propertyOpt.get());
//            ownerPropertyRepository.save(properties);
//            return true;
//        }
//        propertiesOpt.get().setQuantity(propertiesOpt.get().getQuantity() + 1);
//        ownerPropertyRepository.save(propertiesOpt.get());
//
//        return true;
//    }

//    @Override
//    public List<PropertyDto> properties(int ownerId) {
//        return null;
//    }


}
    
