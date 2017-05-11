package com.example.richardje1.brewr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * CreateUser creates a user and adds them to the database using Backgroundworker
 *
 * @Author Martin Liriano
 * @Author Jacob Richard
 * @Version 1.0
 */
public class CreateUser extends Activity {
    //fields used to enter user input
    private EditText enterFname, enterLname, enterUsername, enterPassword, passwordConfirm,
            enterEmail;
    private Button createButton;

    private BackgroundWorker backgroundWorker;

    //fields used to store the input
    private String fName, lName, username, password, passwordConf, email;

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
        backgroundWorker = new BackgroundWorker(this);
    }

    public void onClick(View view) {
        fName = enterFname.getText().toString();
        lName = enterLname.getText().toString();
        username = enterUsername.getText().toString();
        passwordConf = passwordConfirm.getText().toString();
        password = enterPassword.getText().toString();
        email = enterEmail.getText().toString();

        //Check to make sure passwords confirm matches the password
        if (!passwordConf.equals(password)) {
            Intent myIntent = new Intent(getApplicationContext(), CreateUser.class);
            startActivity(myIntent);
        }
        else {
            String type = "0";

            backgroundWorker.execute(type, fName, lName, username, email, password);
        }

    }
}
