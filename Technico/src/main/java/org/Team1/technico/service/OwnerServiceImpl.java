package org.Team1.technico.service;

import lombok.AllArgsConstructor;
import org.Team1.technico.TechnicoMain;
import org.Team1.technico.dto.ResponseResult;
import org.Team1.technico.dto.ResponseStatus;
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
    public ResponseResult<Owner> createOwner(Owner owner) {
        try {
            if (validateEmail(owner.getEmail()))
                return new ResponseResult<>(ownerRepository.save(owner), ResponseStatus.SUCCESS, "Oκ");
        } catch (Exception e) {
            e.getMessage();
        }
        return new ResponseResult<>(null, ResponseStatus.OWNER_NOT_CREATED, "Save has failed.");
    }

    @Override
    public ResponseResult<List<Owner>> readOwner() {
        try {
            return new ResponseResult<>(ownerRepository.findAll(), ResponseStatus.SUCCESS, "Oκ");
        } catch (Exception e) {
            return new ResponseResult<>(null, ResponseStatus.OWNER_NOT_FOUND, "Owner not found");
        }
    }

    @Override
    public ResponseResult<Owner> readOwner(int ownerId) {
        try {
            return new ResponseResult<>(ownerRepository.findById(ownerId).get(), ResponseStatus.SUCCESS, "Oκ");
        } catch (Exception e) {
            return new ResponseResult<>(null, ResponseStatus.OWNER_NOT_FOUND, "Owner not found");
        }
    }

    @Override
    public ResponseResult<List<Owner>> getOwnerByVatNumberOrEmail(String vatNumber, String email) {
        try {
            return new ResponseResult<>(ownerRepository.findOwnersByVatNumberOrEmail(vatNumber, email), ResponseStatus.SUCCESS, "Oκ");
        } catch (Exception e) {
            return new ResponseResult<>(null, ResponseStatus.OWNER_NOT_FOUND, "Owner not found");
        }
    }

    @Override
    public ResponseResult<Owner> updateOwner(int ownerId, Owner owner) {
        try {
            Optional<Owner> ownerDb = ownerRepository.findById(ownerId);
            if (ownerDb.isEmpty())
                return new ResponseResult<>(null, ResponseStatus.OWNER_CANNOT_BE_UPDATED, "Fail to update");
            Owner updated = ownerDb.
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
            return new ResponseResult<>(updated, ResponseStatus.SUCCESS, "Oκ");
        } catch (Exception e) {
            return new ResponseResult<>(null, ResponseStatus.OWNER_CANNOT_BE_UPDATED, "Fail to update");
        }
    }

    @Override
    public ResponseResult<Boolean> deleteOwner(int ownerId) {
        try {
            Optional<Owner> ownerDb = ownerRepository.findById(ownerId);
            if (ownerDb.isEmpty() || !ownerDb.get().getProperties().isEmpty())
                return new ResponseResult<>(null, ResponseStatus.OWNER_NOT_FOUND, "Delete has failed.");
            ownerRepository.delete(ownerDb.get());
            return new ResponseResult<>(true, ResponseStatus.SUCCESS, "Oκ");
        } catch (Exception e) {
            return new ResponseResult<>(null, ResponseStatus.OWNER_NOT_FOUND, "Delete has failed.");
        }
    }

    @Override
    public ResponseResult<List<Property>> getPropertiesOfOwner(int ownerId) {
        try {
            Optional<Owner> ownerOptional = ownerRepository.findById(ownerId);
            if (ownerOptional.isEmpty())
                return new ResponseResult<>(null, ResponseStatus.OWNER_NOT_FOUND, "Owner not found");
            return new ResponseResult<>(ownerOptional.get().getProperties(), ResponseStatus.SUCCESS, "Oκ");
        } catch (Exception e) {
            return new ResponseResult<>(null, ResponseStatus.OWNER_NOT_FOUND, "Failed to found properties.");
        }
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
}
