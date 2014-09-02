package com.shuraidinfotech.ramzaan2014.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.shuraidinfotech.ramzaan2014.utils.HardLogging;

import java.util.Calendar;

/**
 * Created by AABID on 18/5/14.
 */
public class BootCompletedIntentReceiver extends BroadcastReceiver {
	private final String TAG = "BootCompletedIntentReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
			HardLogging.init();
			HardLogging.LogThis(TAG," Boot Detected ");
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(System.currentTimeMillis());
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.HOUR, 0);
			calendar.set(Calendar.AM_PM, Calendar.AM);
			calendar.add(Calendar.DAY_OF_MONTH, 1);

			Intent wakeIntent = new Intent(context, AlarmSetter.class);
			PendingIntent wakePendingIntent = PendingIntent.getService(context, 0,
					wakeIntent, 0);

			AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context
					.ALARM_SERVICE);
			alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
					AlarmManager.INTERVAL_DAY, wakePendingIntent);

			Intent pushIntent = new Intent(context, AlarmSetter.class);
			context.startService(pushIntent);
		}
	}
}
