package com.example.android.dontwait_govirtualqueue;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SignUp extends AppCompatActivity {

    public class SignupDataStore extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... strings) {

            try {
                Socket s =new Socket("10.0.0.6",5982);
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                dos.writeUTF("store");
                dos.writeUTF(strings[0]);
                dos.writeUTF(strings[1]);
                dos.writeUTF(strings[2]);
                dos.writeUTF(strings[3]);
                dos.close();
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Button signup = findViewById(R.id.signup_button);
        signup.setOnClickListener(signupButtonClickListener);

    }
    private View.OnClickListener signupButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            TextView textView = findViewById(R.id.signup_successful_text);
            textView.setVisibility(View.INVISIBLE);
            EditText firstname = findViewById(R.id.firstname);
            EditText lastname = findViewById(R.id.lastname);
            EditText email = findViewById(R.id.email);
            EditText pass = findViewById(R.id.password);
            EditText repass = findViewById(R.id.repassword);
            TextView starfirstname = findViewById(R.id.starfirstname);
            TextView starlastname = findViewById(R.id.starlastname);
            TextView staremail = findViewById(R.id.staremail);
            TextView starpass = findViewById(R.id.starpass);
            TextView starrepass = findViewById(R.id.starrepass);
            TextView[] stararray = {starfirstname,starlastname,staremail,starpass,starrepass};
            EditText[] viewsarray = {firstname,lastname,email,pass,repass};
            boolean fieldempty = false;
            boolean passwordmismatch;
            for(int i=0;i<viewsarray.length;i++)
            {
                if(viewsarray[i].getText().toString().equals(""))
                {
                    stararray[i].setVisibility(View.VISIBLE);
                        fieldempty = true;

                }
                else
                {
                    stararray[i].setVisibility(View.INVISIBLE);
                }
            }
            if(!pass.getText().toString().equals(repass.getText().toString()))
            {
                starrepass.setVisibility(View.VISIBLE);
                passwordmismatch = true;
            }
            else
            {
                starrepass.setVisibility(View.INVISIBLE);
                passwordmismatch = false;
            }
            if(!fieldempty && !passwordmismatch)
            {
                TextView textView1 = findViewById(R.id.signup_successful_text);
                textView1.setVisibility(View.VISIBLE);
                SignupDataStore sds = new SignupDataStore();
                String[] data_to_store = {email.getText().toString(),pass.getText().toString(),firstname.getText().toString(),lastname.getText().toString()};
                sds.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,data_to_store);
            }

        }
    };

}
