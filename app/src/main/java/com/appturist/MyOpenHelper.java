package com.appturist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by suksun on 9/20/2016 AD.
 */


public class MyOpenHelper extends SQLiteOpenHelper {
    //Explicit
    public static final String database_name = "dbturist1.db";
    private static final int database_version = 4;
    private static final String create_user_table = "create table tbt_turist (" +
            "tid integer primary key, " +
            "tname text, " +
            "taddress text, " +
            "tphone text, " +
            "tlatitude text, " +
            "tlongtitude text, " +
            "tpicture text, " +
            "tdistrict text);";

    private static final String create_order_table = "create table orderTABLE (" +
            "_id integer primary key," +
            "ID_User text," +
            "Date text," +
            "Sent_To text," +
            "Product text," +
            "Price text," +
            "Piece text," +
            "Total text);";

    private static final String create_menu_table = "create table tbt_menu (" +
            "mid integer primary key," +
            "tid text," +
            "mname text," +
            "mdetail text," +
            "mpicture text);";


    /*

CREATE TABLE `tbt_turist` (
`tid` int(5) NOT NULL,
  `tname` varchar(100) CHARACTER SET tis620 NOT NULL,
  `taddress` text CHARACTER SET tis620 NOT NULL,
  `tphone` varchar(10) CHARACTER SET tis620 NOT NULL,
  `tlatitude` int(20) NOT NULL,
  `tlongtitude` int(20) NOT NULL,
  `tpicture` varchar(100) CHARACTER SET tis620 NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbt_turist`
--

INSERT INTO `tbt_turist` (`tid`, `tname`, `taddress`, `tphone`, `tlatitude`, `tlongtitude`, `tpicture`) VALUES
(1, 'test1', 'testsfts', '0850089898', 16, 104, 'http://www.suksun.biz/picture/invensale.jpg');

    */



    public MyOpenHelper(Context context){
        super(context, database_name, null, database_version);
    }//Constructor

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL(create_menu_table);
        sqLiteDatabase.execSQL(create_user_table);

        sqLiteDatabase.execSQL(create_order_table);
       Log.i("create_user_table",create_user_table);
        Log.i("create_menu_table",create_menu_table);
        Log.i("create_menu_table",create_order_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1){

    }
} //Main Class