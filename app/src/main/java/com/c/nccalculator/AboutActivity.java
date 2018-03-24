package com.c.nccalculator;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {
	
	TextView textView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		PackageInfo pInfo = null;
		textView=(TextView) findViewById(R.id.textView1);
		try {
			pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (PackageManager.NameNotFoundException e) {
			int verCode = pInfo.versionCode;
			e.printStackTrace();
		}

		String version = pInfo.versionName;

		textView.setText("About\n\n"+getApplicationName(this)+" v"+version+"\n" +
				"\n" +
				"Developer\n" +
				"\"VR apps\"");

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		
	}
	public static String getApplicationName(Context context) {
		ApplicationInfo applicationInfo = context.getApplicationInfo();
		int stringId = applicationInfo.labelRes;
		return stringId == 0 ? applicationInfo.nonLocalizedLabel.toString() : context.getString(stringId);
	}
	@Override
	public void onBackPressed() {

		Intent i=new Intent(AboutActivity.this,MoreActivity.class);
		startActivity(i);
		finish();

	}

	@Override
	public boolean onSupportNavigateUp(){
		Intent intent=new Intent(AboutActivity.this,MoreActivity.class);
		startActivity(intent);
		finish();
		return true;
	}
}
