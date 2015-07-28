package com.pkokoshnikov.bookingservice.injection.factory;

import com.pkokoshnikov.bookingservice.process.BookingProcessor;
import com.pkokoshnikov.bookingservice.process.BookingProcessorImpl;
import org.glassfish.hk2.api.Factory;

/**
 * User: pako1113
 * Date: 24.07.15
 */
public class BookingProcessorFactory implements Factory<BookingProcessor> {
    @Override
    public BookingProcessor provide() {
        return new BookingProcessorImpl();
    }

    @Override
    public void dispose(BookingProcessor bookingProcessor) {

    }
}
