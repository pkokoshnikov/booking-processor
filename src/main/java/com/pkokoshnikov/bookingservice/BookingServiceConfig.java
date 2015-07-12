package com.pkokoshnikov.bookingservice;

import com.pkokoshnikov.bookingservice.injection.ApplicationBinder;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * User: pako1113
 * Date: 08.07.15
 */
public class BookingServiceConfig extends ResourceConfig {
    public BookingServiceConfig() {
        register(new ApplicationBinder());
    }
}
