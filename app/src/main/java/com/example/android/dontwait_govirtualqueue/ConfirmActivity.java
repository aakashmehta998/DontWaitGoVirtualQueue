package com.example.android.dontwait_govirtualqueue;

import android.app.Application;
import android.content.Intent;
import android.location.Address;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.model.Marker;

import org.w3c.dom.Text;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class ConfirmActivity extends AppCompatActivity {
    int markerindex = 0;
    String name="",address="",contact="";
    public class GetDetails extends AsyncTask<Void,Void,String>
    {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                Socket s = new Socket("10.0.0.6",5982);
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                dos.writeUTF("GetDetails");
                dos.writeInt(markerindex);
                name = dis.readUTF();
                Log.v("harshdemo",name+" comfirm");
                address = dis.readUTF();
                Log.v("harshdemo",address);
                contact = dis.readUTF();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return "valid";
        }
    }
    View.OnClickListener orderOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(getApplicationContext(),BarcodeGeneration.class);
            intent.putExtra("markerindex",markerindex);
            startActivity(intent);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        TextView restaurant_name = findViewById(R.id.restaurant_name);
        TextView restaurant_address = findViewById(R.id.restaurant_address);
        TextView restaurant_contact = findViewById(R.id.restaurant_contact);
        Button order=findViewById(R.id.order);
        order.setOnClickListener(orderOnClickListener);

         markerindex = getIntent().getIntExtra("Key", 0);
         GetDetails getDetails = new GetDetails();
        try {
            getDetails.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        restaurant_name.setText(name);
        restaurant_address.setText(address);
        restaurant_contact.setText(contact);

    }
}
