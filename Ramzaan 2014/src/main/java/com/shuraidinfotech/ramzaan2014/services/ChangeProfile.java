package com.shuraidinfotech.ramzaan2014.services;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;

import com.shuraidinfotech.ramzaan2014.utils.AppConstants;
import com.shuraidinfotech.ramzaan2014.utils.HardLogging;
import com.shuraidinfotech.ramzaan2014.utils.Utils;

/**
 * Created by AABID on 18/5/14.
 */
public class ChangeProfile extends Service {

	private static final String TAG = "ChangeProfile";
	public static String OPERATION_TYPE = "type";

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		HardLogging.init();
		int type = intent.getExtras().getInt(OPERATION_TYPE);
		Utils.showToast(ChangeProfile.this,"Service started with type: "+type);
		AudioManager mAudioManager = (AudioManager)getSystemService(AUDIO_SERVICE);
		if(type== AppConstants.TYPE_TO_GENERAL){
			mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
			HardLogging.LogThis(TAG,"Switched To Normal Mode");
		}else{
			mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
			HardLogging.LogThis(TAG,"Switched To Silent Mode");
		}
		return START_NOT_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
