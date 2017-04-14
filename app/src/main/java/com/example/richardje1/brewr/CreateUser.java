package com.example.richardje1.brewr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.UUID;

/**
 * Created by richardje1 on 4/10/17.
 */

public class CreateUser extends Activity {
    BrewrUser brewrUser;
    EditText enterFname, enterLname, enterUsername, enterPassword, passwordConfirm,
            enterEmail;
    Button createButton;

    BackgroundWorker backgroundWorker;

    String fName, lName, username, password, passwordConf, email;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        enterFname = (EditText) findViewById(R.id.edit_fname);
        enterLname = (EditText) findViewById(R.id.edit_lname);
        enterUsername = (EditText) findViewById(R.id.edit_username);
        enterPassword = (EditText) findViewById(R.id.edit_password);
        passwordConfirm = (EditText) findViewById(R.id.confirm_password);
        enterEmail = (EditText) findViewById(R.id.edit_email);
        createButton = (Button) findViewById(R.id.make_account_button);

        /*
        fName = enterFname.getText().toString();
        lName = enterLname.getText().toString();
        username = enterUsername.getText().toString();
        passwordConf = passwordConfirm.getText().toString();
        password = enterPassword.getText().toString();
        email = enterEmail.getText().toString();
        */

        backgroundWorker = new BackgroundWorker(this);
    }

    public void onClick(View view) {
        fName = enterFname.getText().toString();
        lName = enterLname.getText().toString();
        username = enterUsername.getText().toString();
        passwordConf = passwordConfirm.getText().toString();
        password = enterPassword.getText().toString();
        email = enterEmail.getText().toString();


        if (!passwordConf.equals(password)) {
            Intent myIntent = new Intent(getApplicationContext(), CreateUser.class);
            startActivity(myIntent);
        }
        else {
            String type = "0";
            UUID uuid = UUID.randomUUID();

            String id = uuid.toString();

            backgroundWorker.execute(type, username, password, id, fName, lName, email);
        }

    }
        /*8
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if (enterUsername.getText().toString().equals("a") &&
                //       enterPassword.getText().toString().equals("a")) {
                Toast.makeText(getApplicationContext(),
                        "Created", Toast.LENGTH_SHORT).show();
                //Intent myIntent = new Intent(v.getContext(), HomePageActivity.class);
                //startActivityForResult(myIntent, 0);

                //Intent myIntent = new Intent(v.getContext(), HomePageAllActivity.class);
                //Intent myIntent = new Intent(v.getContext(), HomePageActivity.class);
                //startActivityForResult(myIntent, 0);
                //} else {
                //  Toast.makeText(getApplicationContext(), "Wrong Credentials",
                //        Toast.LENGTH_SHORT).show();

                //}
            }
        });
**/


}
