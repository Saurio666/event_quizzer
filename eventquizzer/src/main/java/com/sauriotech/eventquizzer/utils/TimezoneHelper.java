package com.sauriotech.eventquizzer.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * @author SAURIOTech Solutions.
 *
 */
public final class TimezoneHelper {
	private static final DateFormat DF = new SimpleDateFormat("dd/MM/yyyy");
	private static final DateFormat DF_TIME = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	public static Date getDateFromTimezone(String timeZone) {
		TimeZone t2 = TimeZone.getTimeZone(timeZone);
		return Calendar.getInstance(t2).getTime();
	}
	
	public static String getStringDateFromTimezone(String timeZone) {
		Date d = getDateFromTimezone(timeZone);
		return DF.format(d);
	}

	public static String getStringDateFullFromTimezone(String timeZone) {
		Date d = getDateFromTimezone(timeZone);
		return convertStringDateFull(d);
	}
	
	public static String convertStringDateFull(Date date) {
		return DF_TIME.format(date);
	}
	
	public static long getDifferenceInMinutes(Date start, Date end) {
		long duration = end.getTime() - start.getTime();
		
		return TimeUnit.MILLISECONDS.toMinutes(duration);
	}
}
