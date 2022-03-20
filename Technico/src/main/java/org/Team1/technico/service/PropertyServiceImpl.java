package org.Team1.technico.service;


import lombok.AllArgsConstructor;
import org.Team1.technico.TechnicoMain;
import org.Team1.technico.dto.PropertyDto;
import org.Team1.technico.exceptions.IdentityE9Exception;
import org.Team1.technico.model.Owner;
import org.Team1.technico.model.Property;
import org.Team1.technico.repository.OwnerPropertyRepository;
import org.Team1.technico.repository.OwnerRepository;
import org.Team1.technico.repository.PropertyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.regex.Pattern;

@AllArgsConstructor
@Service

public class PropertyServiceImpl implements PropertyService {

    private PropertyRepository propertyRepository;
    private OwnerRepository ownerRepository;
    private OwnerPropertyRepository ownerPropertyRepository;
    private static final Logger logger = LoggerFactory.getLogger(TechnicoMain.class);


    @Override
    public Property createProperty(Property property) {
        logger.warn("check for warnings");
        try {
            if (validateIdentityE9(property.getIdentityE9()))
                return propertyRepository.save(property);
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    private boolean validateIdentityE9(int identityE9) {
        try {
            String regexPattern = " ";
            boolean b = Pattern.matches(regexPattern, identityE9);
            if (!b)
                throw new IdentityE9Exception("Invalid E9: " + identityE9);
            return true;
        } catch (IdentityE9Exception e) {
            e.getMessage();
            logger.info("Invalid E9: " + identityE9);
        }
        return false;
    }

    @Override
    public List<Property> readProperty() {
        return propertyRepository.findAll();
    }

    @Override
    public Property readProperty(int propertyId) {
        return propertyRepository.findById(propertyId).get();
    }

    @Override
    public Property updateProperty(int propertyId, Property property) {
        Optional<Property> propertyDb = PropertyRepository.findById(propertyId);
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
                            validateIdentityE9(property.getIdentityE9());
                            return propertyRepository.save(match);
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
        return false;
    }

    @Override
    public boolean addProperty(int ownerId, Property property) {

        Optional<Owner> ownerOptional = Optional.of(ownerRepository.findById(ownerId).get());
        if (ownerOptional.isEmpty())
            return false;

        createProperty(property);
        List<Property> properties = ownerOptional.get().getProperties();
        properties.add(property);
        ownerOptional.get().setProperties(properties);
        return true;


    }


    @Override
    public boolean createProperties(int ownerId) {
        Optional<Owner> ownerOpt = ownerRepository.findById(ownerId);
        Optional<Property> propertyOpt = propertyRepository.findById(ownerId);
        if (ownerOpt.isEmpty() || propertyOpt.isEmpty())
            return false;

        Optional<Properties> propertiesOpt
                = ownerPropertyRepository.findByOwnerProperty(ownerId, propertyId);

        if (propertiesOpt.isEmpty()) {
            Properties properties = new Properties();
            properties.setOwner(ownerOpt.get());
            properties.setProperty(propertyOpt.get());
            ownerPropertyRepository.save(properties);
            return true;
        }
        propertiesOpt.get().setQuantity(propertiesOpt.get().getQuantity() + 1);
        ownerPropertyRepository.save(propertiesOpt.get());

        return true;
    }

    @Override
    public List<PropertyDto> properties(int ownerId) {
        return null;
    }


}
    
