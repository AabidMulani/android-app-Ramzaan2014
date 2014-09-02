package com.shuraidinfotech.ramzaan2014.utils;

import android.util.Log;

public class Logger {

	private static final boolean DO_LOG = true;

	public static void d(String tag, String msg) {
		if (DO_LOG)
			Log.d(tag, msg);
	}

	public static void i(String tag, String msg) {
		if (DO_LOG)
			Log.i(tag, msg);
	}

	public static void e(String tag, String msg) {
		if (DO_LOG)
			Log.e(tag, msg);
	}

	public static void v(String tag, String msg) {
		if (DO_LOG)
			Log.v(tag, msg);
	}

	public static void w(String tag, String msg) {
		if (DO_LOG)
			Log.w(tag, msg);
	}

	public static String getStackTraceString(Exception e) {
		return Log.getStackTraceString(e);
	}

}
