package com.example.richardje1.brewr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * MainActivity is the primary page that gets pulled up when the application gets launched
 * can log a user in or direct the user to a page to register
 *
 * @Author Martin Liriano
 * @Author Jacob Richard
 * @Version 1.0
 */
public class MainActivity extends Activity{

    Button loginButton;
    Button makeAccountButton;
    EditText enterUsername, enterPassword;

    BackgroundWorker backgroundWorker;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = (Button)findViewById(R.id.login_button);
        makeAccountButton = (Button)findViewById(R.id.make_account_button);
        enterUsername = (EditText)findViewById(R.id.enter_username_text);
        enterPassword = (EditText)findViewById(R.id.enter_password_text);
        backgroundWorker = new BackgroundWorker(this);


        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String username = enterUsername.getText().toString();
                String password = enterPassword.getText().toString();
                String type = "1";

                backgroundWorker.execute(type, username, password);
            }
        });

        makeAccountButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                    Intent myIntent = new Intent(v.getContext(), CreateUser.class);
                    startActivityForResult(myIntent, 0);
                    finish();
                }

            });



    }
}
