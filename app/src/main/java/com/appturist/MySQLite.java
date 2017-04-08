package com.appturist;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by suksun on 9/20/2016 AD.
 */

public class MySQLite {
    public MyOpenHelper myOpenHelper;
    private SQLiteDatabase sqLiteDatabase;

    public static final String user_table = "tbt_turist";
    public static final String column_tid = "tid";
    public static final String column_tname = "tname";
    public static final String column_taddress = "taddress";
    public static final String column_tphone = "tphone";
    public static final String column_tlatitude = "tlatitude";
    public static final String column_tlongtitude = "tlongtitude";
    public static final String column_tpicture = "tpicture";
    public static final String column_tdistrict = "tdistrict";


    public static final String menu_mtable = "tbt_menu";
    public static final String column_ttid = "tid";
    public static final String column_mid = "mid";
    public static final String column_mname = "mname";
    public static final String column_mdetail = "mdetail";
    public static final String column_mpicture = "mpicture";





    public MySQLite(Context context){
        myOpenHelper = new MyOpenHelper(context);
        sqLiteDatabase = myOpenHelper.getWritableDatabase();
    }// Constructor



    public long addNewturist(String strtname,
                           String strtaddress,
                           String strtphone,
                           String strtlatitude,
                           String strtlongtitude,
                           String strtpicture,
                             String strtdistrict){
        ContentValues contentValues = new ContentValues();
        contentValues.put(column_tname, strtname);
        contentValues.put(column_taddress, strtaddress);
        contentValues.put(column_tphone,strtphone);
        contentValues.put(column_tlatitude,strtlatitude);
        contentValues.put(column_tlongtitude,strtlongtitude);
        contentValues.put(column_tpicture,strtpicture);
        contentValues.put(column_tdistrict,strtdistrict);
        return sqLiteDatabase.insert(user_table, null, contentValues);
    }

    public long addNewmenu(String strttid,
                             String strmname,
                             String strmdetail,
                             String strmpicture) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(column_ttid, strttid);
        contentValues.put(column_mname, strmname);
        contentValues.put(column_mdetail,strmdetail);
        contentValues.put(column_mpicture,strmpicture);
        return sqLiteDatabase.insert(menu_mtable, null, contentValues);
    }
}
