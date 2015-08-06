package com.pkokoshnikov.bookingservice.injection.factory;

import org.glassfish.hk2.api.Factory;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 * User: pako1113
 * Date: 24.07.15
 */
public class PersistenceFactory implements Factory<EntityManager>{

    private EntityManager entityManager = Persistence.createEntityManagerFactory("com.pkokoshnikov.bookingservice").createEntityManager();
    @Override
    public EntityManager provide() {
        return entityManager;
    }

    @Override
    public void dispose(EntityManager entityManagerFactory) {
        entityManager.close();
    }
}
