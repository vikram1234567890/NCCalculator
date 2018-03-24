package com.c.nccalculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;

public class History extends AppCompatActivity implements OnItemClickListener,OnMenuItemClickListener
{
    ListView lView;
    Adapter adapter;
    private ArrayList<String> arrayList;
     InterstitialAd mInterstitialAd;
    static String store="";
   SharedPreferences sharedPreferences;
    ArrayList<String> temp=new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mInterstitialAd = new InterstitialAd(History.this);
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


        lView=(ListView) findViewById(R.id.listView1);
        lView.setOnItemClickListener(this);
        arrayList=new ArrayList<String>();
        DBhistory db=new DBhistory(this);
        arrayList=db.getAllHistory();
        db.close();

        for(int i=arrayList.size()-1;i>=0;i--){
            temp.add(arrayList.get(i));
        }
        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, temp);
        lView.setAdapter((ListAdapter) adapter);

    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        new StartActivity().changeSharedPref("TempStore","",this);
        Intent intent=new Intent(getApplicationContext(),StartActivity.class);

        store=temp.get(position);
        intent.putExtra("H",store);
        startActivity(intent);
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        menu.add("Clear History").setOnMenuItemClickListener(History.this).setTitle("Clear History").setIcon(android.R.drawable.ic_menu_delete).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add("").setOnMenuItemClickListener(this).setTitle("Help").setIcon(android.R.drawable.ic_menu_help).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return true;

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if(item.getTitle()=="Clear History")
        {

            arrayList.clear();
            StartActivity.otherList=new String[30];
            DBhistory db=new DBhistory(History.this);

                db.deleteAllHistory();
            db.close();
            arrayList.clear();
            adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
            // adapter=new ArrayAdapter<String>(History.this, android.R.layout.simple_list_item_1, arrayList) ;
            lView.setAdapter((ListAdapter) adapter);
            Toast.makeText(getApplicationContext(), "History cleared", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (item.getTitle()=="Help")
        {
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("Help").setMessage("Click on any number to copy it to input box").create().show();
        }

        return false;
    }

    @Override
    public void onBackPressed() {
    Intent intent=new Intent(History.this,StartActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public boolean onSupportNavigateUp(){
        Intent intent=new Intent(History.this,StartActivity.class);
        startActivity(intent);
        finish();
        return true;
    }
}
