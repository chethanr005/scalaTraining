package training.Java.dateTime;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.Optional;

public class localDateTime {
    public static void main(String[] args) {
        LocalDate localDate = LocalDate.ofYearDay(2020,366);
        System.out.println(localDate.getDayOfWeek());
        System.out.println(localDate.getDayOfMonth() );
        System.out.println(localDate.get(ChronoField.DAY_OF_MONTH));
    }
}
