package com.give928.java.java8.datetime;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class App {
    public static void main(String[] args) {
        // 기존 라이브러리
//        testOldDateTime();

        // 기계 시간
        Instant instant = Instant.now();
        System.out.println(instant); // 기준시 UTC, GMT
        System.out.println(instant.atZone(ZoneId.of("UTC"))); // 기준시 UTC, GMT

        ZoneId zone = ZoneId.systemDefault();
        System.out.println("zone = " + zone);
        ZonedDateTime zonedDateTime = instant.atZone(zone);
        System.out.println("zonedDateTime = " + zonedDateTime);

        // 인류용 일시 표현
        System.out.println();

        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("localDateTime = " + localDateTime);

        LocalDateTime birthday = LocalDateTime.of(1984, Month.SEPTEMBER, 28, 0, 0, 0);
        System.out.println("birthday = " + birthday);

        ZonedDateTime nowInKorean = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        System.out.println("nowInKorean = " + nowInKorean);

        // 기간 표현
        System.out.println();

        LocalDate today = LocalDate.now();
        LocalDate thisYearBirthday = LocalDate.of(2022, Month.SEPTEMBER, 28);
        System.out.println("today = " + today);
        System.out.println("thisYearBirthday = " + thisYearBirthday);

        Period period = Period.between(today, thisYearBirthday);
        System.out.println("period.getMonths() = " + period.getMonths());
        System.out.println("period.getDays() = " + period.getDays());

        Period until = today.until(thisYearBirthday);
        System.out.println("until.getMonths() = " + until.getMonths());
        System.out.println("until.getDays() = " + until.getDays());
        System.out.println("until.get(ChronoUnit.MONTHS) = " + until.get(ChronoUnit.MONTHS));
        System.out.println("until.get(ChronoUnit.DAYS) = " + until.get(ChronoUnit.DAYS));

        Instant now = Instant.now();
        Instant plus10Seconds = now.plusSeconds(10);

        Duration duration = Duration.between(now, plus10Seconds);
        System.out.println("duration.getSeconds() = " + duration.getSeconds());

        // 파싱 또는 포매팅
        System.out.println();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        System.out.println("localDateTime.format(dateTimeFormatter) = " + localDateTime.format(dateTimeFormatter));

        LocalDate localDate = LocalDate.parse("2022.09.28", dateTimeFormatter);
        System.out.println("localDate = " + localDate);

        // 레거시 API 지원
        System.out.println();

        Date date = new Date();
        Instant instantFormDate = date.toInstant();
        Date dateFormInstant = Date.from(instant);

        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        ZonedDateTime zonedDateTime1 = gregorianCalendar.toInstant().atZone(ZoneId.systemDefault());
        LocalDateTime localDateTime1 = zonedDateTime1.toLocalDateTime();
        GregorianCalendar gregorianCalendar1 = GregorianCalendar.from(zonedDateTime1);

        ZoneId zoneId = TimeZone.getTimeZone("PST").toZoneId();
        TimeZone timeZone = TimeZone.getTimeZone(zoneId);
    }

    private static void testOldDateTime() throws InterruptedException {
        Date date = new Date();
        long time = date.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        System.out.println("date = " + date);
        System.out.println("time = " + time);

        Thread.sleep(1000 * 3);

        Date after3Seconds = new Date();
        System.out.println("after3Seconds = " + after3Seconds);
        after3Seconds.setTime(time); // mutable
        System.out.println("after3Seconds = " + after3Seconds);

        Calendar calendar1 = new GregorianCalendar(1984, 9, 28);
        System.out.println("calendar1 = " + simpleDateFormat.format(calendar1.getTime()));
        Calendar calendar2 = new GregorianCalendar(1984, Calendar.SEPTEMBER, 28);
        System.out.println("calendar2 = " + simpleDateFormat.format(calendar2.getTime()));
    }
}
