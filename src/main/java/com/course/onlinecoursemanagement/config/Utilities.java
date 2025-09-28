package com.course.onlinecoursemanagement.config;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utilities {
    public static boolean hasValue(String str) {
        return str != null && !str.isBlank();
    }

    public static String formatDate(Date date) {
        return new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a", Locale.ENGLISH)
                .format(date);
    }
}
