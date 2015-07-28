package com.pkokoshnikov.bookingservice.injection;

import com.pkokoshnikov.bookingservice.dao.BookingItemDAO;
import com.pkokoshnikov.bookingservice.injection.factory.BookingProcessorFactory;
import com.pkokoshnikov.bookingservice.injection.factory.PersistenceFactory;
import com.pkokoshnikov.bookingservice.injection.factory.ResponsePackerFactory;
import com.pkokoshnikov.bookingservice.process.BookingProcessor;
import com.pkokoshnikov.bookingservice.process.ResponsePacker;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.persistence.EntityManager;

/**
 * User: pako1113
 * Date: 24.07.15
 */
public class ApplicationBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(BookingItemDAO.class).to(BookingItemDAO.class);
        bindFactory(PersistenceFactory.class).to(EntityManager.class);
        bindFactory(BookingProcessorFactory.class).to(BookingProcessor.class);
        bindFactory(ResponsePackerFactory.class).to(ResponsePacker.class);
    }
}
