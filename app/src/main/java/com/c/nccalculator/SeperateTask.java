package com.c.nccalculator;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.speech.tts.Voice;
import android.support.annotation.IntegerRes;

/**
 * Created by pc on 3/8/2017.
 */

public class SeperateTask extends AsyncTask<String ,Void,Void  > {
    Context context;
    String s;

    public SeperateTask(Context context,String s)
    {

        this.context=context;
        this.s=s;
    }
    @Override
    protected Void doInBackground(String... strings) {
        DBhistory dBhistory=new DBhistory(context);
        dBhistory.insertHistory(s);
        dBhistory.close();
        return null;
    }

}
