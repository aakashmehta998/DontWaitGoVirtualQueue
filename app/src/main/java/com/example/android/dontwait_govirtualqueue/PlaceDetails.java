package com.example.android.dontwait_govirtualqueue;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.dontwait_govirtualqueue.R;
import com.google.android.gms.location.places.PlacesOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlaceDetails extends AppCompatActivity {
    String placePhone;
    String placeWebsite;
    @BindView(R.id.contact_title)
    TextView contactTitle;
    @BindView(R.id.phone_title)
    TextView phoneTitle;
    @BindView(R.id.website_title)
    TextView websiteTitle;
    @BindView(R.id.rating_view)
    LinearLayout ratingView;
    @BindView(R.id.price_view)
    LinearLayout priceView;
    @BindView(R.id.about_view)
    LinearLayoutCompat aboutView;
    @BindView(R.id.about_title)
    TextView aboutTitle;
    @BindView(R.id.contact_view)
    LinearLayoutCompat contactView;
    @BindView(R.id.book_button)
    AppCompatButton bookButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);
        ButterKnife.bind(this);
        TextView place_name_text = findViewById(R.id.place_name_text);
        TextView place_address_text = findViewById(R.id.place_address_text);
        TextView place_phone = findViewById(R.id.phone_number_text);
        TextView place_website = findViewById(R.id.website_text);
        TextView place_rating = findViewById(R.id.rating_text);
        TextView place_price = findViewById(R.id.price_text);
        Intent intent = getIntent();
        String placeName1 = "DEmo";
        PlacesOptions myPlace = new PlacesOptions.Builder().build();
        placeName1 = intent.getStringExtra("placename");
        String placeAdrress = intent.getStringExtra("placeaddress");
        placePhone = intent.getStringExtra("placephone");
        placeWebsite = intent.getStringExtra("placewebsite");
        Log.v("checkHere", placePhone);
        Log.v("checkhere", placeWebsite);
        if (!placePhone.equals("")) {
            contactView.setVisibility(View.VISIBLE);
            contactTitle.setVisibility(View.VISIBLE);
            phoneTitle.setVisibility(View.VISIBLE);
            place_phone.setVisibility(View.VISIBLE);
            place_phone.setText(placePhone);
            place_phone.setOnClickListener(phoneListener);


        }
        if (!placeWebsite.equals("")) {
            contactView.setVisibility(View.VISIBLE);
            contactTitle.setVisibility(View.VISIBLE);
            websiteTitle.setVisibility(View.VISIBLE);
            place_website.setVisibility(View.VISIBLE);
            placeWebsite = "http://" + placeWebsite;
            place_website.setText(placeWebsite);
            place_website.setOnClickListener(websiteListener);
        }

        String placeRating = intent.getStringExtra("placerating");
        String placePrice = intent.getStringExtra("placeprice");
        Log.v("checkHere", "rating" + placeRating);
        Log.v("checkHere", "price" + placePrice);
        if (!placeRating.equals("-1.0")) {
            aboutView.setVisibility(View.VISIBLE);
            ratingView.setVisibility(View.VISIBLE);
            aboutTitle.setVisibility(View.VISIBLE);
            place_rating.setText(placeRating + "/5.0");

        }
        if (!placePrice.equals("-1")) {
            aboutView.setVisibility(View.VISIBLE);
            aboutTitle.setVisibility(View.VISIBLE);
            priceView.setVisibility(View.VISIBLE);
            placePrice = getPriceLevel(placePrice);
            place_price.setText(placePrice);
        }
        Log.v("checkHere", "Placedetails" + placeName1);
        place_address_text.setText(placeAdrress);
        place_name_text.setText(placeName1);


    }

    View.OnClickListener phoneListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + placePhone));
            startActivity(intent);
        }
    };
    View.OnClickListener websiteListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(placeWebsite));
            startActivity(intent);
        }
    };

    String getPriceLevel(String a) {
        if (a.equals("0")) {
            return "Free";
        } else if (a.equals("1")) {
            return "Inexpensive";
        } else if (a.equals("2")) {
            return "Moderate";
        } else if (a.equals("3")) {
            return "Expensive";
        } else if (a.equals("4")) {
            return "Very Expensive";
        } else {
            return "";
        }
    }

    @OnClick(R.id.book_button)
    public void onViewClicked() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(PlaceDetails.this);
        builder.setMessage("How many Seats?");
        final EditText input = new EditText(PlaceDetails.this);
        input.setHint("Enter no of seats you want to book");
        input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        lp.rightMargin = 10;
        builder.setView(input);
        builder.setPositiveButton("NEXT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String seats=input.getText().toString();
                Log.v("seatsString",seats);
                Integer seat=0;
                if(!seats.equals("")) {
                    seat = Integer.parseInt(seats);
                    Log.v("seats in integer",seat+"");
                }
                Intent intent = new Intent(getApplicationContext(),FinalActivity.class);
                intent.putExtra("seats",seats);
                if(seats.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Please enter no of seats",Toast.LENGTH_LONG).show();
                }
                else if(seat>10)
                {
                    Toast.makeText(PlaceDetails.this,"Seats limited to 10",Toast.LENGTH_LONG).show();
                }
                else {
                    startActivity(intent);
                    dialogInterface.cancel();
                }
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }
}
