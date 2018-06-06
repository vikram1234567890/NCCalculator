
package com.c.nccalculator;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SlidingDrawer;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class StartActivity extends AppCompatActivity implements OnClickListener, DrawerLayout.DrawerListener {


	static TextView eText1, eText2, eText3, eText4, eText5;
	static TextView tView1, tView2, tView3, tView4;
	Button bone, btwo, bthree, bfour, bfive, bsix, bseven, beight, bnine, bzero, bdot, bplus, bminus, bmultiplication, bdivide, bequalto, bpower, broot, bcancel, bfactorial, ba, bb, bc, bd, be, bf, bopenbracket, bclosedbraclet;
	ImageButton bhelp;
	static String s = "";
	String[] NumberSystem = {"General mode", "From Binary", "From Decimal", "From Octal", "From HexaDecimal"};
	Editable str = null;
	SharedPreferences sharedPreferences;
	Vibrator vibrate;
	String shared_pref = "shared_pref";
	private String version;
	private AlertDialog alertDialog;
	private LinearLayout tut_1, tut_2, tut_3;
	private Button tut_1_btn, tut_2_btn, tut_3_btn;
	private DrawerLayout drawer;

	public StartActivity() {

	}

	static int spinindex, i1 = 0, count = 0, rateCount = 0;

	static String[] otherList;
	Boolean equal = false;
	private AdView mAdView, adView1;
	static boolean update_check = false, first_time = true;
	InterstitialAd mInterstitialAd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_activity);

		drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.setDrawerListener(this);
		vibrate = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
		count = 0;
		tView1 = (TextView) findViewById(R.id.textView1);
		tView2 = (TextView) findViewById(R.id.textView2);
		tView3 = (TextView) findViewById(R.id.textView3);
		tView4 = (TextView) findViewById(R.id.textView4);

		eText1 = (TextView) findViewById(R.id.editText1);
		eText2 = (TextView) findViewById(R.id.editText2);
		eText3 = (TextView) findViewById(R.id.editText3);
		eText4 = (TextView) findViewById(R.id.editText4);
		eText5 = (TextView) findViewById(R.id.editText5);
		tut_1 = (LinearLayout) findViewById(R.id.tut_1);
		tut_2 = (LinearLayout) findViewById(R.id.tut_2);
		tut_3 = (LinearLayout) findViewById(R.id.tut_3);

		tut_1_btn = (Button) findViewById(R.id.tut_1_btn);
		tut_2_btn = (Button) findViewById(R.id.tut_2_btn);
		tut_3_btn = (Button) findViewById(R.id.tut_3_btn);

		tut_1_btn.setOnClickListener(this);
		tut_2_btn.setOnClickListener(this);
		tut_3_btn.setOnClickListener(this);

		if (getSharedPref("tutorial", this).equals("")) {
			tut_1();
		}
		eText1.setOnClickListener(this);
		if (!getSharedPref("change", this).contains("yes")) {
			changeSharedPref("Rate", "no", this);
			changeSharedPref("TempStore", "", this);
			changeSharedPref("Vibrate", "on", this);
			changeSharedPref("change", "yes", this);

		}
		if (getSharedPref("TempStore", this).trim().length() != 0) {
			eText1.setText(getSharedPref("TempStore", this));

		}
			eText1.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
					changeSharedPref("TempStore", eText1.getText().toString(), StartActivity.this);


			}

			@Override
			public void afterTextChanged(Editable editable) {

			}
		});


		bone = (Button) findViewById(R.id.one);
		btwo = (Button) findViewById(R.id.two);
		bthree = (Button) findViewById(R.id.three);
		bfour = (Button) findViewById(R.id.four);
		bfive = (Button) findViewById(R.id.five);
		bsix = (Button) findViewById(R.id.six);
		bseven = (Button) findViewById(R.id.seven);
		beight = (Button) findViewById(R.id.eight);
		bnine = (Button) findViewById(R.id.nine);
		bzero = (Button) findViewById(R.id.zero);
		ba = (Button) findViewById(R.id.a);
		bb = (Button) findViewById(R.id.b);
		bc = (Button) findViewById(R.id.c);
		bd = (Button) findViewById(R.id.d);
		be = (Button) findViewById(R.id.e);
		bf = (Button) findViewById(R.id.f);
		bdot = (Button) findViewById(R.id.dot);
		bplus = (Button) findViewById(R.id.plus);
		bminus = (Button) findViewById(R.id.minus);
		bmultiplication = (Button) findViewById(R.id.multiplication);
		bdivide = (Button) findViewById(R.id.divide);
		bequalto = (Button) findViewById(R.id.equalto);
		bpower = (Button) findViewById(R.id.power);
		broot = (Button) findViewById(R.id.Root);
		bcancel = (Button) findViewById(R.id.cancel);
		bfactorial = (Button) findViewById(R.id.factorial);
		bopenbracket = (Button) findViewById(R.id.openB);
		bclosedbraclet = (Button) findViewById(R.id.closedB);
		bhelp = (ImageButton) findViewById(R.id.help);


		bone.setOnClickListener(StartActivity.this);
		btwo.setOnClickListener(StartActivity.this);
		bthree.setOnClickListener(StartActivity.this);
		bfour.setOnClickListener(StartActivity.this);
		bfive.setOnClickListener(StartActivity.this);
		bsix.setOnClickListener(StartActivity.this);
		bseven.setOnClickListener(StartActivity.this);
		beight.setOnClickListener(StartActivity.this);
		bnine.setOnClickListener(StartActivity.this);
		bzero.setOnClickListener(StartActivity.this);
		bcancel.setOnClickListener(StartActivity.this);
		bplus.setOnClickListener(StartActivity.this);
		bminus.setOnClickListener(StartActivity.this);
		bmultiplication.setOnClickListener(StartActivity.this);
		bdivide.setOnClickListener(StartActivity.this);
		bequalto.setOnClickListener(StartActivity.this);
		ba.setOnClickListener(StartActivity.this);
		bb.setOnClickListener(StartActivity.this);
		bc.setOnClickListener(StartActivity.this);
		bd.setOnClickListener(StartActivity.this);
		be.setOnClickListener(StartActivity.this);
		bf.setOnClickListener(StartActivity.this);
		bcancel.setOnClickListener(StartActivity.this);
		bdot.setOnClickListener(StartActivity.this);
		bfactorial.setOnClickListener(StartActivity.this);
		bpower.setOnClickListener(StartActivity.this);
		broot.setOnClickListener(StartActivity.this);
		bopenbracket.setOnClickListener(StartActivity.this);
		bclosedbraclet.setOnClickListener(StartActivity.this);
		bhelp.setOnClickListener(StartActivity.this);

		try {
			if (getIntent().getStringExtra("H").trim().length() != 0) {
				eText1.setText(getIntent().getStringExtra("H"));
				getIntent().removeExtra("H");
			}
		} catch (Exception e) {

		}
		bcancel.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				vibrate.vibrate(30);
				eText1.setText("");
				eText2.setText("");
				eText3.setText("");
				eText4.setText("");
				eText5.setText("");


				return false;
			}
		});
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(StartActivity.this,
				R.layout.support_simple_spinner_dropdown_item, NumberSystem);
		Spinner spinnerView = (Spinner) findViewById(R.id.spinner);
		spinnerView.setAdapter(adapter1);
		count = 0;

		spinnerView.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

				int index = arg2;
				spinindex = index;


				switch (spinindex) {
					case 0:
						if (!first_time) {
							if (eText1.getText().toString().length() != 0) {

								eText2.setText("");
								eText3.setText("");
								eText4.setText("");
								eText5.setText("");
							}

							tView1.setVisibility(View.GONE);
							tView2.setVisibility(View.GONE);
							tView3.setVisibility(View.GONE);
							tView4.setVisibility(View.GONE);

							eText2.setVisibility(View.GONE);
							eText3.setVisibility(View.GONE);
							eText4.setVisibility(View.GONE);
							eText5.setVisibility(View.GONE);
							btwo.setClickable(true);
							bthree.setClickable(true);
							bfour.setClickable(true);
							bfive.setClickable(true);
							bsix.setClickable(true);
							bseven.setClickable(true);
							beight.setClickable(true);
							bnine.setClickable(true);

							btwo.setTextColor((getResources().getColor(R.color.white)));
							bthree.setTextColor((getResources().getColor(R.color.white)));
							bfour.setTextColor((getResources().getColor(R.color.white)));
							bfive.setTextColor((getResources().getColor(R.color.white)));
							bsix.setTextColor((getResources().getColor(R.color.white)));
							bseven.setTextColor((getResources().getColor(R.color.white)));
							beight.setTextColor((getResources().getColor(R.color.white)));
							bnine.setTextColor((getResources().getColor(R.color.white)));

							ba.setEnabled(false);
							bb.setEnabled(false);
							bc.setEnabled(false);
							bd.setEnabled(false);
							be.setEnabled(false);
							bf.setEnabled(false);

							ba.setClickable(false);
							bb.setClickable(false);
							bc.setClickable(false);
							bd.setClickable(false);
							be.setClickable(false);
							bf.setClickable(false);

							ba.setTextColor((getResources().getColor(R.color.light_green)));
							bb.setTextColor((getResources().getColor(R.color.light_green)));
							bc.setTextColor((getResources().getColor(R.color.light_green)));
							bd.setTextColor((getResources().getColor(R.color.light_green)));
							be.setTextColor((getResources().getColor(R.color.light_green)));
							bf.setTextColor((getResources().getColor(R.color.light_green)));

							bfactorial.setClickable(true);
							bpower.setClickable(true);
							broot.setClickable(true);
							bfactorial.setTextColor((getResources().getColor(R.color.black)));
							bpower.setTextColor((getResources().getColor(R.color.black)));
							broot.setTextColor((getResources().getColor(R.color.black)));

						} else {
							first_time = false;
						}
						break;
					case 1:

						if (eText1.getText().toString().length() != 0) {
							eText1.setText("");

							eText2.setText("");
							eText3.setText("");
							eText4.setText("");
							eText5.setText("");


						}
						tView1.setVisibility(View.GONE);
						tView2.setVisibility(View.VISIBLE);
						tView3.setVisibility(View.VISIBLE);
						tView4.setVisibility(View.VISIBLE);

						eText2.setVisibility(View.GONE);
						eText3.setVisibility(View.VISIBLE);
						eText4.setVisibility(View.VISIBLE);
						eText5.setVisibility(View.VISIBLE);


						btwo.setClickable(false);
						bthree.setClickable(false);
						bfour.setClickable(false);
						bfive.setClickable(false);
						bsix.setClickable(false);
						bseven.setClickable(false);
						beight.setClickable(false);
						bnine.setClickable(false);
						btwo.setTextColor((getResources().getColor(R.color.light_gray)));
						bthree.setTextColor((getResources().getColor(R.color.light_gray)));
						bfour.setTextColor((getResources().getColor(R.color.light_gray)));
						bfive.setTextColor((getResources().getColor(R.color.light_gray)));
						bsix.setTextColor((getResources().getColor(R.color.light_gray)));
						bseven.setTextColor((getResources().getColor(R.color.light_gray)));
						beight.setTextColor((getResources().getColor(R.color.light_gray)));
						bnine.setTextColor((getResources().getColor(R.color.light_gray)));


						ba.setEnabled(false);
						bb.setEnabled(false);
						bc.setEnabled(false);
						bd.setEnabled(false);
						be.setEnabled(false);
						bf.setEnabled(false);

						ba.setClickable(false);
						bb.setClickable(false);
						bc.setClickable(false);
						bd.setClickable(false);
						be.setClickable(false);
						bf.setClickable(false);


						ba.setTextColor((getResources().getColor(R.color.light_green)));
						bb.setTextColor((getResources().getColor(R.color.light_green)));
						bc.setTextColor((getResources().getColor(R.color.light_green)));
						bd.setTextColor((getResources().getColor(R.color.light_green)));
						be.setTextColor((getResources().getColor(R.color.light_green)));
						bf.setTextColor((getResources().getColor(R.color.light_green)));

						bfactorial.setClickable(false);
						bpower.setClickable(false);
						broot.setClickable(false);
						bfactorial.setTextColor((getResources().getColor(R.color.light_green)));
						bpower.setTextColor((getResources().getColor(R.color.light_green)));
						broot.setTextColor((getResources().getColor(R.color.light_green)));

						break;

					case 2:
						if (eText1.getText().toString().length() != 0) {
							eText1.setText("");

							eText2.setText("");
							eText3.setText("");
							eText4.setText("");
							eText5.setText("");
						}
						tView1.setVisibility(View.VISIBLE);
						tView2.setVisibility(View.GONE);
						tView3.setVisibility(View.VISIBLE);
						tView4.setVisibility(View.VISIBLE);

						eText2.setVisibility(View.VISIBLE);
						eText3.setVisibility(View.GONE);
						eText4.setVisibility(View.VISIBLE);
						eText5.setVisibility(View.VISIBLE);


						btwo.setClickable(true);
						bthree.setClickable(true);
						bfour.setClickable(true);
						bfive.setClickable(true);
						bsix.setClickable(true);
						bseven.setClickable(true);
						beight.setClickable(true);
						bnine.setClickable(true);

						btwo.setTextColor((getResources().getColor(R.color.white)));
						bthree.setTextColor((getResources().getColor(R.color.white)));
						bfour.setTextColor((getResources().getColor(R.color.white)));
						bfive.setTextColor((getResources().getColor(R.color.white)));
						bsix.setTextColor((getResources().getColor(R.color.white)));
						bseven.setTextColor((getResources().getColor(R.color.white)));
						beight.setTextColor((getResources().getColor(R.color.white)));
						bnine.setTextColor((getResources().getColor(R.color.white)));


						ba.setEnabled(false);
						bb.setEnabled(false);
						bc.setEnabled(false);
						bd.setEnabled(false);
						be.setEnabled(false);
						bf.setEnabled(false);


						ba.setClickable(false);
						bb.setClickable(false);
						bc.setClickable(false);
						bd.setClickable(false);
						be.setClickable(false);
						bf.setClickable(false);
						ba.setTextColor((getResources().getColor(R.color.light_green)));
						bb.setTextColor((getResources().getColor(R.color.light_green)));
						bc.setTextColor((getResources().getColor(R.color.light_green)));
						bd.setTextColor((getResources().getColor(R.color.light_green)));
						be.setTextColor((getResources().getColor(R.color.light_green)));
						bf.setTextColor((getResources().getColor(R.color.light_green)));


						bfactorial.setClickable(false);
						bpower.setClickable(false);
						broot.setClickable(false);
						bfactorial.setTextColor((getResources().getColor(R.color.light_green)));
						bpower.setTextColor((getResources().getColor(R.color.light_green)));
						broot.setTextColor((getResources().getColor(R.color.light_green)));


						break;

					case 3:
						if (eText1.getText().toString().length() != 0) {
							eText1.setText("");

							eText2.setText("");
							eText3.setText("");
							eText4.setText("");
							eText5.setText("");
						}
						tView1.setVisibility(View.VISIBLE);
						tView2.setVisibility(View.VISIBLE);
						tView3.setVisibility(View.GONE);
						tView4.setVisibility(View.VISIBLE);

						eText2.setVisibility(View.VISIBLE);
						eText3.setVisibility(View.VISIBLE);
						eText4.setVisibility(View.GONE);
						eText5.setVisibility(View.VISIBLE);


						btwo.setClickable(true);
						bthree.setClickable(true);
						bfour.setClickable(true);
						bfive.setClickable(true);
						bsix.setClickable(true);
						bseven.setClickable(true);
						beight.setClickable(false);
						bnine.setClickable(false);


						btwo.setTextColor((getResources().getColor(R.color.white)));
						bthree.setTextColor((getResources().getColor(R.color.white)));
						bfour.setTextColor((getResources().getColor(R.color.white)));
						bfive.setTextColor((getResources().getColor(R.color.white)));
						bsix.setTextColor((getResources().getColor(R.color.white)));
						bseven.setTextColor((getResources().getColor(R.color.white)));
						beight.setTextColor((getResources().getColor(R.color.light_gray)));
						bnine.setTextColor((getResources().getColor(R.color.light_gray)));


						ba.setEnabled(false);
						bb.setEnabled(false);
						bc.setEnabled(false);
						bd.setEnabled(false);
						be.setEnabled(false);
						bf.setEnabled(false);


						ba.setClickable(false);
						bb.setClickable(false);
						bc.setClickable(false);
						bd.setClickable(false);
						be.setClickable(false);
						bf.setClickable(false);
						ba.setTextColor((getResources().getColor(R.color.light_green)));
						bb.setTextColor((getResources().getColor(R.color.light_green)));
						bc.setTextColor((getResources().getColor(R.color.light_green)));
						bd.setTextColor((getResources().getColor(R.color.light_green)));
						be.setTextColor((getResources().getColor(R.color.light_green)));
						bf.setTextColor((getResources().getColor(R.color.light_green)));


						bfactorial.setClickable(false);
						bpower.setClickable(false);
						broot.setClickable(false);
						bfactorial.setTextColor((getResources().getColor(R.color.light_green)));
						bpower.setTextColor((getResources().getColor(R.color.light_green)));
						broot.setTextColor((getResources().getColor(R.color.light_green)));
						break;

					case 4:
						if (eText1.getText().toString().length() != 0) {
							eText1.setText("");

							eText2.setText("");
							eText3.setText("");
							eText4.setText("");
							eText5.setText("");
						}
						tView1.setVisibility(View.VISIBLE);
						tView2.setVisibility(View.VISIBLE);
						tView3.setVisibility(View.VISIBLE);
						tView4.setVisibility(View.GONE);

						eText2.setVisibility(View.VISIBLE);
						eText3.setVisibility(View.VISIBLE);
						eText4.setVisibility(View.VISIBLE);
						eText5.setVisibility(View.GONE);


						btwo.setClickable(true);
						bthree.setClickable(true);
						bfour.setClickable(true);
						bfive.setClickable(true);
						bsix.setClickable(true);
						bseven.setClickable(true);
						beight.setClickable(true);
						bnine.setClickable(true);


						btwo.setTextColor((getResources().getColor(R.color.white)));
						bthree.setTextColor((getResources().getColor(R.color.white)));
						bfour.setTextColor((getResources().getColor(R.color.white)));
						bfive.setTextColor((getResources().getColor(R.color.white)));
						bsix.setTextColor((getResources().getColor(R.color.white)));
						bseven.setTextColor((getResources().getColor(R.color.white)));
						beight.setTextColor((getResources().getColor(R.color.white)));
						bnine.setTextColor((getResources().getColor(R.color.white)));


						ba.setEnabled(true);
						bb.setEnabled(true);
						bc.setEnabled(true);
						bd.setEnabled(true);
						be.setEnabled(true);
						bf.setEnabled(true);

						ba.setClickable(true);
						bb.setClickable(true);
						bc.setClickable(true);
						bd.setClickable(true);
						be.setClickable(true);
						bf.setClickable(true);

						ba.setTextColor((getResources().getColor(R.color.black)));
						bb.setTextColor((getResources().getColor(R.color.black)));
						bc.setTextColor((getResources().getColor(R.color.black)));
						bd.setTextColor((getResources().getColor(R.color.black)));
						be.setTextColor((getResources().getColor(R.color.black)));
						bf.setTextColor((getResources().getColor(R.color.black)));


						bfactorial.setClickable(false);
						bpower.setClickable(false);
						broot.setClickable(false);
						bfactorial.setTextColor((getResources().getColor(R.color.light_green)));
						bpower.setTextColor((getResources().getColor(R.color.light_green)));
						broot.setTextColor((getResources().getColor(R.color.light_green)));

						break;

				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});



	}



	void UpdateChecker() {
		final String url = "https://webandroid.000webhostapp.com/ownjokes/update_app.php";
		new Thread(new Runnable() {
			@Override
			public void run() {
				MobileAds.initialize(getApplicationContext(), "ca-app-pub-1211635675454735~1301340403");

				adView1 = (AdView) findViewById(R.id.ad_view);

				mAdView = (AdView) findViewById(R.id.ad_view);
				final AdRequest adRequest = new AdRequest.Builder()
						.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
						.build();
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						mAdView.loadAd(adRequest);
						adView1.loadAd(adRequest);
					}
				});

			}
		}).start();

		verifyInternetPermissions();
		PackageInfo pInfo = null;
		try {
			pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (PackageManager.NameNotFoundException e) {
			int verCode = pInfo.versionCode;
			e.printStackTrace();
		}

		version = pInfo.versionName;


		//Creating a string request
		StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						showJSON(response);
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						//You can handle error here if you want
					}
				}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<>();
				params.put("version", version);
				params.put("u_id", "2");


				return params;
			}
		};

		//Adding the string request to the queue
		RequestQueue requestQueue = Volley.newRequestQueue(this);
		int socketTimeout = 30000;//30 seconds - change to what you want
		RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
		stringRequest.setRetryPolicy(policy);
		requestQueue.add(stringRequest);


	}

