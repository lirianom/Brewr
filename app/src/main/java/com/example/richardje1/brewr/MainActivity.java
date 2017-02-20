package com.example.richardje1.brewr;

import android.app.Activity;
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
    EditText enterUsername, enterPassword;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = (Button)findViewById(R.id.login_button);
        enterUsername = (EditText)findViewById(R.id.enter_username_text);
        enterPassword = (EditText)findViewById(R.id.enter_password_text);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (enterUsername.getText().toString().equals("admin") &&
                        enterPassword.getText().toString().equals("admin")) {
                    Toast.makeText(getApplicationContext(),
                            "Login Successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong Credentials",
                            Toast.LENGTH_SHORT).show();

                }
            }
        });


    }
}
