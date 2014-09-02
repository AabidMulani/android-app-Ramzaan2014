package com.shuraidinfotech.ramzaan2014.adaptors;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.shuraidinfotech.ramzaan2014.R;
import com.shuraidinfotech.ramzaan2014.activity.AutoProfileActivity;
import com.shuraidinfotech.ramzaan2014.datamodels.AutoProfileTable;
import com.shuraidinfotech.ramzaan2014.utils.Logger;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by AABID on 18/5/14.
 */
public class AutoProfileListAdaptor extends BaseAdapter {

	private static final String TAG = "AutoProfileListAdaptor";
	private LayoutInflater inflater;
	private Activity activity;
	private List<AutoProfileTable> autoProfileTableList;
	private int themeColor, redColor;
	private AutoProfileActivity.OnToggledListener onToggledListener;

	public AutoProfileListAdaptor(Activity activity, List<AutoProfileTable> autoProfileTableList,
	                              AutoProfileActivity.OnToggledListener onToggledListener) {
		Logger.d(TAG, "ListAllAdaptor");
		this.activity = activity;
		this.autoProfileTableList = autoProfileTableList;
		this.onToggledListener = onToggledListener;
		this.inflater = (LayoutInflater) activity.getSystemService(Context
				.LAYOUT_INFLATER_SERVICE);
		themeColor = activity.getResources().getColor(R.color.theme_text_color);
		redColor = Color.RED;
	}

	@Override
	public int getCount() {
		return autoProfileTableList.size();
	}

	@Override
	public AutoProfileTable getItem(int position) {
		return autoProfileTableList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	class ViewHolder {
		@InjectView(R.id.text_view_title)
		TextView titleText;
		@InjectView(R.id.text_view_start_value)
		TextView startTime;
		@InjectView(R.id.text_view_end_value)
		TextView endTime;
		@InjectView(R.id.toggleButton)
		ToggleButton onOffButton;

		public ViewHolder(View view) {
			ButterKnife.inject(this, view);
		}
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view;
		if (convertView == null) {
			view = inflater.inflate(R.layout.inflater_auto_profile_listitem, null);
			ViewHolder viewHolder = new ViewHolder(view);
			view.setTag(viewHolder);
		} else {
			view = convertView;
		}
		final ViewHolder holder = (ViewHolder) view.getTag();
		AutoProfileTable currentObject = getItem(position);
		holder.onOffButton.setChecked(currentObject.isActive());
		int color = currentObject.isActive() ? themeColor : redColor;
		holder.titleText.setText(currentObject.getName());
		holder.titleText.setTextColor(color);
		holder.startTime.setText(currentObject.getStartTimeStr());
		holder.startTime.setTextColor(color);
		holder.endTime.setText(currentObject.getEndTimeStr());
		holder.endTime.setTextColor(color);
		holder.onOffButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onToggledListener.onToggle(position, holder.onOffButton.isChecked());
			}
		});
		return view;
	}
}
