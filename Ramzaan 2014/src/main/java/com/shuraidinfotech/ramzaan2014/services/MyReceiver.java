package com.shuraidinfotech.ramzaan2014.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by AABID on 19/5/14.
 */
public class MyReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		Intent serviceIntent = new Intent(context, ChangeProfile.class);
		serviceIntent.putExtra(ChangeProfile.OPERATION_TYPE, intent.getExtras().getInt
				(ChangeProfile.OPERATION_TYPE));
		context.startService(serviceIntent);
	}
}