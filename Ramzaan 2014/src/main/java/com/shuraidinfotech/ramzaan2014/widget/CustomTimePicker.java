package com.shuraidinfotech.ramzaan2014.widget;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TimePicker;

/**
 * Created by AABID on 18/5/14.
 */
public class CustomTimePicker extends TimePickerDialog {

	private String title;

	public CustomTimePicker(Context context, OnTimeSetListener callBack, int hourOfDay,
	                        int minute, boolean is24HourView) {
		super(context, callBack, hourOfDay, minute, is24HourView);
	}

	public CustomTimePicker(Context context, int theme, OnTimeSetListener callBack, int hourOfDay,
	                        int minute, boolean is24HourView) {
		super(context, theme, callBack, hourOfDay, minute, is24HourView);
	}


	@Override
	public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
		super.onTimeChanged(view, hourOfDay, minute);
		if(title!=null)	setTitle(title);
	}

	public void setMyTitle(String title) {
		this.title = title;
	}
}
