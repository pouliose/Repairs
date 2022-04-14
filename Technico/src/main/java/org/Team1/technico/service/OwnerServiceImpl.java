package org.Team1.technico.service;

import lombok.AllArgsConstructor;
import org.Team1.technico.TechnicoMain;
import org.Team1.technico.exceptions.EmailException;
import org.Team1.technico.model.Owner;
import org.Team1.technico.model.Property;
import org.Team1.technico.repository.OwnerRepository;
import org.Team1.technico.utils.ResponseResult;
import org.Team1.technico.utils.ResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;

@AllArgsConstructor

@Service
public class OwnerServiceImpl implements OwnerService {
    private OwnerRepository ownerRepository;
    private static final Logger logger = LoggerFactory.getLogger(TechnicoMain.class);

    /**
     * The method checkNewOwnerValues ensures that the provided owner param compromises with the Owner model requirements
     *
     * @param owner
     * @return ResponseResult<Owner>
     */
    @Override
    public ResponseResult<Owner> createOwner(Owner owner) {
        try {
            return checkNewOwnerValues(owner);
        } catch (Exception e) {
            e.getMessage();
        }
        return new ResponseResult(null, ResponseStatus.OWNER_NOT_CREATED, "Owner failed to be created.");
    }

    @Override
    public ResponseResult<List<Owner>> readOwner() {
        try {
            List<Owner> owners = ownerRepository.findAll();
            if (owners != null && !owners.isEmpty())
                return new ResponseResult<>(owners, ResponseStatus.SUCCESS, "Oκ");
            else
                return new ResponseResult<>(null, ResponseStatus.SUCCESS, "No owners to show.");
        } catch (Exception e) {
            e.getMessage();
            return new ResponseResult<>(null, ResponseStatus.OWNER_NOT_FOUND, "Failed to find owners.");
        }
    }

    @Override
    public ResponseResult<Owner> readOwner(int ownerId) {
        try {
            Optional<Owner> owner = ownerRepository.findById(ownerId);
            if (owner != null && owner.isPresent())
                return new ResponseResult<>(owner.get(), ResponseStatus.SUCCESS, "Oκ");
            else
                return new ResponseResult<>(null, ResponseStatus.OWNER_NOT_FOUND, "There is not any owner with id= " + ownerId);
        } catch (Exception e) {
            return new ResponseResult<>(null, ResponseStatus.OWNER_NOT_FOUND, "Failed to find owner.");
        }
    }

    @Override
    public ResponseResult<List<Owner>> getOwnerByVatNumberOrEmail(String vatNumber, String email) {
        try {
            List<Owner> owners = ownerRepository.findOwnersByVatNumberOrEmail(vatNumber, email);
            if (!owners.isEmpty()) {
                return new ResponseResult<>(owners, ResponseStatus.SUCCESS, "Oκ");
            } else
                return new ResponseResult<>(null, ResponseStatus.SUCCESS, "No search results.");
        } catch (Exception e) {
            return new ResponseResult<>(null, ResponseStatus.OWNER_NOT_FOUND, "Failed to find owner.");
        }
    }

    @Override
    public ResponseResult<Map<String, Object>> getOwnerContainsSubVat(Pageable paging, String subVatNumber) {
        List<Owner> ownersWithSubVat = new ArrayList<>();
        Page<Owner> pageSubVats = null;
        if(subVatNumber == null) {
            pageSubVats = ownerRepository.findAll(paging);
        } else {
            pageSubVats = ownerRepository.findOwnersByVatNumberContaining(subVatNumber, paging);
        }
        ownersWithSubVat = pageSubVats.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("owners", ownersWithSubVat);
        response.put("currentPage", pageSubVats.getNumber());
        response.put("totalItems", pageSubVats.getTotalElements());
        response.put("totalPages", pageSubVats.getTotalPages());
        return new ResponseResult<>(response, ResponseStatus.SUCCESS, "Ok");
    }

