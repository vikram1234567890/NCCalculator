

package com.c.nccalculator;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class MoreActivity extends AppCompatActivity implements OnClickListener {
	Button about,help,vibrate;
		String s="";
	SharedPreferences sharedPreferences;
	InterstitialAd mInterstitialAd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more_activity);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		mInterstitialAd = new InterstitialAd(MoreActivity.this);
		mInterstitialAd.setAdUnitId("ca-app-pub-1211635675454735/1518006401");
		AdRequest adRequest = new AdRequest.Builder() .build();
		mInterstitialAd.loadAd(adRequest);
		mInterstitialAd.setAdListener(new AdListener() {
			@Override
			public void onAdLoaded() {
				if (mInterstitialAd.isLoaded()) {
					mInterstitialAd.show();
				}
			}});
			 about=(Button) findViewById(R.id.Button02);

		help=(Button) findViewById(R.id.help);
		vibrate=(Button) findViewById(R.id.Button04);

		  	about.setOnClickListener(this);

		help.setOnClickListener(this);
		vibrate.setOnClickListener(this);

	changeVibrateStatus();

	}
	 void changeVibrateStatus()
	{
		if (new StartActivity().getSharedPref("Vibrate",this).contains("on"))
		{
			vibrate.setText("Vibrate ON");
		}
		else 	if (new StartActivity().getSharedPref("Vibrate",this).contains("off"))
		{
			vibrate.setText("Vibrate OFF");
		}
	}
	 final void helpBuilder(Builder builder)
	  {
			
	      	   // to allow cancelling the dialog box
	      	   builder.setCancelable(true);
	      	   // set title
	      	   builder.setTitle("Help");
	      	   // set message
	      	   builder.setMessage("-Select mode to change from binary,decimal,octal,hexadecimal,DEFAULT is General mode where calculator performs basic arithmetic operations\n\n-To popup keyboard click on first text box once or twice \n\n-To hide keyboard press back button or click Hide button on keyboard.");
	      	   // display dialog box
	      	  
	      	      builder.show();
	  }

	 public void onClick(View v)
	 {
		 
		 
		switch (v.getId()) 
		{
			case R.id.Button04:
				if (new StartActivity().getSharedPref("Vibrate",this).contains("on"))
				{
					new StartActivity().changeSharedPref("Vibrate","off",this);
				}
				else 	if (new StartActivity().getSharedPref("Vibrate",this).contains("off"))
				{
					new StartActivity().changeSharedPref("Vibrate","on",this);
				}
				changeVibrateStatus();

				break;
		case R.id.Button02:
	
			 Intent i = new Intent(MoreActivity.this,AboutActivity.class);
		       startActivity(i);
		   	finish();
		       break;

		case R.id.help:
			Builder builder=new Builder(MoreActivity.this);
		helpBuilder(builder);
			break;


		}
		
	 }

	@Override
	public void onBackPressed() {

		Intent i=new Intent(MoreActivity.this,StartActivity.class);
		startActivity(i);
		finish();

	}




	@Override
	public boolean onSupportNavigateUp(){
		Intent intent=new Intent(MoreActivity.this,StartActivity.class);
		startActivity(intent);
		finish();
		return true;
	}
}