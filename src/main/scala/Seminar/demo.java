package Seminar;

import java.time.*;

public class demo {
    public static void main(String[] args) {
        LocalDate d=LocalDate.of(2022,1,24);
        LocalTime t=LocalTime.of(11,12,34);

        ZonedDateTime zdt=ZonedDateTime.now(ZoneId.of("US/Alaska"));
        System.out.println(zdt);

        System.out.println(zdt.withZoneSameInstant(ZoneId.of("Asia/Calcutta")));
        System.out.println(LocalDateTime.of(LocalDate.now(),LocalTime.now()).atZone(ZoneId.of("US/Alaska")));
//        ZonedDateTime z=ZonedDateTime.o(LocalDateTime.now()).atZone(ZoneId.of("US/Alaska"));

    }
}