void InterstitialAds(final Context context){
	new Thread(new Runnable() {
		@Override
		public void run() {
			final InterstitialAd mInterstitialAd;
			mInterstitialAd = new InterstitialAd(context);
			mInterstitialAd.setAdUnitId("ca-app-pub-1211635675454735/1518006401");
			final AdRequest adRequest = new AdRequest.Builder() .build();


			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					mInterstitialAd.loadAd(adRequest);
					mInterstitialAd.setAdListener(new AdListener() {
						@Override
						public void onAdLoaded() {
							if (mInterstitialAd.isLoaded()) {
								mInterstitialAd.show();
							}
						}});
				}
			});

		}
	}).start();

}
	/*public class UpdateChecker extends AsyncTask<Void,Void,String> {

		private  final String url = "https://webandroid.000webhostapp.com/ownjokes/update_app.php";





		@Override
		protected void onPreExecute() {

			super.onPreExecute();
			verifyInternetPermissions();
			PackageInfo pInfo = null;
			try {
				pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
			} catch (PackageManager.NameNotFoundException e) {
				int verCode = pInfo.versionCode;
				e.printStackTrace();
			}

			 version = pInfo.versionName;}

		@Override
		protected void onPostExecute(String s) {
			super.onPostExecute(s);
			showJSON(s);
		}

		@Override
		protected String doInBackground(Void... v) {
			HashMap<String,String> params = new HashMap<>();

			params.put("version",version);
			params.put("u_id","2");

			RequestHandler rh = new RequestHandler();
			String res = rh.sendPostRequest(url, params);

			return res;
		}
	}*/
	private void showJSON(String json) {
		String  s="",s1="";
		try {
			JSONObject jsonObject = new JSONObject(json);
			JSONArray result = jsonObject.getJSONArray("version");
			JSONArray result1 = jsonObject.getJSONArray("version_detail");

			for(int i = 0; i<result.length(); i++) {

				s = result.getString(i);
				s1 = result1.getString(i);
			}
			if (Float.parseFloat(s)>Float.parseFloat(version)) {
				update_check = true;
				new Update(this, s, s1).message();
			}
		}
		catch (Exception e){

		}

	}
	public void verifyInternetPermissions() {
		// Check if we have write permission
		int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
		String mPermission = Manifest.permission.ACCESS_NETWORK_STATE;

		if (permission != PackageManager.PERMISSION_GRANTED) {
			// We don't have permission so prompt the user
			ActivityCompat.requestPermissions(this, new String[]{mPermission},
					2);

		}
	}


	void tut_1(){
			tut_1.setVisibility(View.VISIBLE);

	}
	void tut_2(){

			tut_2.setVisibility(View.VISIBLE);


	}
	void tut_3(){

			tut_3.setVisibility(View.VISIBLE);

	}
