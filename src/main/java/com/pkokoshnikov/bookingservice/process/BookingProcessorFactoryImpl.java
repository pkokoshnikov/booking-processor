package com.pkokoshnikov.bookingservice.process;

/**
 * User: pako1113
 * Date: 12.07.15
 * Implementation of factory
 */
public class BookingProcessorFactoryImpl implements BookingProcessorFactory{
    @Override
    public BookingProcessor create(ProcessorType processorType) {
        switch (processorType) {
            case BOOKING_PROCESSOR:
                return new BookingProcessorImpl();

            default:
                throw new IllegalArgumentException("It's not declared booking processor type = " + processorType.name());
        }
    }
}
