package com.c.nccalculator;

import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;


public class History implements OnItemClickListener {
    ListView lView;
    Adapter adapter;
    private ArrayList<String> arrayList;

    static String store="";
    AlertDialog alertDialog;
    View view;
    ImageView imageView;
    ArrayList<String> temp=new ArrayList<>();
    public   History(View view, final AlertDialog alertDialog) {
        this.alertDialog=alertDialog;
        this.view=view;




        lView=(ListView) view.findViewById(R.id.listView1);
        lView.setOnItemClickListener(this);
        arrayList=new ArrayList<String>();
        DBhistory db=new DBhistory(view.getContext());
        arrayList=db.getAllHistory();
        db.close();

        for(int i=arrayList.size()-1;i>=0;i--){
            temp.add(arrayList.get(i));
        }
        if (arrayList.size()==0){
            FrameLayout frameLayout= (FrameLayout) view.findViewById(R.id.no_history);
            frameLayout.setVisibility(View.VISIBLE);
        }
        adapter=new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, temp);
        lView.setAdapter((ListAdapter) adapter);

        imageView= (ImageView) view.findViewById(R.id.delete);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayList.clear();
                StartActivity.otherList=new String[30];
                DBhistory db=new DBhistory(view.getContext());

                db.deleteAllHistory();
                db.close();
                arrayList.clear();
                adapter=new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, arrayList);
                // adapter=new ArrayAdapter<String>(History.this, android.R.layout.simple_list_item_1, arrayList) ;
                lView.setAdapter((ListAdapter) adapter);
                Toast.makeText(view.getContext(), "History cleared", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        new StartActivity().changeSharedPref("TempStore","",view.getContext());

        store=temp.get(position);
        StartActivity.eText1.setText(store);
        alertDialog.dismiss();
    }


}
