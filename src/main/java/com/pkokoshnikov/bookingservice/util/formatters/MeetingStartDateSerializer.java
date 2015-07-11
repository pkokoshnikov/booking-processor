package com.pkokoshnikov.bookingservice.util.formatters;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by pavel on 10.07.2015.
 */
public class MeetingStartDateSerializer extends JsonSerializer<Date> {
    private static SimpleDateFormat formatter =
            new SimpleDateFormat(Constants.MEETING_START_FORMAT);

    @Override
    public void serialize(Date value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(formatter.format(value));
    }
}
