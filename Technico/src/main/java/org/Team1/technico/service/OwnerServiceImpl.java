package org.Team1.technico.service;

import lombok.AllArgsConstructor;
import org.Team1.technico.TechnicoMain;
import org.Team1.technico.dto.PropertyDto;
import org.Team1.technico.exceptions.EmailException;
import org.Team1.technico.model.Owner;
import org.Team1.technico.model.Property;
import org.Team1.technico.repository.OwnerRepository;
import org.Team1.technico.repository.PropertyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@AllArgsConstructor

@Service
public class OwnerServiceImpl implements OwnerService {
    private OwnerRepository ownerRepository;
    private PropertyRepository propertyRepository;
    private static final Logger logger = LoggerFactory.getLogger(TechnicoMain.class);

    @Override
    public Owner createOwner(Owner owner) {
        try {
            if (validateEmail(owner.getEmail()))
                return ownerRepository.save(owner);
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    @Override
    public List<Owner> readOwner() {
        return ownerRepository.findAll();
    }

    @Override
    public Owner readOwner(int ownerId) {
        return ownerRepository.findById(ownerId).get();
    }

    @Override
    public List<Owner> getOwnerByVatNumberOrEmail(String vatNumber, String email) {
        if (vatNumber != null || email != "" && email != null)
            return ownerRepository.findOwnersByVatNumberOrEmail(vatNumber, email);
        return null;
    }

    @Override
    public Owner updateOwner(int ownerId, Owner owner) {
        Optional<Owner> ownerDb = ownerRepository.findById(ownerId);
        if (ownerDb.isEmpty())
            return null;
        try {
            return ownerDb.
                    map(match -> {
                        try {
                            match.setVatNumber(owner.getVatNumber());
                            match.setFirstName(owner.getFirstName());
                            match.setLastName(owner.getLastName());
                            match.setAddress(owner.getAddress());
                            match.setPhoneNumber(owner.getPhoneNumber());
                            validateEmail(owner.getEmail());
                            match.setEmail(owner.getEmail());
                            match.setUsername(owner.getUsername());
                            match.setPassword(owner.getPassword());
                            return ownerRepository.save(match);

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
    public boolean deleteOwner(int ownerId) {
        Optional<Owner> ownerDb = ownerRepository.findById(ownerId);
        if (ownerDb.isEmpty() || ! ownerDb.get().getProperties().isEmpty())
            return false;
        ownerRepository.delete(ownerDb.get());
        return true;
    }

    @Override
    public List<Property> getPropertiesOfOwner(int ownerId) {
        Optional<Owner> ownerOptional = ownerRepository.findById(ownerId);
        if (ownerOptional.isEmpty())
            return null;

        return ownerOptional.get().getProperties();

//        List<PropertyDto> propertiesDto = propertyRepository.findAll()
//                .stream()
//                .filter(property -> property.getOwner().getId() == ownerId)
//                .map(property -> new PropertyDto(property.getId(), property.getIdentityE9(), property.getAddress(), property.getConstructionYear(), property.getPropertyType())).toList();
//        return propertiesDto;
//        List<Property> properties = propertyRepository.findPropertyByOwnerId(ownerId);
//        List<PropertyDto> propertiesDto = properties.stream().map(property -> new PropertyDto(property.getId(), property.getIdentityE9(), property.getAddress(), property.getConstructionYear(), property.getPropertyType())).toList();
    }

    public boolean validateEmail(String email) {
        try {
            String regexPattern = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
            boolean b = Pattern.matches(regexPattern, email);
            if (!b)
                throw new EmailException("Invalid email: " + email);
            return true;
        } catch (EmailException e) {
            e.getMessage();
            logger.info("Invalid email: " + email);
        }
        return false;
    }

    /*@Override
    public boolean addPropertyToOwner(int ownerId, int propertyId) {
        Optional<Owner> ownerOptional = ownerRepository.findById(ownerId);
        Optional<Property> propertyOptional = propertyRepository.findById(propertyId);
        Owner ownerToBeUpdated = new Owner();
        if (ownerOptional.isPresent() && propertyOptional.isPresent()){
            ownerToBeUpdated = ownerOptional.get();
            List<Property> properties = new ArrayList<>();
            properties = ownerToBeUpdated.getProperties();
            Property propertyToAdd = propertyOptional.get();
            properties.add(propertyToAdd);
            ownerToBeUpdated.setProperties(properties);
            ownerRepository.save(ownerToBeUpdated);
            return true;
        }
        return false;
    }*/
}
