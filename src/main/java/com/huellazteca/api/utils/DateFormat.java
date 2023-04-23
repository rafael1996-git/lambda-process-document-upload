package com.huellazteca.api.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateFormat {

    public static String getDateFormat(String date) throws ParseException {
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        myFormat.setTimeZone(TimeZone.getTimeZone("America/Mexico_City"));
        Date reformattedStr = myFormat.parse(date);
        myFormat = new SimpleDateFormat("dd/MM/yyyy");
        return myFormat.format(reformattedStr);
    }
}
