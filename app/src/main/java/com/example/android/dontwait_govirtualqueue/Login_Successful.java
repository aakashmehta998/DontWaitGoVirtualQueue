package com.example.android.dontwait_govirtualqueue;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Login_Successful extends AppCompatActivity {
    String firstname="",lastname="",email="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__successful);
        Intent intent = getIntent();
         firstname = intent.getStringExtra("firstname");
         lastname = intent.getStringExtra("lastname");
         email = intent.getStringExtra("email");
        TextView textView = findViewById(R.id.welcome_text);
        textView.setText("Welcome, "+firstname+" "+lastname);
        View.OnClickListener continue_onclick_listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1= new Intent(getApplicationContext(),CitySelection.class);
                intent1.putExtra("email",email);
                startActivity(intent1);


            }
        };
        Button continueButton = findViewById(R.id.continue_button);
        continueButton.setOnClickListener(continue_onclick_listener);
    }

}
