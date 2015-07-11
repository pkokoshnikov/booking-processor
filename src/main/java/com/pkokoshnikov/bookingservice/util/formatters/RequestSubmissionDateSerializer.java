package com.pkokoshnikov.bookingservice.util.formatters;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: pako1113
 * Date: 07.07.15
 */
public class RequestSubmissionDateSerializer extends JsonSerializer<Date> {
    private static SimpleDateFormat formatter =
            new SimpleDateFormat(Constants.REQUEST_SUBMISSION_FORMAT);

    @Override
    public void serialize(Date value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(formatter.format(value));
    }
}
