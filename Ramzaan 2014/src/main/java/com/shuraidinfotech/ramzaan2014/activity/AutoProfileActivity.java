package com.shuraidinfotech.ramzaan2014.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TimePicker;

import com.shuraidinfotech.ramzaan2014.R;
import com.shuraidinfotech.ramzaan2014.adaptors.AutoProfileListAdaptor;
import com.shuraidinfotech.ramzaan2014.datamodels.AutoProfileTable;
import com.shuraidinfotech.ramzaan2014.services.AlarmSetter;
import com.shuraidinfotech.ramzaan2014.services.ChangeProfile;
import com.shuraidinfotech.ramzaan2014.services.MyReceiver;
import com.shuraidinfotech.ramzaan2014.utils.AppConstants;
import com.shuraidinfotech.ramzaan2014.utils.HardLogging;
import com.shuraidinfotech.ramzaan2014.utils.Logger;
import com.shuraidinfotech.ramzaan2014.utils.Utils;
import com.shuraidinfotech.ramzaan2014.widget.CustomTimePicker;

import java.util.Calendar;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by AABID on 18/5/14.
 */
public class AutoProfileActivity extends Activity {
	@InjectView(R.id.listView)
	ListView listView;
	private List<AutoProfileTable> databaseList;
	private AutoProfileListAdaptor listAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_autoprofile_screen);
		ButterKnife.inject(this);
		databaseList = AutoProfileTable.listAll(AutoProfileTable.class);
		listAdapter = new AutoProfileListAdaptor(this, databaseList, onToggledListener);
		listView.setAdapter(listAdapter);
		listView.setOnItemClickListener(onItemClickListener);
	}

	AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			changeTime(position, databaseList.get(position).getStartTimeHr(),
					databaseList.get(position).getStartTimeMin(), databaseList.get(position).getEndTimeHr(),
					databaseList.get(position).getEndTimeMin());
		}
	};

	int selectedStartTimeHr, selectedStartTimeMin;

	private void changeTime(final int position, final int startTimeHr, final int startTimeMin,
	                        final int endTimeHr,final int endTimeMin) {

		final TimePickerDialog.OnTimeSetListener endTimeSet = new TimePickerDialog
				.OnTimeSetListener() {
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				if ((hourOfDay > selectedStartTimeHr) || (hourOfDay <= selectedStartTimeHr && minute
						> selectedStartTimeMin)) {
					if (databaseList.get(position).isActive()) {
						new RefreshAll(AutoProfileActivity.this, position, selectedStartTimeHr,
								selectedStartTimeMin, hourOfDay, minute).execute();
					} else {
						resetAlarmService(position, selectedStartTimeHr,
								selectedStartTimeMin, hourOfDay, minute, false);
					}
				} else {
					Utils.showThisMsg(AutoProfileActivity.this, getString(R.string.title_error),
							getString(R.string.error_endtime_smaller), new Utils
									.OnOkPressedListener() {
								@Override
								public void onOKPressed() {
									changeTime(position, startTimeHr, startTimeMin, endTimeHr,
											endTimeMin);
								}
							}
					);
				}
			}
		};

		final TimePickerDialog.OnTimeSetListener startTimeSet = new TimePickerDialog
				.OnTimeSetListener() {
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				selectedStartTimeHr = hourOfDay;
				selectedStartTimeMin = minute;
				CustomTimePicker endTimePickerDialog = new CustomTimePicker(AutoProfileActivity
						.this,endTimeSet, endTimeHr, endTimeMin, false);
				endTimePickerDialog.setMyTitle(getString(R.string.title_end_time));
				endTimePickerDialog.show();
			}
		};
		CustomTimePicker startTimePickerDialog = new CustomTimePicker(AutoProfileActivity.this,
				startTimeSet, startTimeHr, startTimeMin, false);
		startTimePickerDialog.setMyTitle(getString(R.string.title_start_time));
		startTimePickerDialog.show();

	}

	public String getTimeString(int hr, int min) {
		if (hr == 0) {
			return "12:" + addPadding(min) + " AM";
		} else if (hr > 12) {
			return addPadding(hr - 12) + ":" + addPadding(min) + " PM";
		} else {
			return addPadding(hr) + ":" + addPadding(min) + " AM";
		}
	}

	public String addPadding(int num) {
		return num > 9 ? String.valueOf(num) : "0" + num;
	}

	class RefreshAll extends AsyncTask<Void, Void, Void> {
		private static final String TAG = "RefreshAll";
		private ProgressDialog progressDialog;
		private Activity activity;
		int position, startH, startM, endH, endM;
		private boolean fromEditTime, toggledValue;

		RefreshAll(Activity activity, int position, int startH, int startM, int endH, int endM) {
			this.activity = activity;
			this.position = position;
			this.startH = startH;
			this.startM = startM;
			this.endH = endH;
			this.endM = endM;
			this.fromEditTime = true;
		}

		RefreshAll(Activity activity, int position, boolean toggledValue) {
			this.activity = activity;
			this.position = position;
			this.toggledValue = toggledValue;
			this.fromEditTime = false;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(activity);
			progressDialog.setMessage(activity.getString(R.string.msg_processing));
			progressDialog.setIndeterminate(true);
			progressDialog.setCanceledOnTouchOutside(false);
			progressDialog.setCancelable(false);
			progressDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				List<AutoProfileTable> activeProfileChange = AutoProfileTable.find
						(AutoProfileTable.class,
								" is_active = ? ", "true");
				for (AutoProfileTable autoProfileTable : activeProfileChange) {
					try {
						HardLogging.LogThis(TAG, "Removed [" + autoProfileTable.getName() + "] " +
								"at" +"[" + autoProfileTable.getStartTimeStr() + "] to SILENT");
						Calendar silentCal = Calendar.getInstance();
						silentCal.setTimeInMillis(System.currentTimeMillis());
						silentCal.set(Calendar.MILLISECOND, 0);
						silentCal.set(Calendar.SECOND, 0);
						silentCal.set(Calendar.MINUTE, autoProfileTable.getStartTimeMin());
						silentCal.set(Calendar.HOUR_OF_DAY, autoProfileTable.getStartTimeHr());

						//creating and assigning value to alarm manager class
						Intent silentIntent = new Intent(AutoProfileActivity.this,
								MyReceiver.class);
						silentIntent.putExtra(ChangeProfile.OPERATION_TYPE,
								AppConstants.TYPE_TO_SILENT);
						AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
						PendingIntent pendingIntent = PendingIntent.getBroadcast(AutoProfileActivity
										.this, 0,
								silentIntent, 0
						);
						alarmManager.cancel(pendingIntent);

						Calendar wakeCal = Calendar.getInstance();
						wakeCal.setTimeInMillis(System.currentTimeMillis());
						wakeCal.set(Calendar.MILLISECOND, 0);
						wakeCal.set(Calendar.SECOND, 0);
						wakeCal.set(Calendar.MINUTE, autoProfileTable.getEndTimeMin());
						wakeCal.set(Calendar.HOUR_OF_DAY, autoProfileTable.getEndTimeHr());

						HardLogging.LogThis(TAG, "Removed [" + autoProfileTable.getName() + "] " +
								"at " +"[" + autoProfileTable.getEndTimeStr() + "] to GENERAL");

						//creating and assigning value to alarm manager class
						Intent wakeIntent = new Intent(AutoProfileActivity.this, MyReceiver.class);
						wakeIntent.putExtra(ChangeProfile.OPERATION_TYPE,
								AppConstants.TYPE_TO_GENERAL);
						PendingIntent wakePendingIntent = PendingIntent.getBroadcast
								(AutoProfileActivity.this, 0,wakeIntent, 0);
						alarmManager.cancel(wakePendingIntent);
					} catch (Exception ex) {
						Logger.e(TAG, Logger.getStackTraceString(ex));
					}
				}
			} catch (Exception ex) {
				Logger.e(TAG, Logger.getStackTraceString(ex));
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			progressDialog.dismiss();
			if (fromEditTime) {
				resetAlarmService(position, startH, startM, endH, endM, true);
			} else {
				databaseList.get(position).setActive(toggledValue);
				databaseList.get(position).save();
				listAdapter.notifyDataSetChanged();
				Intent intent = new Intent(AutoProfileActivity.this, AlarmSetter.class);
				startService(intent);
			}
		}
	}

	private void resetAlarmService(int position, int startH, int startM, int endH, int endM,
	                               boolean isActive) {

		databaseList.get(position).setStartTimeHr(startH);
		databaseList.get(position).setStartTimeMin(startM);
		databaseList.get(position).setStartTimeStr(getTimeString(startH, startM));

		databaseList.get(position).setEndTimeHr(endH);
		databaseList.get(position).setEndTimeMin(endM);
		databaseList.get(position).setStartTimeStr(getTimeString(endH, endM));
		databaseList.get(position).save();
		listAdapter.notifyDataSetChanged();

		if (isActive) {
			Intent intent = new Intent(AutoProfileActivity.this, AlarmSetter.class);
			startService(intent);
		}
	}


	public interface OnToggledListener {
		public void onToggle(int position, boolean isChecked);
	}

	public OnToggledListener onToggledListener = new OnToggledListener() {
		@Override
		public void onToggle(int position, boolean isChecked) {
			new RefreshAll(AutoProfileActivity.this, position, isChecked).execute();
		}
	};

}
