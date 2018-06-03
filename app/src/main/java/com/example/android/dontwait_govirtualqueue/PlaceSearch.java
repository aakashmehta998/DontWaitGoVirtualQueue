package com.example.android.dontwait_govirtualqueue;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.dontwait_govirtualqueue.PlaceDetails;
import com.example.android.dontwait_govirtualqueue.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class PlaceSearch extends AppCompatActivity implements OnMapReadyCallback {
    AppCompatActivity activity = null;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE_1 = 2;
    AppCompatEditText sourceEditText;
    AppCompatEditText destinationEditText;
    AppCompatImageButton locationButton;
    boolean locationflag = false;
    LatLng currentLocation;
    GoogleMap m_map;
    Place source=null;
    //Place destination=null;
    LatLng sourceLatLng = new LatLng(0,0);
    LatLng destinationLatLng = new LatLng(0,0);
    boolean flag1 = false;
    //boolean flag2 = false;
    Marker sourceMarker;
    //Marker destinationMarker;
    GeoDataClient mGeoDataClient;
    PlaceDetectionClient mPlaceDetectionClient;
    FusedLocationProviderClient mfusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_search);
       // final int locationOff = getResources().getIdentifier("location_off", "drawable", this.getPackageName());
        //final int locationOn = getResources().getIdentifier("location_on_1", "drawable", this.getPackageName());
        activity = this;
        //locationButton = findViewById(R.id.location_button);
        mGeoDataClient = Places.getGeoDataClient(this, null);
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);
        mfusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        final AutocompleteFilter filter = new AutocompleteFilter.Builder().setTypeFilter(79).setCountry("IND").build();
