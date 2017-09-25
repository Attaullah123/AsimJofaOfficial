package com.cresset.asimjofaofficial;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cresset.asimjofaofficial.map.MyMarker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;


public class ShowMap extends AppCompatActivity {

    static final LatLng ASIMJOFA = new LatLng(24.823110, 67.035186);
    private GoogleMap map;
    private ImageView back;
    private Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        back = (ImageView) findViewById(R.id.img_cross);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                .getMap();

        float zoomLevel = (float) 18.0;



        map.moveCamera(CameraUpdateFactory.newLatLngZoom(ASIMJOFA, zoomLevel));
        if (map != null) {
            Marker kiel = map.addMarker(new MarkerOptions().position(ASIMJOFA).icon(BitmapDescriptorFactory
                    .fromResource(R.drawable.location_icon1)));
            //kiel.setTitle("ASIM JOFA");
            // kiel.setSnippet("Block 9, Clifton, Near Do Talwar، Karachi, Pakistan");
            kiel.showInfoWindow();
        } else {
            Toast.makeText(context, "Please switch on the GPS", Toast.LENGTH_LONG).show();
        }


    }
}
