package com.carehires.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class RecentWeekendUtils {

    public static LocalDate getMostRecentWeekend() {
        LocalDate today = LocalDate.now();

        // if today is Sunday, return yesterday (Saturday)
        if (today.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return today.minusDays(1);
        }
        // if today is Saturday, return today
        else if (today.getDayOfWeek() == DayOfWeek.SATURDAY) {
            return today;
        }

        // otherwise, find the last Saturday
        else {
            return today.with(DayOfWeek.SATURDAY).minusWeeks(1);
        }
    }
}