//        m_map.setOnIndoorStateChangeListener((GoogleMap.OnIndoorStateChangeListener) this);
        /*View.OnClickListener locationListner = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (locationflag == true) {
                    locationflag = false;
                    locationButton.setBackgroundResource(locationOff);
                } else {
                    locationflag = true;
                    locationButton.setBackgroundResource(locationOn);
                }
                if (locationflag == true) {
                    getDeviceLocation();
                    sourceLatLng = currentLocation;
                    CameraPosition target = CameraPosition.builder().target(currentLocation).zoom(14).build();
                    m_map.animateCamera(CameraUpdateFactory.newCameraPosition(target));
                    String placeName = "Your Location";
                    sourceEditText.setText(placeName);
                    if (flag1 == true) {
                        removeSourceMarker();
                    }
                    setSourceMarker(currentLocation, placeName);
                } else {
                    sourceEditText.setText("");
                    sourceEditText.setHint("Search Source Here");
                    removeSourceMarker();
                }
            }
        };*/
        //locationButton.setOnClickListener(locationListner);
        sourceEditText = findViewById(R.id.source_text);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sourceEditText.setShowSoftInputOnFocus(false);
        } else {
            sourceEditText.setTextIsSelectable(true);
        }
        /*destinationEditText = findViewById(R.id.destination_text);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            destinationEditText.setShowSoftInputOnFocus(false);
        } else {
            destinationEditText.setTextIsSelectable(true);
        }*/
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map_fragment4);
        mapFragment.getMapAsync(this);
        View.OnClickListener sourceListner = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {

                    Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).setFilter(filter).build(activity);
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesNotAvailableException d) {

                } catch (GooglePlayServicesRepairableException e) {

                }

            }
        };
        sourceEditText.setOnClickListener(sourceListner);
        /*View.OnClickListener destinationListner = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).build(activity);
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE_1);
                } catch (GooglePlayServicesNotAvailableException d) {

                } catch (GooglePlayServicesRepairableException e) {

                }
            }
        };
        destinationEditText.setOnClickListener(destinationListner);
        if (sourceLatLng != null && destinationLatLng != null)
            addpoly();*/
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                source = PlaceAutocomplete.getPlace(this, intent);
                sourceEditText.setText(source.getName());
                if (flag1 == true)
                    removeSourceMarker();
                sourceLatLng = source.getLatLng();
                String placeName = source.getName().toString();
                setSourceMarker(sourceLatLng, placeName);
     //           addpoly();
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, intent);
                Toast.makeText(this, "error", Toast.LENGTH_LONG);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "cancelled", Toast.LENGTH_LONG);
            }
        }
    }

   public void updateLocationUI() {
        try {
            m_map.setMyLocationEnabled(true);
            m_map.getUiSettings().setMyLocationButtonEnabled(true);
        } catch (SecurityException e) {

        }

    }

    public void getDeviceLocation() {
        try {
            Task<Location> locationResult = mfusedLocationProviderClient.getLastLocation();
            locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    if (task.isSuccessful()) {
                        Location mLastKnownLocation = task.getResult();
                        m_map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()), 14));
                        currentLocation = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
                    }
                }
            });
        } catch (SecurityException e) {

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        m_map = googleMap;
        updateLocationUI();
        getDeviceLocation();
        m_map.setOnInfoWindowClickListener(MyOnInfoWindowClickListener);
    }

    public void setSourceMarker(LatLng source_latlng, String placeName) {
        CameraPosition source_location = CameraPosition.builder().target(source_latlng).zoom(16).build();
        m_map.animateCamera(CameraUpdateFactory.newCameraPosition(source_location), null);
        sourceMarker = m_map.addMarker(new MarkerOptions().position(source_latlng).title(placeName));
        flag1 = true;
    }

    public void removeSourceMarker() {
        sourceMarker.remove();
    }

  /*  public void setDestinationMarker(Place destination) {
        destinationLatLng = destination.getLatLng();
        CameraPosition destination_location = CameraPosition.builder().target(destinationLatLng).zoom(16).build();
        m_map.animateCamera(CameraUpdateFactory.newCameraPosition(destination_location), null);
        destinationMarker = m_map.addMarker(new MarkerOptions().position(destinationLatLng).title(destination.getName().toString()));
        flag2 = true;
    }

    public void removeDestinationMarker() {
        destinationMarker.remove();
    }*/

    GoogleMap.OnInfoWindowClickListener MyOnInfoWindowClickListener = new GoogleMap.OnInfoWindowClickListener() {
        @Override
        public void onInfoWindowClick(Marker marker) {

            Intent intent = new Intent(getApplicationContext(), PlaceDetails.class);
            Log.v("checkHere", "placename" + marker.getTitle().toString());
            intent.putExtra("placename", marker.getTitle().toString());
            LatLng markerLatLng = marker.getPosition();
            Log.v("checkHere", "latlng" + markerLatLng);
            Uri uri;
         /*   if(source == null)
            {
                Log.v("checkHere","source is null");
            }  */

            if (markerLatLng.toString().equals(sourceLatLng.toString())){
                if(marker.getTitle().equals("Your Location"))
                {
                    // Log.v("checkHere", "sourcelatlng" + source.getLatLng());
                    intent.putExtra("placeaddress", "");
                    // Log.v("checkHere","sourceAddress"+source.getAddress());
                    intent.putExtra("placephone","");
                    intent.putExtra("placewebsite","");
                    intent.putExtra("placerating","-1.0");
                    intent.putExtra("placeprice","-1");
                }
                else {
                    Log.v("checkHere", "sourcelatlng" + source.getLatLng());
                    intent.putExtra("placeaddress", source.getAddress());
                    Log.v("checkHere", "sourceAddress" + source.getAddress());
                    intent.putExtra("placephone", source.getPhoneNumber());
                    uri = source.getWebsiteUri();
                    if(uri!=null) {


                        intent.putExtra("placewebsite", uri.getHost());
                    }
                    else
                    {
                        intent.putExtra("placewebsite","");
                    }
                    intent.putExtra("placerating", source.getRating()+"");
                    intent.putExtra("placeprice", source.getPriceLevel()+"");
                }
            }
            /*if (markerLatLng.toString().equals(destinationLatLng.toString())) {
                Log.v("checkHere", "destinationlatlng" + destination.getLatLng());
                intent.putExtra("placeaddress", destination.getAddress());
                Log.v("checkHere","destinationAddress"+destination.getAddress());
                intent.putExtra("placephone",destination.getPhoneNumber());
                uri = destination.getWebsiteUri();
                if(uri!=null) {


                    intent.putExtra("placewebsite", uri.getHost());
                }
                else
                {
                    intent.putExtra("placewebsite","");
                }
                intent.putExtra("placerating",destination.getRating()+"");
                intent.putExtra("placeprice",destination.getPriceLevel()+"");
            }*/
            startActivity(intent);
        }


    };

}
