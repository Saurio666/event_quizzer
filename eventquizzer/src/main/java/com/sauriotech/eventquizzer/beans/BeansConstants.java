package com.sauriotech.eventquizzer.beans;

/**
 * @author SAURIOTech Solutions.
 *
 */
public final class BeansConstants {
	public final class Event
	{
		public static final String ATTENDANCE_OK = "EVT_ATTENDANCE_OK";
		public static final String ATTENDANCE_DUPLICATE = "EVT_ATTENDANCE_DUPLICATE";
		public static final String NOT_FOUND = "EVT_NOT_FOUND";
		public static final String NOT_AUTHORIZED = "EVT_NOT_AUTHORIZED";
		public static final String NOT_ACTIVE_QUIZ = "NOT_ACTIVE_QUIZ";
	}
	
	public final class Quizz
	{
		public static final String NOT_FOUND = "QUIZZ_NOT_FOUND";
		public static final String ALREADY_ANSWERED = "QUIZZ_ALREADY_ANSWERED";
		public static final String MAX_ATTEMPTS = "MAX_ATTEMPTS";
		public static final String OUT_OF_TIME = "OUT_OF_TIME";
	}
}
