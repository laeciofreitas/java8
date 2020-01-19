package com.learning.chapter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

public class Chapter10 {

    public static void main(String[] args) {
        LocalDate localDate = LocalDate.now().plusMonths(1);
        System.out.println(localDate);

        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);

        LocalTime localTime = LocalTime.now();
        System.out.println(localTime);

        LocalDateTime localDateTime1 = LocalDate.now().atTime(localTime);

        LocalDateTime localDateTime2 = localDate.atTime(localTime);
        localDateTime2.atZone(ZoneId.of("America/Sao_Paulo"));
    }
}
