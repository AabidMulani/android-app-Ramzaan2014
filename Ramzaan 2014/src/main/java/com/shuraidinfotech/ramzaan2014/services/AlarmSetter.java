package com.shuraidinfotech.ramzaan2014.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.shuraidinfotech.ramzaan2014.datamodels.AutoProfileTable;
import com.shuraidinfotech.ramzaan2014.utils.AppConstants;
import com.shuraidinfotech.ramzaan2014.utils.HardLogging;
import com.shuraidinfotech.ramzaan2014.utils.Logger;

import java.util.Calendar;
import java.util.List;

/**
 * Created by AABID on 18/5/14.
 */
public class AlarmSetter extends Service {
	private final String TAG = "AlarmSetter";

	@Override
	public void onCreate() {
		super.onCreate();
		HardLogging.init();
		//Set all auto-profile alarms
		List<AutoProfileTable> activeProfileChange = AutoProfileTable.find(AutoProfileTable.class,
				" is_active = ? ", "true");
		HardLogging.LogThis(TAG, "Assigning New Alarm Service [" + activeProfileChange.size() +
				"]");
		for (AutoProfileTable autoProfileTable : activeProfileChange) {
			try {
				HardLogging.LogThis(TAG, "Assigned [" + autoProfileTable.getName() + "] at " +
						"[" + autoProfileTable.getStartTimeHr()+":" + autoProfileTable
						.getStartTimeMin() + "] to SILENT");
				Calendar silentCal = Calendar.getInstance();
				silentCal.setTimeInMillis(System.currentTimeMillis());
				silentCal.set(Calendar.MILLISECOND, 0);
				silentCal.set(Calendar.SECOND, 0);
				silentCal.set(Calendar.MINUTE, autoProfileTable.getStartTimeMin());
				silentCal.set(Calendar.HOUR_OF_DAY, autoProfileTable.getStartTimeHr());

				//creating and assigning value to alarm manager class
				Intent silentIntent = new Intent(AlarmSetter.this, MyReceiver.class);
				silentIntent.putExtra(ChangeProfile.OPERATION_TYPE, AppConstants.TYPE_TO_SILENT);
				AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
				PendingIntent pendingIntent = PendingIntent.getBroadcast(AlarmSetter.this, 0,
						silentIntent, 0);
				alarmManager.set(AlarmManager.RTC, silentCal.getTimeInMillis(),
						pendingIntent);


				Calendar wakeCal = Calendar.getInstance();
				wakeCal.setTimeInMillis(System.currentTimeMillis());
				wakeCal.set(Calendar.MILLISECOND, 0);
				wakeCal.set(Calendar.SECOND, 0);
				wakeCal.set(Calendar.MINUTE, autoProfileTable.getEndTimeMin());
				wakeCal.set(Calendar.HOUR_OF_DAY, autoProfileTable.getEndTimeHr());

				HardLogging.LogThis(TAG, "Assigned [" + autoProfileTable.getName() + "] at " +
						"[" + autoProfileTable.getEndTimeHr()+":" + autoProfileTable
						.getEndTimeMin() + "] to GENERAL");

				//creating and assigning value to alarm manager class
				Intent wakeIntent = new Intent(AlarmSetter.this, MyReceiver.class);
				wakeIntent.putExtra(ChangeProfile.OPERATION_TYPE, AppConstants.TYPE_TO_GENERAL);
				PendingIntent wakePendingIntent = PendingIntent.getBroadcast(AlarmSetter.this, 0,
						wakeIntent, 0);
				alarmManager.set(AlarmManager.RTC, wakeCal.getTimeInMillis(),
						wakePendingIntent);
			} catch (Exception ex) {
				Logger.e(TAG, Logger.getStackTraceString(ex));
				HardLogging.LogThis(TAG, "Exception Occurred");
			}
		}
		stopSelf();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Logger.e(TAG,"onDestroy()");
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
