package com.cresset.asimjofaofficial;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.cresset.asimjofaofficial.map.MyMarker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

import static com.cresset.asimjofaofficial.R.id.map;


public class Store extends Fragment {

    static final LatLng ASIMJOFA = new LatLng(24.823110, 67.035186);
    private GoogleMap map;
    private ImageView back;
    private Context context;
    private static long back_pressed;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_store, container, false);

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(getContext(), "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();
        } else {
            showGPSDisabledAlertToUser();

        }

        ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMapAsync(new OnMapReadyCallback() {
            float zoomLevel = (float) 18.0;
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ASIMJOFA, zoomLevel));
                map = googleMap;
                if (map != null) {
                    Marker kiel = map.addMarker(new MarkerOptions().position(ASIMJOFA).icon(BitmapDescriptorFactory
                            .fromResource(R.drawable.location_icon1)));
                    //kiel.setTitle("ASIM JOFA");
                    // kiel.setSnippet("Block 9, Clifton, Near Do Talwar، Karachi, Pakistan");
                    kiel.showInfoWindow();
                    // Rest of the stuff you need to do with the map
                }
            }
        });


//        map = ((MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map))
//                .getMap();
//
//        float zoomLevel = (float) 18.0;
//
//
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(ASIMJOFA, zoomLevel));
//        if (map != null) {
//            Marker kiel = map.addMarker(new MarkerOptions().position(ASIMJOFA).icon(BitmapDescriptorFactory
//                    .fromResource(R.drawable.location_icon1)));
//            //kiel.setTitle("ASIM JOFA");
//            // kiel.setSnippet("Block 9, Clifton, Near Do Talwar، Karachi, Pakistan");
//            kiel.showInfoWindow();
//        } else {
//
//        }
        return view;

    }


    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPS",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
}
