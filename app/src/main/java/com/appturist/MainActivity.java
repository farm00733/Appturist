package com.appturist;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Animation fade_in, fade_out;
    ViewFlipper viewFlipper;
    ListView listview;
    String strtname;
    private MySQLite mySQLite;
    public MyOpenHelper myOpenHelper;
    private SQLiteDatabase sqLiteDatabase;
    public Spinner spn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spn = (Spinner) findViewById(R.id.spinner);
        viewFlipper = (ViewFlipper) this.findViewById(R.id.bckgrndViewFlipper1);

        fade_in = AnimationUtils.loadAnimation(this,
                android.R.anim.fade_in);
        fade_out = AnimationUtils.loadAnimation(this,
                android.R.anim.fade_out);

        viewFlipper.setInAnimation(fade_in);
        viewFlipper.setOutAnimation(fade_out);

        //sets auto flipping
        viewFlipper.setAutoStart(true);
        viewFlipper.setFlipInterval(3500);
        viewFlipper.startFlipping();


        ArrayAdapter<String> ada;
        List<String> list;
        list = new ArrayList<String>();
        list.add("กาฬสินธุ์");
        list.add("สมเด็จ");
        list.add("ดอนจาน");
        list.add("กมลาไสย");
        list.add("หนองกุงศรี");
        list.add("ฆ้องชัย");
        list.add("ยางตลาด");
        list.add("คำม่วง");
        list.add("สามชัย");
        list.add("ห้วยเม็ก");
        list.add("ท่าคันโท");
        list.add("นามน");
        list.add("ร่องคำ");
        list.add("ห้วยผึ้ง");
        list.add("เขาวง");
        list.add("กุฉินารายณ์");
        list.add("สหัสขันธ์");
        list.add("นาคู");
        ada = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, list);
        ada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(ada);

        //Bind widget
        final TextView txtsearch = (TextView) findViewById(R.id.txtsearch);
        strtname = txtsearch.getText().toString().trim();

        listview = (ListView) findViewById(R.id.listview1);


//Request SQLite
        mySQLite = new MySQLite(this);
        synAndDelete();

        final Button btn1 = (Button) findViewById(R.id.button1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchturist();

            }
        });


    }

    private void synAndDelete() {
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        sqLiteDatabase.delete(MySQLite.user_table, null, null);
        sqLiteDatabase.delete(MySQLite.menu_mtable, null, null);

        SynJSON mySynJSON = new SynJSON();
        mySynJSON.execute();

    } //synAndDelete


    private void searchturist() {


        EditText txtsearch2 = (EditText) findViewById(R.id.txtsearch);
        strtname = txtsearch2.getText().toString().trim();
        Toast.makeText(getApplicationContext(), spn.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
        final SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        final Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM tbt_turist WHERE tname  like " + "'%" +
                strtname + "%'", null);

        //  final Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM tbt_turist WHERE tname  like " + "'%" +
        //     strtname + "%' or tdistrict like " + "'%"+ spn.getSelectedItem().toString() +"%' ", null);


        Log.d("strtname", strtname);
        cursor.moveToFirst();


        int count = cursor.getCount();
        final String[] tid = new String[count];
        final String[] tname = new String[count];
        final String[] taddress = new String[count];
        final String[] tphone = new String[count];
        final String[] tlatitude = new String[count];
        final String[] tlongtitude = new String[count];
        final String[] tpicture = new String[count];
        final String[] tdistrict = new String[count];

        for (int i = 0; i < count; ) {
            tid[i] = cursor.getString(cursor.getColumnIndex(MySQLite.column_tid));
            tname[i] = cursor.getString(cursor.getColumnIndex(MySQLite.column_tname));
            taddress[i] = cursor.getString(cursor.getColumnIndex(MySQLite.column_taddress));
            tphone[i] = cursor.getString(cursor.getColumnIndex(MySQLite.column_tphone));
            tlatitude[i] = cursor.getString(cursor.getColumnIndex(MySQLite.column_tlatitude));
            tlongtitude[i] = cursor.getString(cursor.getColumnIndex(MySQLite.column_tlongtitude));
            tpicture[i] = cursor.getString(cursor.getColumnIndex(MySQLite.column_tpicture));
            tdistrict[i] = cursor.getString(cursor.getColumnIndex(MySQLite.column_tdistrict));

            i++;

            cursor.moveToNext();


        }

        cursor.close();


        TuristAdapter turistAdapter = new TuristAdapter(MainActivity.this, tpicture, tname);
        listview.setAdapter(turistAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent gotomenu = new Intent(MainActivity.this, MenuActivity.class);
                gotomenu.putExtra("ttid", tid[position]);
                gotomenu.putExtra("tname", tname[position]);
                gotomenu.putExtra("tpicture", tpicture[position]);
                gotomenu.putExtra("taddress", taddress[position]);
                startActivity(gotomenu);
                //        Log.d("tid tname tpicture",tid.toString() + " " + tname.toString() + " " + tpicture.toString());
                Toast.makeText(getApplication(), tid[position].toString() + " " + tname[position].toString() + " " + tpicture[position].toString() + " " + taddress[position].toString(), Toast.LENGTH_LONG).show();
            }
        });


