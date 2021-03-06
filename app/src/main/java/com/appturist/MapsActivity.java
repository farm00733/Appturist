package com.appturist;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String latString, lngString, iconString, titleString;
    private double latADouble, lngADouble;
    private LatLng centerLatLng;
    private int imageMakerAnInt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //Receive Value From Intent
        receiveValue();

        //Choose Image Maker


        //Create LatLng
        //createLatLng();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    }   // Main Method



    private void createLatLng() {

        centerLatLng = new LatLng(latADouble, lngADouble);

    }   //crateLatLng

    private void receiveValue() {

        latString = getIntent().getStringExtra("tlatitude");
        lngString = getIntent().getStringExtra("tlongtitude");
        Log.d("8AprilV1", "lat receive ==> " + latString);
        Log.d("8AprilV1", "lng receive ==> " + lngString);

        latADouble = Double.parseDouble(latString);
        lngADouble = Double.parseDouble(lngString);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Setup Center of Map to LatLng
        LatLng latLng = new LatLng(13.668463, 100.623097);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));

        //Create Marker
//        mMap.addMarker(new MarkerOptions()
//                .position(centerLatLng)
//                .icon(BitmapDescriptorFactory.fromResource(imageMakerAnInt))
//                .title(titleString));

    }   // onMapReady

}   // Main Class
