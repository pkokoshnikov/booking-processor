package com.pkokoshnikov.bookingservice.process;

import com.pkokoshnikov.bookingservice.process.types.ProcessorType;
/**
 * Created by pavel on 12.07.2015.
 */
public class BookingProcessorFactoryImpl implements BookingProcessorFactory{
    @Override
    public BookingProcessor create(ProcessorType processorType) {
        switch (processorType) {
            case SIMPLE_BOOKING_PROCESSOR:
                return new BookingProcessorImpl();

            default:
                throw new IllegalArgumentException("It's not declared booking processor type = " + processorType.name());
        }
    }
}
