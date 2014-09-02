package com.shuraidinfotech.ramzaan2014.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.shuraidinfotech.ramzaan2014.R;
import com.shuraidinfotech.ramzaan2014.activity.MainActivity;
import com.shuraidinfotech.ramzaan2014.datamodels.AutoProfileTable;

/**
 * Created by AABID on 18/5/14.
 */
public class InitializeDatabase extends AsyncTask<Void, Void, Boolean> {
	private static final String TAG = "InitializeDatabase";
	private ProgressDialog progressDialog;
	private Activity activity;

	public InitializeDatabase(Activity activity) {
		this.activity = activity;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progressDialog = new ProgressDialog(activity);
		progressDialog.setMessage(activity.getString(R.string.msg_initialize_database));
		progressDialog.setIndeterminate(true);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.setCancelable(false);
		progressDialog.show();
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		try {
			//SET AUTO PROFILE DATABASE
			AutoProfileTable fazrProfile = new AutoProfileTable(activity,
					activity.getString(R.string.name_fazr), 5, 50, "05:50 AM", 6, 10, "06:10 AM", false);
			fazrProfile.save();
			AutoProfileTable zohrProfile = new AutoProfileTable(activity,
					activity.getString(R.string.name_zohr), 13, 15, "01:15 PM", 13, 50, "01:50 PM", false);
			zohrProfile.save();
			AutoProfileTable asrProfile = new AutoProfileTable(activity,
					activity.getString(R.string.name_ASR), 17, 20, "05:20 PM", 17, 45, "17:45 PM", false);
			asrProfile.save();
			AutoProfileTable mgrbProfile = new AutoProfileTable(activity,
					activity.getString(R.string.name_magrib), 18, 50, "06:50 PM", 19, 20, "07:20 PM",
					false);
			mgrbProfile.save();
			AutoProfileTable ishaProfile = new AutoProfileTable(activity,
					activity.getString(R.string.name_isha), 20, 25, "08:25 PM", 21, 00, "09:00 PM", false);
			ishaProfile.save();
			Logger.e(TAG,"Auto Profiler Database Initialized - Successfully");
			return true;
		} catch (Exception ex) {
			Logger.e(TAG, Logger.getStackTraceString(ex));
		}
		return false;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		progressDialog.dismiss();
		if (result) {
			SharedPreferences prefs = activity.getSharedPreferences(AppConstants.PREF_NAME, 0);
			SharedPreferences.Editor editor = prefs.edit();
			editor.putBoolean(AppConstants.PREF_INIT_APP,false);
			editor.commit();
			Utils.showToast(activity,"Welcome!");
			((MainActivity)activity).initUI();
		} else {
			Utils.showThisMsg(activity, activity.getString(R.string.title_error),
					activity.getString(R.string.msg_error_init_database),
					new Utils.OnOkPressedListener() {
						@Override
						public void onOKPressed() {
							activity.finish();
						}
					}
			);
		}
	}
}
