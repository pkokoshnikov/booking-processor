package com.pkokoshnikov.bookingservice.injection;

import com.pkokoshnikov.bookingservice.process.BookingProcessor;
import com.pkokoshnikov.bookingservice.process.BookingProcessorImpl;
import org.glassfish.hk2.api.DynamicConfiguration;
import org.glassfish.hk2.utilities.Binder;
import org.glassfish.hk2.utilities.BuilderHelper;

/**
 * User: pako1113
 * Date: 08.07.15
 */
public class ApplicationBinder implements Binder {
    @Override
    public void bind(DynamicConfiguration dynamicConfiguration) {
        dynamicConfiguration.bind(BuilderHelper.link(BookingProcessorImpl.class).to(BookingProcessor.class).build());
    }
}
