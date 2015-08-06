package com.pkokoshnikov.bookingservice.dao;

import com.pkokoshnikov.bookingservice.model.Property;

import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * User: pako1113
 * Date: 03.08.15
 */
public class PropertiesDAOImpl implements PropertiesDAO{
    private final EntityManager entityManager;

    @Inject
    public PropertiesDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Property findPropertyByName(String name) {
        Property property = entityManager.find(Property.class, name);
        return property;
    }
}
