package com.sauriotech.eventquizzer.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * @author SAURIOTech Solutions.
 *
 */
public final class TimezoneHelper {
	private static String FULL_DATETIME_PATTERN = "dd/MM/yyyy HH:mm:ss";
	private static String DATE_PATTERN = "dd/MM/yyyy";
	
	private static final DateFormat DF = new SimpleDateFormat(DATE_PATTERN);
	private static final DateFormat DF_TIME = new SimpleDateFormat(FULL_DATETIME_PATTERN);
	private static final DateTimeFormatter DTF_FULL = DateTimeFormatter.ofPattern(FULL_DATETIME_PATTERN);	
	private static SimpleDateFormat formatter = new SimpleDateFormat(FULL_DATETIME_PATTERN);
	
	public static Date getDateFromTimezone(String timeZone) {
		DummyLogger.sysout(TimezoneHelper.class.getName(), "getDateFromTimezone", "timeZone --> " + timeZone);
		ZoneId idZona = ZoneId.of(timeZone);	
		Date ts = Calendar.getInstance().getTime();
		Instant instant = Instant.ofEpochMilli(ts.getTime());
		LocalDateTime res = LocalDateTime.ofInstant(instant, idZona);		
		Date returnValue = new Date();
		try {
			returnValue = formatter.parse(res.format(DTF_FULL));
			DummyLogger.sysout(TimezoneHelper.class.getName(), "getDateFromTimezone","returnValue --> " + returnValue);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnValue = new Date();
		}
		
		return returnValue;
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

	public static String convertStringDateShort(Date date) {
		return DF.format(date);
	}
	
	public static long getDifferenceInMinutes(Date start, Date end) {
		long duration = end.getTime() - start.getTime();
		
		return TimeUnit.MILLISECONDS.toMinutes(duration);
	}
	
	public static boolean checkDateBetweenTwoDates(Date start, Date end, Date check) {
		DummyLogger.sysout(TimezoneHelper.class.getName(), "checkDateBetweenTwoDates","Start --> " + start);
		DummyLogger.sysout(TimezoneHelper.class.getName(), "checkDateBetweenTwoDates","End --> " + end);
		DummyLogger.sysout(TimezoneHelper.class.getName(), "checkDateBetweenTwoDates","Check --> " + check);
		return check.compareTo(start) >= 0 && check.compareTo(end) <= 0;
	}
}
