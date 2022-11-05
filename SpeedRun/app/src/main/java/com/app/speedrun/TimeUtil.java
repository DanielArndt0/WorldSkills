package com.app.speedrun;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class TimeUtil {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Period between(String startDate, String endDate, String pattern) {
        return Period.between(
                LocalDate.parse(startDate, DateTimeFormatter.ofPattern(pattern)),
                LocalDate.parse(endDate, DateTimeFormatter.ofPattern(pattern))
        );
    }
}
