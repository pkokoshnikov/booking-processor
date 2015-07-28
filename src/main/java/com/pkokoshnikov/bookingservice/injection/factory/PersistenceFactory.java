package com.pkokoshnikov.bookingservice.injection.factory;

import org.glassfish.hk2.api.Factory;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 * User: pako1113
 * Date: 24.07.15
 */
public class PersistenceFactory implements Factory<EntityManager>{

    @Override
    public EntityManager provide() {
        return Persistence.createEntityManagerFactory("com.pkokoshnikov.bookingservice").createEntityManager();
    }

    @Override
    public void dispose(EntityManager entityManagerFactory) {

    }
}
