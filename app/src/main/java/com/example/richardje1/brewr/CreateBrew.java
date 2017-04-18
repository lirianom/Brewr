package com.example.richardje1.brewr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.UUID;

/**
 * Created by richardje1 on 4/17/17.
 */

public class CreateBrew extends Activity{
    BrewrUser brewrUser;
    EditText enterTitle,  enterDescription;
    Spinner enterMethod;
    TextView usernameDisplay;
    Button dateButton;

    BackgroundWorker backgroundWorker;

    String title, description, username, method;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_brew);
        enterTitle = (EditText) findViewById(R.id.brew_title);
        enterDescription = (EditText) findViewById(R.id.description);
        usernameDisplay = (TextView) findViewById(R.id.shown_username);
        enterMethod = (Spinner) findViewById(R.id.brew_method);
        dateButton = (Button) findViewById(R.id.brew_date);

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

        title = enterTitle.getText().toString();
        description = enterDescription.getText().toString();
        //method = enterMethod;


            Intent myIntent = new Intent(getApplicationContext(), HomePageActivity.class);
            startActivity(myIntent);



    }
}
