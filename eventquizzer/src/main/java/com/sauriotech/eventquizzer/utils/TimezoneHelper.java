package com.sauriotech.eventquizzer.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author SAURIOTech Solutions.
 *
 */
public final class TimezoneHelper {
	private static final DateFormat DF = new SimpleDateFormat("dd/MM/yyyy");
	
	public static Date getDateFromTimezone(String timeZone) {
		TimeZone t2 = TimeZone.getTimeZone(timeZone);
		return Calendar.getInstance(t2).getTime();
	}
	
	public static String getStringDateFromTimezone(String timeZone) {
		Date d = getDateFromTimezone(timeZone);
		return DF.format(d);
	}
}
