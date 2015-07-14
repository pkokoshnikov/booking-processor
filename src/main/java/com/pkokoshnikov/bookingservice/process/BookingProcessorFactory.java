package com.pkokoshnikov.bookingservice.process;

/**
 * User: pako1113
 * Date: 12.07.15
 * This interface provides method for creating of processor
 */
public interface BookingProcessorFactory {
    BookingProcessor create(ProcessorType processorType);
}
