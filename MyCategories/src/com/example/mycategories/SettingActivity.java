package com.example.mycategories;

import android.os.Bundle;
import android.preference.PreferenceActivity;


public class SettingActivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_setting);
		addPreferencesFromResource(R.layout.preferences);
	}

}
