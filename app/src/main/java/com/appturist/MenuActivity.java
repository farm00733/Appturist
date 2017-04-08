package com.appturist;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MenuActivity extends AppCompatActivity {
    private MySQLite mySQLite;
    public MyOpenHelper myOpenHelper;
    private SQLiteDatabase sqLiteDatabase;
    private String titleString, firstString, secondString, thirdString, detailString,
            latString, lngString, iconString;
    private String ttid;
    private String tname, taddress;

    String tpicture;
    TextView txttname;
    ImageView imglogo;
    ListView listviewmenu;

    private String idString;    // id ==> tid on tbt_turist


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //Get ID from Intent
        getIDFromIntent();


        tname = getIntent().getStringExtra("tname");
        txttname = (TextView) findViewById(R.id.txttname);
        txttname.setText(tname);
        imglogo = (ImageView) findViewById(R.id.imglogo);
        tpicture = getIntent().getStringExtra("tpicture");
        TextView txtaddress = (TextView) findViewById(R.id.txtaddress);
        taddress = getIntent().getStringExtra("taddress");
        txtaddress.setText(taddress);

       /* TextView txttname = (TextView)findViewById(R.id.textView4);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/THSarabun.ttf");

        txttname.setTypeface(custom_font); */

        Picasso.with(getApplicationContext()).load(tpicture).resize(350, 440).into(imglogo);
        //fill value to chk


        try {

            Picasso.with(getApplicationContext()).load(tpicture).resize(350, 440).into(imglogo);


            //  image.setImageBitmap(loadBitmap(jsonArray.get(position)("iconStrings")));
        } catch (Exception e) {
            // When Error
            imglogo.setImageResource(android.R.drawable.ic_menu_report_image);
        }

        listviewmenu = (ListView) findViewById(R.id.listviewmenu);

//Request SQLite
        mySQLite = new MySQLite(this);
        synAndDelete();

        setlistmenu();


    }

    private void getIDFromIntent() {
        idString = getIntent().getStringExtra("ttid");
        Log.d("8AprilV1", "idString ==> " + idString);

        try {

            GetShopWhereID getShopWhereID = new GetShopWhereID(MenuActivity.this);
            getShopWhereID.execute(idString);

            String strJSON = getShopWhereID.get();
            Log.d("8AprilV1", "JSON ==> " + strJSON);

            JSONArray jsonArray = new JSONArray(strJSON);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            latString = jsonObject.getString("tlatitude");
            lngString = jsonObject.getString("tlongtitude");
            Log.d("8AprilV1", "lat ==> " + latString);
            Log.d("8AprilV1", "lng ==> " + lngString);


        } catch (Exception e) {
            Log.d("8AprilV1", "e getID ==> " + e.toString());
        }

    }

    public void clickMap(View view) {

        Intent objIntent = new Intent(MenuActivity.this, MapsActivity.class);
        objIntent.putExtra("tlatitude", latString);
        objIntent.putExtra("tlongtitude", lngString);

        startActivity(objIntent);

    }

    private void setlistmenu() {

        ttid = getIntent().getStringExtra("ttid");
        Toast.makeText(getApplication(), ttid.toString(), Toast.LENGTH_LONG).show();
        final SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        final Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM tbt_menu WHERE tid  = " + "'" +
                ttid + "'", null);
        Log.d("ttid", ttid);
        cursor.moveToFirst();


        int count = cursor.getCount();
        final String[] tid = new String[count];
        final String[] mname = new String[count];
        final String[] mdetail = new String[count];
        final String[] mpicture = new String[count];


        for (int i = 0; i < count; ) {
            tid[i] = cursor.getString(cursor.getColumnIndex(MySQLite.column_ttid));
            Toast.makeText(getApplication(), tid[i].toString(), Toast.LENGTH_LONG).show();

            mname[i] = cursor.getString(cursor.getColumnIndex(MySQLite.column_mname));
            mdetail[i] = cursor.getString(cursor.getColumnIndex(MySQLite.column_mdetail));
            mpicture[i] = cursor.getString(cursor.getColumnIndex(MySQLite.column_mpicture));
            i++;

            cursor.moveToNext();
        }

        cursor.close();


        MenuuAdapter menuuAdapter = new MenuuAdapter(MenuActivity.this, mpicture, mname);
        listviewmenu.setAdapter(menuuAdapter);

    }

    private void synAndDelete() {
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        // sqLiteDatabase.delete(MySQLite.menu_mtable, null, null);

        SynJSON mySynJSON = new SynJSON();
        mySynJSON.execute();

    } //synAndDelete

    private class SynJSON extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                String strURL = "http://www.zaabkalasin.com/turist/php_get_menu.php";
                //String strURL = "http://10.0.2.2/turist/php_get_menu.php";

                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();


                Request request = builder.url(strURL).build();

                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();


            } catch (Exception e) {
                Log.d("testwitthaya", "do test sys menu " + e.toString());
                return null;
            }
        }

        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                Log.d("witthaya ", "respon menu ==>" + s);

                SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                        MODE_PRIVATE, null);
                sqLiteDatabase.delete(MySQLite.menu_mtable, null, null);

                final JSONArray jsonArray = new JSONArray(s);


                final String[] ttidStrings = new String[jsonArray.length()];
                final String[] mnameStrings = new String[jsonArray.length()];
                final String[] mdetailStrings = new String[jsonArray.length()];
                final String[] mpictureStrings = new String[jsonArray.length()];


                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    ttidStrings[i] = jsonObject.getString("tid");
                    mnameStrings[i] = jsonObject.getString("mname");
                    mdetailStrings[i] = jsonObject.getString("mdetail");
                    mpictureStrings[i] = jsonObject.getString("mpicture");

                    mySQLite.addNewmenu(ttidStrings[i], mnameStrings[i], mdetailStrings[i], mpictureStrings[i]);
                }

            } catch (JSONException e) {
                //  e.printStackTrace();
                Log.d("witthaya", "onpost menu==>" + e.toString());
            }

        }
    }
}
