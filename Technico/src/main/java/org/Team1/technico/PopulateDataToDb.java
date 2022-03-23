package org.Team1.technico;

import lombok.AllArgsConstructor;
import org.Team1.technico.model.*;
import org.Team1.technico.repository.OwnerRepository;
import org.Team1.technico.repository.PropertyRepository;
import org.Team1.technico.repository.RepairRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Configuration
public class PopulateDataToDb {
    private static final Logger log = LoggerFactory.getLogger(PopulateDataToDb.class);
    private OwnerRepository ownerRepository;
    private PropertyRepository propertyRepository;
    private RepairRepository repairRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void restoreDatabase() {
        log.info("Clearing repositories.");
        ownerRepository.deleteAll();
        repairRepository.deleteAll();
        propertyRepository.deleteAll();
        ownerRepository.saveAll(generateOwners());
        log.info("Owners saved!");
        propertyRepository.saveAll(generateProperties());
        propertyRepository.saveAll(matchPropertyOwners(propertyRepository.findAll(), ownerRepository.findAll()));
        log.info("Properties saved!");
        repairRepository.saveAll(generateRepairs());
        repairRepository.saveAll(matchProperyRepairs(propertyRepository.findAll(), repairRepository.findAll()));
        log.info("Repairs saved!");
        log.info("Database setup completed!");
    }

    private static List<Owner> generateOwners() {
        List<Owner> owners = new ArrayList<>();
        Owner owner1 = new Owner("150250350", "John", "Homes", "50 7701 Avonlan Way 50, Lloydminster, Canada", "+15877890714", "john@mail.com", "johnHomes", "secret1!@");
        Owner owner2 = new Owner("150250351", "Thomas", "Burton", "50 7701 Clifsay Road 11  Lloydminster, Canada", "+15877898000", "thomas@mail.com", "thomasBurton", "secret2##");
        Owner owner3 = new Owner("150250352", "Anna", "Harris", "50 7701 Barnfil Road 20  Lloydminster, Canada", "+15877899000", "anna@mail.com", "annaHarris", "secret3!!");
        owners.add(owner1);
        owners.add(owner2);
        owners.add(owner3);
        return owners;
    }

    private static List<Property> generateProperties() {
        List<Property> properties = new ArrayList<>();
        Property property1 = new Property("123567890", "50 7701 Boucherleche Pathway 50, Lloydminster, Canada", 1990, PropertyType.FLAT);
        Property property2 = new Property("123567891", "50 7701 Brown Bear Street 5, Lloydminster, Canada", 2005, PropertyType.DETACHED);
        Property property3 = new Property("123567892", "50 7701 Abbot's Road 32, Lloydminster, Canada", 2020, PropertyType.SEMIDETACHED);
        properties.add(property1);
        properties.add(property2);
        properties.add(property3);
        return properties;
    }

    private static List<Repair> generateRepairs() {
        List<Repair> repairs = new ArrayList<>();
        LocalDate datetime1 = LocalDate.now().minusYears(1L);
        LocalDate datetime2 = LocalDate.now().minusMonths(4L);
        LocalDate datetime3 = LocalDate.now().minusMonths(2L);
        LocalDate datetime4 = LocalDate.now().minusMonths(10L);
        LocalDate datetime5 = LocalDate.now().minusDays(45L);
        Repair repair1 = new Repair(datetime1, LocalDate.of(2023, 1, 13), RepairStatus.IN_PROGRESS, RepairType.PAINTING, BigDecimal.valueOf(770L), "A paint description.");
        Repair repair2 = new Repair(datetime2, LocalDate.of(2024, 2, 19), RepairStatus.PENDING, RepairType.INSULATION, BigDecimal.valueOf(15000L), "An insulation description.");
        Repair repair3 = new Repair(datetime3, LocalDate.of(2022, 05, 8), RepairStatus.IN_PROGRESS, RepairType.ELECTRICAL, BigDecimal.valueOf(5000L), "An electrical description.");
        Repair repair4 = new Repair(datetime4, LocalDate.of(2023, 2, 19), RepairStatus.PENDING, RepairType.INSULATION, BigDecimal.valueOf(19000L), "An insulation description.");
        Repair repair5 = new Repair(datetime5, LocalDate.of(2022, 05, 28), RepairStatus.IN_PROGRESS, RepairType.ELECTRICAL, BigDecimal.valueOf(5000L), "An electrical description.");
        repairs.add(repair1);
        repairs.add(repair2);
        repairs.add(repair3);
        repairs.add(repair4);
        repairs.add(repair5);
        return repairs;
    }

    private static List<Property> matchPropertyOwners(List<Property> properties, List<Owner> owners) {
        for (Property property : properties) {
            Owner ownerRandom = owners.get((int) (Math.random() * owners.size()));
            property.setOwner(ownerRandom);
            property.setOwnerVatNumber(ownerRandom.getVatNumber());
        }
        log.info("Properties and owners were matched!");
        return properties;
    }

    private static List<Repair> matchProperyRepairs(List<Property> properties, List<Repair> repairs) {
        for (Repair repair : repairs) {
            repair.setProperty(properties.get((int) (Math.random() * properties.size())));
        }
        log.info("Properties and repairs were matched!");
        return repairs;
    }

}
