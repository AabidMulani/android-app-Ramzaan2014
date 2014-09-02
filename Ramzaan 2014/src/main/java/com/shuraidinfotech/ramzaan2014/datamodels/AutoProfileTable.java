package com.shuraidinfotech.ramzaan2014.datamodels;

import android.content.Context;

import com.orm.SugarRecord;

/**
 * Created by AABID on 18/5/14.
 */
public class AutoProfileTable extends SugarRecord<AutoProfileTable> {

	private String name;
	private int startTimeHr;
	private int startTimeMin;
	private String startTimeStr;
	private int endTimeHr;
	private int endTimeMin;
	private String endTimeStr;
	private boolean isActive;

	public AutoProfileTable(Context context) {
		super(context);
	}

	public AutoProfileTable(Context context, String name, int startTimeHr, int startTimeMin,
	                        String startTimeStr,int endTimeHr, int endTimeMin,
	                        String endTimeStr, boolean isActive) {
		super(context);
	    this.name = name;
		this.startTimeHr = startTimeHr;
		this.startTimeMin = startTimeMin;
		this.startTimeStr = startTimeStr;
		this.endTimeHr = endTimeHr;
		this.endTimeMin = endTimeMin;
		this.endTimeStr = endTimeStr;
		this.isActive = isActive;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStartTimeHr() {
		return startTimeHr;
	}

	public void setStartTimeHr(int startTimeHr) {
		this.startTimeHr = startTimeHr;
	}

	public int getStartTimeMin() {
		return startTimeMin;
	}

	public void setStartTimeMin(int startTimeMin) {
		this.startTimeMin = startTimeMin;
	}

	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public int getEndTimeHr() {
		return endTimeHr;
	}

	public void setEndTimeHr(int endTimeHr) {
		this.endTimeHr = endTimeHr;
	}

	public int getEndTimeMin() {
		return endTimeMin;
	}

	public void setEndTimeMin(int endTimeMin) {
		this.endTimeMin = endTimeMin;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
}
