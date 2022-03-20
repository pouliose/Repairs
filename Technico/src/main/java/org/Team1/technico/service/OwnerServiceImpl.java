package org.Team1.technico.service;

import lombok.AllArgsConstructor;
import org.Team1.technico.TechnicoMain;
import org.Team1.technico.exceptions.EmailException;
import org.Team1.technico.model.Owner;
import org.Team1.technico.repository.OwnerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@AllArgsConstructor

@Service
public class OwnerServiceImpl implements OwnerService {
    private OwnerRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(TechnicoMain.class);

    @Override
    public Owner createOwner(Owner owner) {
        logger.warn("check for warnings");
        try {
            if(validateEmail(owner.getEmail()))
                return repository.save(owner);
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    @Override
    public List<Owner> readOwner() {
        return repository.findAll();
    }

    @Override
    public Owner readOwner(int ownerId) {
        return repository.findById(ownerId).get();
    }

    @Override
    public Owner updateOwner(int ownerId, Owner owner) {
        Optional<Owner> ownerDb = repository.findById(ownerId);
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
    public boolean deleteOwner(int ownerId) {
        Optional<Owner> ownerDb = repository.findById(ownerId);
        if (ownerDb.isEmpty())
            return false;
        repository.delete(ownerDb.get());
        return true;
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
