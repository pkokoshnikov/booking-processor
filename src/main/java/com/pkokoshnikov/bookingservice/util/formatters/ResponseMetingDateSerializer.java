package com.pkokoshnikov.bookingservice.util.formatters;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.pkokoshnikov.bookingservice.model.BookingItem;
import com.pkokoshnikov.bookingservice.model.GroupByDayBookingItem;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by pavel on 10.07.2015.
 */
public class ResponseMetingDateSerializer extends JsonSerializer<Date> {
    private static SimpleDateFormat formatter =
            new SimpleDateFormat(Constants.RESPONSE_BOOKING_DATE_FORMAT);

    @Override
    public void serialize(Date value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(formatter.format(value));
    }
}
