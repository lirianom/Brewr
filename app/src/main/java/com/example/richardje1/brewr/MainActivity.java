package com.example.richardje1.brewr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by richardje1 on 2/20/17.
 */

public class MainActivity extends Activity{

    Button loginButton;
    Button makeAccountButton;
    EditText enterUsername, enterPassword;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = (Button)findViewById(R.id.login_button);
        makeAccountButton = (Button)findViewById(R.id.make_account_button);
        enterUsername = (EditText)findViewById(R.id.enter_username_text);
        enterPassword = (EditText)findViewById(R.id.enter_password_text);


        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (enterUsername.getText().toString().equals("a") &&
                        enterPassword.getText().toString().equals("a")) {
                    Toast.makeText(getApplicationContext(),
                            "Login Successful", Toast.LENGTH_SHORT).show();

                    //Intent myIntent = new Intent(v.getContext(), HomePageAllActivity.class);
                    Intent myIntent = new Intent(v.getContext(), HomePageActivity.class);
                    startActivityForResult(myIntent, 0);
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong Credentials",
                            Toast.LENGTH_SHORT).show();

                }
            }
        });

        makeAccountButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                    //Toast.makeText(getApplicationContext(), "Make Account Screen",
                    //      Toast.LENGTH_SHORT).show();
                    Intent myIntent = new Intent(v.getContext(), CreateUser.class);
                    startActivityForResult(myIntent, 0);
                }

            });



    }
}
