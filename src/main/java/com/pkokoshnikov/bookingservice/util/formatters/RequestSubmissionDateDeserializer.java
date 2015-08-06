package com.pkokoshnikov.bookingservice.util.formatters;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: pako1113
 * Date: 07.07.15
 */
public class RequestSubmissionDateDeserializer extends JsonDeserializer<Date> {

    private static SimpleDateFormat formatter =
            new SimpleDateFormat(Constants.REQUEST_SUBMISSION_FORMAT);

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String date = jsonParser.getText();
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}