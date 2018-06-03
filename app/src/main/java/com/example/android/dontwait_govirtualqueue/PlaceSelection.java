package com.example.android.dontwait_govirtualqueue;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class PlaceSelection extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_selection);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    }
    ArrayList<Marker> markerArrayList = new ArrayList<>();
    HashMap<Marker,Integer> markerKey = new HashMap<>();
    HashMap<Marker,String> markerAddress = new HashMap<>();
    HashMap<Marker,String> markerContact = new HashMap<>();
    public class MarkerAdd extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Socket s = new Socket("10.0.0.10",5982);
                 DataInputStream dis = new DataInputStream(s.getInputStream());
                 DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                dos.writeUTF("MarkerRequest");

                int row_count = dis.readInt();
                Log.v("harshdemo","row count:"+row_count);
                for(int i=1;i<=row_count;i++)
                {


                     final double lat = dis.readDouble();
                    Log.v("harshdemo","lat:"+lat);
                     final double lng = dis.readDouble();
                    Log.v("harshdemo","lng:"+lng);
                     final int counter = i;
                     final String title = dis.readUTF();




                            try {
                                final String address = dis.readUTF();
                                Log.v("harshdemo",address);

                                final String contact = dis.readUTF();
                                Log.v("harshdemo",contact);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        Marker m = null;
                                        m = mMap.addMarker(new MarkerOptions().position(new LatLng(lat,lng)).title(title));
                                        markerArrayList.add(m);
                                        markerKey.put(m,counter);
                                        markerAddress.put(m,address);
                                        markerContact.put(m,contact);
                                    }
                                });

                            } catch (Exception e) {
                                Log.v("harshdemo","Inside the catch block");
                                System.out.println("Printing the stack trace");
                                e.printStackTrace();
                            }



                        }




                }
             catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        MarkerAdd markerAdd = new MarkerAdd();
        markerAdd.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        LatLng location = null;
        // Add a marker in Sydney and move the camera
        String city = getIntent().getStringExtra("city");
         final HashMap<Marker, Integer> mHashMap = new HashMap<Marker, Integer>();
        if(city.equals("ahmedabad")) {
            location = new LatLng(23.0225, 72.5714);

        }
        else if(city.equals("mumbai"))
        {
            location = new LatLng(19.0760, 72.8777);
        }
        else if(city.equals("delhi"))
        {
            location = new LatLng(28.7041, 77.1025);
        }
        else if(city.equals("bangalore"))
        {
            location = new LatLng(12.9716, 77.5946);
        }
        else if(city.equals("kolkata"))
        {
            location = new LatLng(22.5726, 88.3639);
        }

        GoogleMap.OnInfoWindowClickListener clickListener = new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                Log.v("harshdemo",mHashMap.get(marker)+"");
                Log.v("harshdemo",markerAddress.get(marker)+"");
                Log.v("harshdemo",markerContact.get(marker)+"");
                Intent intent = new Intent(getApplicationContext(),ConfirmActivity.class);

                intent.putExtra("Key",markerKey.get(marker));
                startActivity(intent);

            }
        };
        mMap.setOnInfoWindowClickListener(clickListener);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,14.0f));
      //  mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Ahmedabad"));

    }
}
