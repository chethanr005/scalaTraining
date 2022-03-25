package com.chethan.assignment6.employee;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by Chethan on Mar 17, 2022.
 */

public class LocalDateSerializer extends JsonSerializer<LocalDate> {


    @Override
    public void serialize(LocalDate localDate, JsonGenerator generator, SerializerProvider serializers) throws IOException {
        generator.writeString(localDate.format(DateTimeFormatter.ISO_LOCAL_DATE));

    }
}
