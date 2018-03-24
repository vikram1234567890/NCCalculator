package com.c.nccalculator;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

/**
 * Created by pc on 3/9/2017.
 */

public class SharedTask extends AsyncTask<Void,Void,String>
{
    String key, value,shared_pref;
    SharedPreferences sharedPreferences;
    Context context;
    public SharedTask(String key,String value,String shared_pref,  Context context)
    {
        this.key=key;
        this.value=value;
        this.shared_pref=shared_pref;
        this.context=context;
    }
    @Override
    protected String doInBackground(Void... strings) {
        if (value!=null)
            changeSharedPref(key,value,context);
        return getSharedPref(key,context);
    }
    private String  getSharedPref(String key,Context context)
    {
        sharedPreferences = context.getSharedPreferences(shared_pref, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key,"");
    }
    private void changeSharedPref(String key,String value,Context context)
    {
        sharedPreferences = context.getSharedPreferences(shared_pref,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, "");
        editor.putString(key, value);
        editor.commit();
    }


}