package com.c.nccalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;


public class DBhistory extends SQLiteOpenHelper  {



        public static final String DATABASE_NAME = "NCChistory.db";
        public static final String HISTORY_TABLE_NAME = "NCChistory";

        public static final String HISTORY_COLUMN_HISTORY = "previous_data";
        public DBhistory(Context context) {
            super(context, DATABASE_NAME, null, 1);
        }



    @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub
            db.execSQL(
                    "create table NCChistory " +
                            "(previous_data)"
            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
            db.execSQL("DROP TABLE IF EXISTS NCChistory");
            onCreate(db);
        }

        public boolean insertHistory(String previous_data) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
   
            contentValues.put("previous_data", previous_data);
      
            db.insert("NCChistory", null, contentValues);

            return true;
        }


        public boolean deleteAllHistory() {

            SQLiteDatabase db = this.getWritableDatabase();
           db.execSQL("delete from "+ HISTORY_TABLE_NAME);
            return true;
        }

        public ArrayList<String> getAllHistory() {
            ArrayList<String> array_list = new ArrayList<String>();

            //hp = new HashMap();
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res = db.rawQuery("select * from NCChistory", null);
            res.moveToFirst();

            while (res.isAfterLast() == false) {
                array_list.add(res.getString(res.getColumnIndex(HISTORY_COLUMN_HISTORY)));
                res.moveToNext();
            }
            return array_list;
        }


}

