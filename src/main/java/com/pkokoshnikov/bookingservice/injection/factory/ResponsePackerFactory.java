package com.pkokoshnikov.bookingservice.injection.factory;


import com.pkokoshnikov.bookingservice.process.response.GroupByDayPacker;
import com.pkokoshnikov.bookingservice.process.response.ResponsePacker;
import org.glassfish.hk2.api.Factory;

/**
 * User: pako1113
 * Date: 27.07.15
 */
public class ResponsePackerFactory implements Factory<ResponsePacker> {

    @Override
    public ResponsePacker provide() {
        return new GroupByDayPacker();
    }

    @Override
    public void dispose(ResponsePacker responsePacker) {

    }
}
