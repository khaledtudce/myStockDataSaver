package app.helperPackage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateConverter {

    public static Date generateEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.HOUR_OF_DAY, 23);

        return calendar.getTime();
    }
    
	public static Date generateDateWithFormat(String date) throws ParseException {
		return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse(date);
	}
	
	public static Date convertDateFromEpoch(String date) {
		long epoch = Long.parseLong(date);
		return new Date( epoch * 1000 );
	}
}