@Override
	public void onClick(View arg0) {
		if (!update_check){
			UpdateChecker();
		}
		try {

            if (rateCount>=6)
                rateApp();
			if (eText1.getText().toString().contains("Error"))
				eText1.setText("");
			count = 0;
			bequalto.setClickable(true);


            if (getSharedPref("Vibrate",this).contains("on"))
            {
                vibrate.vibrate(30);
            }


			equal = false;
			str = (Editable) eText1.getText();
			switch (arg0.getId()) {












				case R.id.a:

					str = str.append(ba.getText());
					eText1.setText(str);
					break;

				case R.id.b:

					str = str.append(bb.getText());
					eText1.setText(str);
					break;
				case R.id.c:

					str = str.append(bc.getText());
					eText1.setText(str);
					break;
				case R.id.d:

					str = str.append(bd.getText());
					eText1.setText(str);
					break;
				case R.id.e:

					str = str.append(be.getText());
					eText1.setText(str);
					break;
				case R.id.f:

					str = str.append(bf.getText());
					eText1.setText(str);
					break;

				case R.id.openB://open bracket

					str = str.append(bopenbracket.getText());
					eText1.setText(str);

					break;
				case R.id.closedB://closed bracket
					str = str.append(bclosedbraclet.getText());
					eText1.setText(str);
					break;
				case R.id.factorial:

					s = eText1.getText().toString().trim();
					int count1 = 0;
					for (int i1 = s.length() - 1; i1 >= 0; --i1) {
						System.out.println("i=" + s.charAt(i1));

						if (s.charAt(i1) == '√' || s.charAt(i1) == '.' || s.charAt(i1) == '!') {
							count1 = 0;
							break;
						} else if (i1 == 0 || s.charAt(i1) == '+' || s.charAt(i1) == '-' || s.charAt(i1) == '*' || s.charAt(i1) == '/' || s.charAt(i1) == '^') {
							count1 = 1;

							break;
						}

					}
					if (count1 == 1)
						eText1.append(bfactorial.getText());
					break;
				case R.id.Root:

					s = eText1.getText().toString();
					if (s.length() == 0)
						eText1.setText(str.append(broot.getText()));
					else if (s.endsWith("+") || s.endsWith("-") || s.endsWith("*") || s.endsWith("/") || s.endsWith("^") || s.endsWith("(")) {
						{
							i1 = s.length();
							char ch = 0;

							do {
								if (i1 == s.length()) {
									ch = s.charAt(i1 - 1);
								} else
									ch = s.charAt(i1);

								if ((ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '^' || ch == '(') && ch != '√') {
									eText1.setText(str.append(broot.getText()));

									break;
								}
								i1 = i1 - 1;

							}
							while (ch == '^' || ch == '+' || ch == '-' || ch == '*' || ch == '/'|| ch == '^' || i1 == 0 && ch != '√');
						}

					}

					break;
				case R.id.power:

					s = eText1.getText().toString().trim();
					if (s.trim().length()!=0 && !s.endsWith("+") && !s.endsWith("-") && !s.endsWith("*") && !s.endsWith("/") && !s.endsWith("^") && !s.endsWith("!") && !s.endsWith("√")) {
						eText1.setText(str.append(bpower.getText()));
					}


					break;
				case R.id.help:
					AlertDialog.Builder builder=new AlertDialog.Builder(this);
					LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					final View dialogView= inflater.inflate(R.layout.more_activity, null);
					builder.setView(dialogView);

					final Button share,history, help,vibrate;
					TextView textView;
					String s="";
					SharedPreferences sharedPreferences;
					InterstitialAds(dialogView.getContext());

					help=(Button) dialogView.findViewById(R.id.help);
					vibrate=(Button) dialogView.findViewById(R.id.Button04);
					share=(Button) dialogView.findViewById(R.id.share);
					history=(Button) dialogView.findViewById(R.id.history);

					PackageInfo pInfo = null;
					textView=(TextView) dialogView.findViewById(R.id.textView1);
					try {
						pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
					} catch (PackageManager.NameNotFoundException e) {
						int verCode = pInfo.versionCode;
						e.printStackTrace();
					}

					String version = pInfo.versionName;

					textView.setText(getApplicationName(this)+" v"+version+"\n" +
							"\n" +
							"Developer\n" +
							"\"VR apps\"");

					help.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View view) {

							switch (view.getId())
							{

								case R.id.help:
									if (tut_3.isShown()){
										tut_3.setVisibility(View.GONE);
									}
									changeSharedPref("tutorial", "", StartActivity.this);
									if (drawer.isDrawerOpen(GravityCompat.END)) {
										drawer.closeDrawer(GravityCompat.END);
									}
									alertDialog.dismiss();
									tut_1();
									break;



							}

						}
					});
					vibrate.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View view) {
							switch (view.getId())
							{
								case R.id.Button04://vibbration button
									if (new StartActivity().getSharedPref("Vibrate",dialogView.getContext()).contains("on"))
									{
										changeSharedPref("Vibrate","off",dialogView.getContext());
									}
									else 	if (new StartActivity().getSharedPref("Vibrate",dialogView.getContext()).contains("off"))
									{
										changeSharedPref("Vibrate","on",dialogView.getContext());
									}
									changeVibrateStatus(vibrate);

									break;
							}
						}
					});

					history.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View view) {

							switch (view.getId()) {
								case R.id.history:
									alertDialog.dismiss();
									AlertDialog.Builder builder=new AlertDialog.Builder(StartActivity.this);
									LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
									final View dialogView= inflater.inflate(R.layout.activity_history, null);
									builder.setView(dialogView);

									alertDialog = builder.setCancelable(true).create();
									alertDialog.show();
									InterstitialAds(view.getContext());
									new History(dialogView,alertDialog);
									break;
							}
						}
					});
					share.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View view) {

							switch (view.getId()) {
								case R.id.share:
									Intent intent=new Intent();
									intent.setAction(Intent.ACTION_SEND);
									intent.putExtra(Intent.EXTRA_TEXT,"Download best number system converter and calculator two in one app \nhttps://play.google.com/store/apps/details?id=com.c.nccalculator");
									intent.setType("text/plain");
									startActivity(intent);
									break;
							}
						}
					});


					changeVibrateStatus(vibrate);

					alertDialog = builder.setCancelable(true).create();
					alertDialog.show();

					break;


















				case R.id.dot:

					s = eText1.getText().toString().trim();
					int count = 0, count2 = 1;
					if (s.length() == 0)
						count2 = 0;

					for (int i1 = s.length() - 1; i1 >= 0; --i1) {
						System.out.println("i=" + s.charAt(i1));

						if (s.charAt(i1) == '.') {
							count = 0;
							break;
						} else if (i1 == 0 || s.charAt(i1) == '+' || s.charAt(i1) == '-' || s.charAt(i1) == '*' || s.charAt(i1) == '/' || s.charAt(i1) == '^') {
							count = 1;
							if (i1 == s.length() - 1 && (s.charAt(i1) == '√' || s.charAt(i1) == '+' || s.charAt(i1) == '-' || s.charAt(i1) == '*' || s.charAt(i1) == '/' || s.charAt(i1) == '^'))
								count2 = 0;
							break;
						}

					}
					if (count == 1 && count2 == 1)
						eText1.append(bdot.getText());
					if (count2 == 0)
						eText1.append("0" + bdot.getText());
					break;
				case R.id.zero:

					str = str.append(bzero.getText());
					eText1.setText(str);
					break;
				case R.id.one:

					str = str.append(bone.getText());
					eText1.setText(str);
					break;
				case R.id.two:

					str = str.append(btwo.getText());
					eText1.setText(str);

					break;
				case R.id.three:

					str = str.append(bthree.getText());
					eText1.setText(str);
					break;
				case R.id.four:

					str = str.append(bfour.getText());
					eText1.setText(str);
					break;
				case R.id.five:

					str = str.append(bfive.getText());
					eText1.setText(str);
					break;
				case R.id.six:

					str = str.append(bsix.getText());
					eText1.setText(str);
					break;
				case R.id.seven:

					str = str.append(bseven.getText());
					eText1.setText(str);
					break;
				case R.id.eight:

					str = str.append(beight.getText());
					eText1.setText(str);
					break;
				case R.id.nine:

					str = str.append(bnine.getText());
					eText1.setText(str);
					break;

				case R.id.cancel:

                  //  changeSharedPref("TempStore","",this);
					s = eText1.getText().toString();
					if (s.length() != 0) {
						StringBuilder strbul = new StringBuilder(80);
						strbul.append(s);
						s = strbul.deleteCharAt(strbul.length() - 1).toString();
						eText1.setText(s);
					}
					break;
				case R.id.plus:

					s = eText1.getText().toString();
					if (s.trim().length() != 0) {
						if (!s.endsWith("*") && (!s.endsWith("+") && !s.endsWith("-") && !s.endsWith("/") && !s.endsWith("^") && !s.endsWith("√")))
							eText1.setText(str.append(bplus.getText()));
					}


					break;
				case R.id.minus:

					s = eText1.getText().toString().trim();
					if (s.length() == 0 && spinindex == 0) {
						eText1.setText(str.append(bminus.getText()));
					} else if (s.trim().length() != 0) {
						if (!s.endsWith("-") || (s.endsWith("+") || s.endsWith("*") || s.endsWith("/") || s.endsWith("^") || s.endsWith("√")))

							eText1.setText(str.append(bminus.getText()));
					}

					break;
				case R.id.multiplication:

					s = eText1.getText().toString();
					if (s.trim().length() != 0) {
						if (!s.endsWith("*") && (!s.endsWith("+") && !s.endsWith("-") && !s.endsWith("/") && !s.endsWith("^") && !s.endsWith("√")))
							eText1.setText(str.append(bmultiplication.getText()));
					}

					break;
				case R.id.divide:

					s = eText1.getText().toString();
					if (s.trim().length() != 0) {
						if (!s.endsWith("/") && (!s.endsWith("+") && !s.endsWith("-") && !s.endsWith("*") && !s.endsWith("^") && !s.endsWith("√")))
							eText1.setText(str.append(bdivide.getText()));
					}

					break;


				case R.id.equalto:
					++rateCount;
						bequalto.setClickable(false);
					bequalto.setClickable(false);
					s = eText1.getText().toString();
					int count4 = 0;
					double temp = 0.0;


					NumSysCalculation n = new NumSysCalculation(this);
					Calculation c = new Calculation();
					if (spinindex == 0) {
					new SeperateTask(StartActivity.this,eText1.getText().toString()).execute(eText1.getText().toString());

						if (s.contains("-")) {
							count4 = s.length() - s.replace("-", "").length();
						}
						if (s.contains("√") && !s.contains("(") && !s.contains(")") && !s.contains("^") && !s.contains("/") && !s.contains("*") && !s.contains("+") && (s.charAt(0) == '-' || count4 <= 1)) {
							temp = c.sqroot(s);
							c.output(String.valueOf(temp));
						} else if (s.contains("!") && !s.contains("(") && !s.contains(")") && !s.contains("^") && !s.contains("/") && !s.contains("*") && !s.contains("+") && (s.charAt(0) == '-' || count4 <= 1)) {
							long fact;
							fact = c.facto(s);
							c.output(String.valueOf(fact));
						} else if (s.contains("(") || s.contains(")") || s.contains("^") || s.contains("/") || s.contains("*") || s.contains("+") || s.contains("-") )
							c.parser(s);

                        new SeperateTask(StartActivity.this,eText1.getText().toString()).execute(eText1.getText().toString());

                    } else if (spinindex != 2) {

                        new SeperateTask(StartActivity.this,eText1.getText().toString()).execute(eText1.getText().toString());
                        new SeperateTask(StartActivity.this,eText2.getText().toString()).execute(eText1.getText().toString());
                        new SeperateTask(StartActivity.this,eText3.getText().toString()).execute(eText1.getText().toString());
                        new SeperateTask(StartActivity.this,eText4.getText().toString()).execute(eText1.getText().toString());
                        new SeperateTask(StartActivity.this,eText5.getText().toString()).execute(eText1.getText().toString());


                        NumSysCon numSysCon = new NumSysCon();
						s = eText3.getText().toString();
						c.parser(s);
						s = eText3.getText().toString();
						textlistener(s);
						if (spinindex == 1)
							eText1.setText(numSysCon.decto(s, 2));
						if (spinindex == 3)
							eText1.setText(numSysCon.decto(s, 8));
						if (spinindex == 4)
							eText1.setText(numSysCon.decto(s, 16));
                        new SeperateTask(StartActivity.this,eText1.getText().toString()).execute(eText1.getText().toString());
                        new SeperateTask(StartActivity.this,eText2.getText().toString()).execute(eText1.getText().toString());
                        new SeperateTask(StartActivity.this,eText3.getText().toString()).execute(eText1.getText().toString());
                        new SeperateTask(StartActivity.this,eText4.getText().toString()).execute(eText1.getText().toString());
                        new SeperateTask(StartActivity.this,eText5.getText().toString()).execute(eText1.getText().toString());

                    } else if (spinindex == 2) {
                        new SeperateTask(StartActivity.this,eText1.getText().toString()).execute(eText1.getText().toString());
                        new SeperateTask(StartActivity.this,eText2.getText().toString()).execute(eText1.getText().toString());

                        new SeperateTask(StartActivity.this,eText4.getText().toString()).execute(eText1.getText().toString());
                        new SeperateTask(StartActivity.this,eText5.getText().toString()).execute(eText1.getText().toString());

                        c.parser(s);
						s = eText1.getText().toString();
						textlistener(s);
                        new SeperateTask(StartActivity.this,eText1.getText().toString()).execute(eText1.getText().toString());
                        new SeperateTask(StartActivity.this,eText2.getText().toString()).execute(eText1.getText().toString());

                        new SeperateTask(StartActivity.this,eText4.getText().toString()).execute(eText1.getText().toString());
                        new SeperateTask(StartActivity.this,eText5.getText().toString()).execute(eText1.getText().toString());

                    }

					break;

				case R.id.tut_1_btn:
					tut_1.setVisibility(View.GONE);
					tut_2();
					break;
				case R.id.tut_2_btn:

					tut_2.setVisibility(View.GONE);

					break;
				case R.id.tut_3_btn:

					tut_3.setVisibility(View.GONE);
					changeSharedPref("tutorial", "1", this);

					break;
			}
			s = eText1.getText().toString().trim();
			if (spinindex != 0) {
				if (s.endsWith(".") || s.endsWith("-")) {

					if (eText1.getText().toString().endsWith("-")) {
						if (eText1.getText().toString().endsWith(".")) {
							eText2.setText(eText2.getText().toString() + String.valueOf(s.charAt(s.length() - 1)));
							eText3.setText(eText3.getText().toString() + String.valueOf(s.charAt(s.length() - 1)));
							eText4.setText(eText4.getText().toString() + String.valueOf(s.charAt(s.length() - 1)));
							eText5.setText(eText5.getText().toString() + String.valueOf(s.charAt(s.length() - 1)));
						}
					}
				}
				if (equal == false && !s.endsWith(".") && !s.endsWith("-")) {

					textlistener(s);
				}

			}
		} catch (Exception e) {
			eText1.setText("Error");
			Log.i("error", "", e);
		}
	}
	public static String getApplicationName(Context context) {
		ApplicationInfo applicationInfo = context.getApplicationInfo();
		int stringId = applicationInfo.labelRes;
		return stringId == 0 ? applicationInfo.nonLocalizedLabel.toString() : context.getString(stringId);
	}
	void changeVibrateStatus(Button vibrate)
	{
		if (getSharedPref("Vibrate",this).contains("on"))
		{
			vibrate.setText("Vibrate ON");
			Drawable img = getResources().getDrawable( android.R.drawable.ic_lock_silent_mode_off );
			vibrate.setCompoundDrawablesWithIntrinsicBounds( null, null, img, null);
		}
		else 	if (getSharedPref("Vibrate",this).contains("off"))
		{
			vibrate.setText("Vibrate OFF");
			Drawable img = getResources().getDrawable( android.R.drawable.ic_lock_silent_mode );
			vibrate.setCompoundDrawablesWithIntrinsicBounds( null, null, img, null);

		}
	}


	private void textlistener(String s) {
		NumSysCalculation numSysCalculation = new NumSysCalculation(this);

		numSysCalculation.nsParser(s, spinindex);

	}
    String  getSharedPref(String key,Context context)
    {
        try {
            key=new SharedTask(key,null,shared_pref,context).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return key;
    }
void changeSharedPref(String key,String value,Context context)
{
 new SharedTask(key,value,shared_pref,context).execute();
}


    private void rateApp()
    {
		if (getSharedPref("Rate",this).contains("no")) {
			rateCount = 0;
			String rate[] = { "Ok,Let's rate it","Later", "Already done"};
			count = 0;
			final AlertDialog.Builder builder = new AlertDialog.Builder(StartActivity.this);
			builder.setTitle("Love this app!! Let's rate it :)");
			builder.setCancelable(false);
			builder.setItems(rate, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i) {
					switch (i) {
						case 0:
							startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.c.nccalculator")));

								break;
						case 1:
							dialogInterface.cancel();


							break;
						case 2:

							changeSharedPref("Rate","yes", StartActivity.this);

							break;
					}
				}
			});

			builder.create().show();
		}
    }



	@Override
	public void onBackPressed() {

		if (drawer.isDrawerOpen(GravityCompat.END)) {
			drawer.closeDrawer(GravityCompat.END);
			count=0;
		}else {
			count++;
			if (count == 2)

				finish();
			else if (count == 1)
				Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();

		}
	}




	@Override
	public void onDrawerSlide(View drawerView, float slideOffset) {

	}

	@Override
	public void onDrawerOpened(View drawerView) {
		if (drawer.isDrawerOpen(GravityCompat.END) && getSharedPref("tutorial", this).equals("") && !tut_2.isShown()){
			tut_3();
		}
	}

	@Override
	public void onDrawerClosed(View drawerView) {

	}

	@Override
	public void onDrawerStateChanged(int newState) {

	}
}