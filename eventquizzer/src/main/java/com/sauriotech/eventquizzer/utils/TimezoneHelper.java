package com.sauriotech.eventquizzer.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
	private static final DateFormat DF = new SimpleDateFormat("dd/MM/yyyy");
	private static final DateFormat DF_TIME = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private static final DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm:ss");
	
	public static Date getDateFromTimezone(String timeZone) {
		DummyLogger.sysout(TimezoneHelper.class.getName(), "getDateFromTimezone", "timeZone --> " + timeZone);
		TimeZone t2 = TimeZone.getTimeZone(timeZone);
		DummyLogger.sysout(TimezoneHelper.class.getName(), "getDateFromTimezone", "t2 --> " + t2);
		DummyLogger.sysout(TimezoneHelper.class.getName(), "getDateFromTimezone", "t2.getID() --> " + t2.getID());
		
		DummyLogger.sysout(TimezoneHelper.class.getName(), "getDateFromTimezone", "Calendar.getInstance(t2).getTime() --> " + Calendar.getInstance(t2).getTime());
		DummyLogger.sysout(TimezoneHelper.class.getName(), "getDateFromTimezone", "Calendar.getInstance(t2).getTime() --> " + Calendar.getInstance(t2).getTime());
		
		Date nowUtc = new Date();
		TimeZone tz = TimeZone.getTimeZone(timeZone);
		Calendar nowTz = Calendar.getInstance(tz);
		nowTz.setTime(nowUtc);		
		DummyLogger.sysout(TimezoneHelper.class.getName(), "getDateFromTimezone", "nowTz.getTime() --> " + nowTz.getTime());
				
		ZoneId idZona = ZoneId.of(timeZone);
		LocalTime horaActual = LocalTime.now(idZona);
		DummyLogger.sysout(TimezoneHelper.class.getName(), "getDateFromTimezone", "horaActual --> " + horaActual);
		DummyLogger.sysout(TimezoneHelper.class.getName(), "getDateFromTimezone", "horaActual --> " + horaActual.format(formato));
		
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
