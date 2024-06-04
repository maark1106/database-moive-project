package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CurrentDate {
    private static final String DATE_STRING = "2024-06-11";
    public static final Date date;

    static {
        Date tempDate = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            tempDate = sdf.parse(DATE_STRING);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        date = tempDate;
    }
}
