package org.Team1.technico.service;

import org.Team1.technico.model.Property;

import java.util.List;

public interface PropertyService {
    Property createProperty(Property property);

    List<Property> readProperty();

    Property readProperty(int propertyId);

    Property updateProperty(int propertyId, Property property);

    boolean deleteProperty(int propertyId);

    //boolean addProperty(int ownerId, Property property);


}

