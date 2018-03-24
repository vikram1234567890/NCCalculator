package com.c.nccalculator;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static int progress;
    ProgressBar progressBar;
    Handler handler = new Handler();
TextView textView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView1=(TextView) findViewById(R.id.textView1);
        PackageInfo pInfo = null;

        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            int verCode = pInfo.versionCode;
            e.printStackTrace();
        }

        String version = pInfo.versionName;
        textView1.setText("NCCalculator v"+version);
        getResources().getConfiguration();


        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        //simulate the working of the progress bar using a thread which sleeps for sometime

        new Thread(new Runnable()
        {

            public void run()
            {
                while(progress<10)
                {
                    try
                    {
                        Thread.sleep(100);

                    }
                    catch(InterruptedException e)
                    {

                    }
                    progress++;
                }

                handler.post(new Runnable()
                {
                    public void run()
                    {

                        Intent startActivity = new Intent(MainActivity.this,StartActivity.class);
                        startActivity(startActivity);
                        finish();


                    }
                });
            }


        }).start();


    }
}
