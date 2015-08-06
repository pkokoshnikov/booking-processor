package com.pkokoshnikov.bookingservice.injection.factory;

import com.pkokoshnikov.bookingservice.process.BookingProcessor;
import com.pkokoshnikov.bookingservice.process.BookingProcessorImpl;
import org.glassfish.hk2.api.Factory;

import javax.inject.Inject;

/**
 * User: pako1113
 * Date: 24.07.15
 */
public class BookingProcessorFactory implements Factory<BookingProcessor> {

    private BookingProcessorImpl bookingProcessor;

    @Inject
    public BookingProcessorFactory(BookingProcessorImpl bookingProcessor) {
        this.bookingProcessor = bookingProcessor;
    }

    @Override
    public BookingProcessor provide() {
        return bookingProcessor;
    }

    @Override
    public void dispose(BookingProcessor bookingProcessor) {

    }
}
