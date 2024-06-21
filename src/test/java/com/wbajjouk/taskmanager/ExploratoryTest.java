package com.wbajjouk.taskmanager;

import org.junit.jupiter.api.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class ExploratoryTest {

    @Test
    public void exploreLocalDateTime() {

        Instant now = Instant.now();
        Instant hundredSecondsFromNow = now.plus(Duration.ofSeconds(100));

        var t = LocalDate.of(2024, 6, 21).atTime(LocalTime.NOON);
        var tPlusOneYear = t.plus(Period.ofYears(1));
        System.out.println(tPlusOneYear);

        DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE_TIME;
        System.out.println(dtf.format(now.atZone(ZoneId.of("Europe/Rome"))));
        System.out.println(dtf.format(now.atZone(ZoneId.of("Europe/London"))));
        System.out.println(dtf.format(now.atOffset(ZoneOffset.UTC)));
        System.out.println(dtf.format(tPlusOneYear));

    }
}
