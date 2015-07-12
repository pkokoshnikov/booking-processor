package com.pkokoshnikov.bookingservice.process;

import com.pkokoshnikov.bookingservice.process.types.ProcessorType;

/**
 * Created by pavel on 12.07.2015.
 */
public interface BookingProcessorFactory {
    BookingProcessor create(ProcessorType processorType);
}
