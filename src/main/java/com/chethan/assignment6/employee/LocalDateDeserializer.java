package com.chethan.assignment6.employee;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by Chethan on Mar 17, 2022.
 */

public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {


    @Override
    public LocalDate deserialize(JsonParser jsonDate, DeserializationContext txt) throws IOException {
        return LocalDate.parse(jsonDate.getText(), DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
