package Java;

import sun.security.timestamp.TSRequest;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

public class DateAndTime {
    public static void main(String[] args) {
        //System Date.
        LocalDate curDate=LocalDate.now();
//        System.out.println(curDate);
//        //System Time.
//        LocalTime curTime=LocalTime.now();
//        System.out.println(curTime);
//        //System Date and Time
//        LocalDateTime curDateTime=LocalDateTime.now();
//        System.out.println(curDateTime);

        System.out.println("DAY_OF_MONTH "+curDate.get(ChronoField.DAY_OF_MONTH));
        System.out.println("DAY_OF_WEEK "+curDate.get(ChronoField.DAY_OF_WEEK));
        System.out.println("ALIGNED_DAY_OF_WEEK_IN_MONTH "+curDate.get(ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH));
//        System.out.println(curDate.get(ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR));
//        System.out.println(curDate.get(ChronoField.ALIGNED_WEEK_OF_MONTH));
//        System.out.println(curDate.get(ChronoField.ALIGNED_WEEK_OF_YEAR));
//        System.out.println(curDate.get(ChronoField.AMPM_OF_DAY));
//        System.out.println(curDate.get(ChronoField.CLOCK_HOUR_OF_AMPM));
//        System.out.println(curDate.get(ChronoField.CLOCK_HOUR_OF_DAY));
//        System.out.println(curDate.get(ChronoField.DAY_OF_YEAR));
        System.out.println(curDate.getLong(ChronoField.EPOCH_DAY));
//        System.out.println(curDate.get(ChronoField.ERA));
//        System.out.println(curDate.get(ChronoField.HOUR_OF_AMPM));
//        System.out.println(curDate.get(ChronoField.HOUR_OF_DAY));
//        System.out.println(curDate.get(ChronoField.INSTANT_SECONDS));
        LocalDate da = LocalDate.now();
        System.out.println(curDate.plusYears(1));
        System.out.println(da.with(TemporalAdjusters.firstInMonth(DayOfWeek.TUESDAY)));
        System.out.println(LocalDate.of(2001,2,28));

        LocalTime curTime=LocalTime.now();
        System.out.println("AMPM of day : "+curTime.get(ChronoField.AMPM_OF_DAY));
        System.out.println("atTime : "+curDate.atTime(11,34));
        System.out.println("atTime : "+curDate.atTime(curTime));

        //atDate
        System.out.println("atDate : "+curTime.atDate(curDate));

        ZonedDateTime zone=ZonedDateTime.now();
        System.out.println("Zone : "+zone);
        System.out.println("Zone ID : "+zone.getZone());
        System.out.println("Zone Offset : "+zone.getOffset());
        ZoneId.getAvailableZoneIds()
                .stream().sorted().forEach(System.out::println);

        System.out.println(ZonedDateTime.now(ZoneId.of("US/Alaska")));
        System.out.println(ZonedDateTime.now(Clock.system(ZoneId.of("US/Alaska"))));
        System.out.println(LocalDateTime.now(ZoneId.of("US/Alaska")));
        System.out.println(LocalDateTime.now());
        System.out.println(LocalDateTime.ofInstant(Instant.now(),ZoneId.systemDefault()));
        System.out.println(LocalDateTime.now().atZone(ZoneId.of("US/Alaska")));
        System.out.println(Instant.now().atZone(ZoneId.of("US/Alaska")));
        System.out.println(OffsetDateTime.now());


        System.out.println(" Converting LocalDate to String and vice-versa");
        String date="2022-01-19";
        LocalDate ld=LocalDate.parse(date);
        LocalDate ld1=LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        System.out.println(ld);
        System.out.println(ld1);
        String date1="17/01/2022";
        DateTimeFormatter dtf=DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate ld3=LocalDate.parse(date1,dtf);
        System.out.println(ld3);
        LocalDate ld2=LocalDate.now();
        String date3=ld2.format(dtf);
        System.out.println(date3);

        System.out.println(" Converting LocalTime to String and vice-versa");
        String time="12:24";
        LocalTime lt=LocalTime.parse(time);
        DateTimeFormatter tf= DateTimeFormatter.ofPattern("hh-mm");
        LocalTime lt1=LocalTime.now();
        String time1=lt1.format(tf);
        System.out.println(time1);
        System.out.println(lt);

    }
}