/*
        final AlertDialog.Builder imageDialog = new AlertDialog.Builder(MainActivity.this);

        final LayoutInflater inflater = (LayoutInflater)MainActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);


        // OnClick
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                View layout = inflater.inflate(R.layout.customturist_fullimage_dialog,
                        (ViewGroup) findViewById(R.id.layout_root));
                ImageView image = (ImageView) layout.findViewById(R.id.fullimage2);


                Picasso.with(getApplicationContext()).load(tpictureStrings[position]).resize(100, 140).into(image);
                //fill value to chk


                try {

                    Picasso.with(getApplicationContext()).load(tpictureStrings[position]).resize(100, 140).into(image);


                    //  image.setImageBitmap(loadBitmap(jsonArray.get(position)("iconStrings")));
                } catch (Exception e) {
                    // When Error
                    image.setImageResource(android.R.drawable.ic_menu_report_image);
                }


                JSONObject jsonObject = null;
                try {
                    jsonObject = jsonArray.getJSONObject(position);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    tnameStrings[position] = jsonObject.getString("tname");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                imageDialog.setIcon(android.R.drawable.btn_star_big_on);
                try {
                    imageDialog.setTitle("View : " + jsonObject.getString("tname"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                imageDialog.setView(layout);
                imageDialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {


                        dialog.dismiss();
                    }

                });

                imageDialog.create();
                imageDialog.show();

            }
        });

        */
    }


    private class SynJSON extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                String strURL = "http://www.zaabkalasin.com/turist/php_get_turist.php";
                // String strURL = "http://10.0.2.2/turist/php_get_turist.php";
                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(strURL).build();

                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();


            } catch (Exception e) {
                Log.d("testwitthaya", "do test " + e.toString());
                return null;
            }
        }


        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                Log.d("witthaya ", "respon ==>" + s);
                final JSONArray jsonArray = new JSONArray(s);


                final String[] tnameStrings = new String[jsonArray.length()];
                final String[] taddressStrings = new String[jsonArray.length()];
                final String[] tphoneStrings = new String[jsonArray.length()];
                final String[] tlatitudeStrings = new String[jsonArray.length()];
                final String[] tlongtitudeStrings = new String[jsonArray.length()];
                final String[] tpictureStrings = new String[jsonArray.length()];
                final String[] tdistrictStrings = new String[jsonArray.length()];

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    tnameStrings[i] = jsonObject.getString("tname");
                    taddressStrings[i] = jsonObject.getString("taddress");
                    tphoneStrings[i] = jsonObject.getString("tphone");
                    tlatitudeStrings[i] = jsonObject.getString("tlatitude");
                    tlongtitudeStrings[i] = jsonObject.getString("tlongtitude");
                    tpictureStrings[i] = jsonObject.getString("tpicture");
                    tdistrictStrings[i] = jsonObject.getString("tdistrict");

                    mySQLite.addNewturist(tnameStrings[i], taddressStrings[i], tphoneStrings[i], tlatitudeStrings[i],
                            tlongtitudeStrings[i], tpictureStrings[i], tdistrictStrings[i]);
                }




/*


    public long addNewturist(String strtname,
                           String strtaddress,
                           String strtphone,
                           String strtlatitude,
                           String strtlongtitude,
                           String strtpicture) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(column_tname, strtname);
        contentValues.put(column_taddress, strtaddress);
        contentValues.put(column_tphone,strtphone);
        contentValues.put(column_tlatitude,strtlatitude);
        contentValues.put(column_tlongtitude,strtlongtitude);
        contentValues.put(column_tpicture,strtpicture);
*/


            } catch (JSONException e) {
                //  e.printStackTrace();
                Log.d("witthaya", "onpost==>" + e.toString());
            }

        }

    }


}
