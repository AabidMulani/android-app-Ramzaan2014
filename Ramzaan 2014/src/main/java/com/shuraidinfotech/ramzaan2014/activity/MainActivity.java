package com.shuraidinfotech.ramzaan2014.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.shuraidinfotech.ramzaan2014.R;
import com.shuraidinfotech.ramzaan2014.utils.AppConstants;
import com.shuraidinfotech.ramzaan2014.utils.InitializeDatabase;

import butterknife.ButterKnife;


public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.inject(this);
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color
					.theme_text_color)));
			actionBar.setIcon(new ColorDrawable(0));
		}
		SharedPreferences prefs = getSharedPreferences(AppConstants.PREF_NAME, 0);
		boolean appStarted = prefs.getBoolean(AppConstants.PREF_INIT_APP, true);
		if (appStarted) {
			new InitializeDatabase(this).execute();
		} else {
			initUI();
		}
	}

	public void initUI() {

	}

	public void onProfileChange(View view) {
		Intent intent = new Intent(MainActivity.this, AutoProfileActivity.class);
		startActivity(intent);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
