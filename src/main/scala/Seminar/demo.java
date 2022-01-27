package Seminar;

import java.io.DataOutput;
import java.io.IOException;
import java.time.*;
import java.time.temporal.ChronoField;
import java.time.zone.ZoneRules;

public class demo {
    public static void main(String[] args) {
        LocalDate d=LocalDate.of(2022,1,24);
        LocalTime t=LocalTime.of(11,12,34);

        ZonedDateTime zdt=ZonedDateTime.now(ZoneId.of("US/Alaska"));
        System.out.println(zdt);

        System.out.println(zdt.withZoneSameInstant(ZoneId.of("Asia/Calcutta")));
        System.out.println(LocalDateTime.of(LocalDate.now(),LocalTime.now()).atZone(ZoneId.of("US/Alaska")));
        System.out.println(ZonedDateTime.now());
        LocalDateTime grtnm=LocalDateTime.now();

//        ZonedDateTime z=ZonedDateTime.o(LocalDateTime.now()).atZone(ZoneId.of("US/Alaska"));
    }
}
