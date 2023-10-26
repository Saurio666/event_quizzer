package com.sauriotech.eventquizzer.utils;


public final class DummyLogger {
	private static final boolean SWITCH = true;

	public static void sysout(String className, String method, Object message) {
		if (SWITCH)
			System.out.println("[" + className + "] [" + method + "] " + message);
	}	
}
