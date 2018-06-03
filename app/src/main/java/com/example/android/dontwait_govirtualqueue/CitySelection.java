package com.example.android.dontwait_govirtualqueue;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CitySelection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_selection);

        TextView ahmedabadText = findViewById(R.id.ahmedabad_text);
        TextView bangaloreText = findViewById(R.id.bangalore_text);
        TextView kolkataText = findViewById(R.id.kolkata_text);
        TextView delhiText = findViewById(R.id.delhi_text);
        TextView mumbaiText = findViewById(R.id.mumbai_text);
        View.OnClickListener ahmedabadOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callPlaceSelection("ahmedabad");

            }
        };
        View.OnClickListener mumbaiOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callPlaceSelection("mumbai");

            }
        };
        View.OnClickListener delhiOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callPlaceSelection("delhi");

            }
        };
        View.OnClickListener kolkataOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callPlaceSelection("kolkata");

            }
        };
        View.OnClickListener bangaloreOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callPlaceSelection("bangalore");

            }
        };
        ahmedabadText.setOnClickListener(ahmedabadOnClickListener);
        kolkataText.setOnClickListener(kolkataOnClickListener);
        bangaloreText.setOnClickListener(bangaloreOnClickListener);
        delhiText.setOnClickListener(delhiOnClickListener);
        mumbaiText.setOnClickListener(mumbaiOnClickListener);
    }
    protected  void callPlaceSelection(String city)
    {
        Intent intent = new Intent(getApplicationContext(),PlaceSelection.class);
        intent.putExtra("city",city);
        startActivity(intent);
    }

}