    /**
     * The method checkUpdatedOwnerValues ensures that the param owner compromises with the Owner model requirements
     *
     * @param ownerId
     * @param owner
     * @return
     */
    @Override
    public ResponseResult<Owner> updateOwner(int ownerId, Owner owner) {
        try {
            Optional<Owner> ownerDb = ownerRepository.findById(ownerId);
            if (ownerDb.isEmpty())
                return new ResponseResult<>(null, ResponseStatus.OWNER_CANNOT_BE_UPDATED, "There is no owner with id: " + ownerId);
            return checkUpdatedOwnerValues(ownerId, owner);
        } catch (Exception e) {
            return new ResponseResult<>(null, ResponseStatus.OWNER_CANNOT_BE_UPDATED, "Update has failed");
        }
    }

    @Override
    public ResponseResult<Boolean> deleteOwner(int ownerId) {
        try {
            Optional<Owner> ownerDb = ownerRepository.findById(ownerId);
            if (ownerDb.isEmpty())
                return new ResponseResult<>(false, ResponseStatus.OWNER_NOT_FOUND, "There is any owner with id: " + ownerId);
            else if (!ownerDb.get().getProperties().isEmpty())
                return new ResponseResult<>(false, ResponseStatus.OWNER_CANNOT_BE_DELETED, "Firstly, delete the properties from the owner and then try again.");
            ownerRepository.delete(ownerDb.get());
            return new ResponseResult<>(true, ResponseStatus.SUCCESS, "Oκ");
        } catch (Exception e) {
            return new ResponseResult<>(null, ResponseStatus.OWNER_CANNOT_BE_DELETED, "Delete has failed.");
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

    /**
     * @param email
     * @return true or false as response to validation check
     */
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

    /**
     * The method ensures that the provided owner param compromises with the Owner model requirements
     *
     * @param owner
     * @return ResponseResult<Owner>
     */
    public ResponseResult<Owner> checkNewOwnerValues(Owner owner) {
        String message;
        //VAT number validation
        if (owner.getVatNumber() == null) {
            message = "VAT number is mandatory field.";
            return new ResponseResult<>(null, ResponseStatus.OWNER_NOT_CREATED, message);
        } else {
            if (owner.getVatNumber().length() != 9) {
                message = "You did not provide a 9 digit VAT number.";
                return new ResponseResult<>(null, ResponseStatus.OWNER_NOT_CREATED, message);
            }
            if (ownerRepository.findAll().stream().filter(ownerVar -> ownerVar.getVatNumber().equals(owner.getVatNumber())).count() == 1) {
                message = "The VAT number you provided is already used from another account.";
                return new ResponseResult<>(null, ResponseStatus.OWNER_NOT_CREATED, message);
            }
        }
        //First name validation
        if (owner.getFirstName() == null || owner.getFirstName().length() < 2) {
            message = "First name is mandatory field.";
            return new ResponseResult<>(null, ResponseStatus.OWNER_NOT_CREATED, message);
        }
        //Last name validation
        if (owner.getLastName() == null || owner.getLastName().length() < 2) {
            message = "Last name is mandatory field.";
            return new ResponseResult<>(null, ResponseStatus.OWNER_NOT_CREATED, message);
        }
        //Email validation
        if (owner.getEmail() != null) {
            if (!(validateEmail(owner.getEmail()))) {
                message = "You did not provide valid email address.";
                return new ResponseResult<>(null, ResponseStatus.OWNER_NOT_CREATED, message);
            }
            if (ownerRepository.findAll().stream().filter(ownerTemp -> ownerTemp.getEmail().equals(owner.getEmail())).count() == 1) {
                message = "The email you provided is already used from other account.";
                return new ResponseResult<>(null, ResponseStatus.OWNER_NOT_CREATED, message);
            }
        }
        //Username number validation
        if (owner.getUsername() == null || owner.getUsername().length() < 2) {
            message = "Username is mandatory field.";
            return new ResponseResult<>(null, ResponseStatus.OWNER_NOT_CREATED, message);
        } else {
            if (ownerRepository.findAll().stream().filter(ownerTemp -> ownerTemp.getUsername().equals(owner.getUsername())).count() == 1) {
                message = "The username you provided is used from other account.";
                return new ResponseResult<>(null, ResponseStatus.OWNER_NOT_CREATED, message);
            }
        }
        //Password validation
        if (owner.getPassword() == null || owner.getPassword().length() < 2) {
            message = "Password is mandatory field.";
            return new ResponseResult<>(null, ResponseStatus.OWNER_NOT_CREATED, message);
        }
        return new ResponseResult<>(ownerRepository.save(owner), ResponseStatus.SUCCESS, "Oκ");
    }

    /**
     * The method ensures that the param owner compromises with the Owner model requirements
     *
     * @param ownerId
     * @param owner
     * @return ResponseResult<Owner>
     */
    public ResponseResult<Owner> checkUpdatedOwnerValues(int ownerId, Owner owner) {
        String message;
        //VAT number validation
        if (owner.getVatNumber() != null) {
            if (owner.getVatNumber().length() != 9) {
                message = "You did not provide a 9 digit VAT number.";
                return new ResponseResult<>(null, ResponseStatus.OWNER_CANNOT_BE_UPDATED, message);
            }
            if (!ownerRepository.findById(ownerId).get().getVatNumber().equals(owner.getVatNumber())) {
                if (ownerRepository.findAll().stream().filter(ownerTemp -> ownerTemp.getVatNumber().equals(owner.getVatNumber())).count() == 1) {
                    message = "The VAT number you provided is already used from another account.";
                    return new ResponseResult<>(null, ResponseStatus.OWNER_CANNOT_BE_UPDATED, message);
                }
            }
        }
        if (owner.getFirstName() != null && owner.getFirstName().length() < 2) {
            message = "Provide valid value for first name.";
            return new ResponseResult<>(null, ResponseStatus.OWNER_CANNOT_BE_UPDATED, message);
        }
        if (owner.getLastName() != null && owner.getLastName().length() < 2) {
            message = "Provide valid value for last name.";
            return new ResponseResult<>(null, ResponseStatus.OWNER_CANNOT_BE_UPDATED, message);
        }
        //Email validation
        if (owner.getEmail() != null) {
            if (!(validateEmail(owner.getEmail()))) {
                message = "You did not provide valid email address.";
                return new ResponseResult<>(null, ResponseStatus.OWNER_CANNOT_BE_UPDATED, message);
            }
            if (!ownerRepository.getById(ownerId).getEmail().equals(owner.getEmail())) {
                if (ownerRepository.findAll().stream().filter(ownerTemp -> ownerTemp.getEmail().equals(owner.getEmail())).count() == 1) {
                    message = "The email you provided is already used from another account.";
                    return new ResponseResult<>(null, ResponseStatus.OWNER_CANNOT_BE_UPDATED, message);
                }
            }
        }
        //Username number validation
        if (owner.getUsername() != null && owner.getUsername().length() < 2) {
            message = "You did not provide valid username.";
            return new ResponseResult<>(null, ResponseStatus.OWNER_CANNOT_BE_UPDATED, message);
        } else {
            if (!ownerRepository.getById(ownerId).getUsername().equals(owner.getUsername())) {
                if (ownerRepository.findAll().stream().filter(ownerTemp -> ownerTemp.getUsername().equals(owner.getUsername())).count() == 1) {
                    message = "The username you provided is used from other account.";
                    return new ResponseResult<>(null, ResponseStatus.OWNER_CANNOT_BE_UPDATED, message);
                }
            }
        }
        if (owner.getPassword() != null && owner.getPassword().length() < 2) {
            message = "You did not provide valid password.";
            return new ResponseResult<>(null, ResponseStatus.OWNER_CANNOT_BE_UPDATED, message);
        }
        Optional<Owner> ownerDb = ownerRepository.findById(ownerId);
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
    }
}
