package com.example.richardje1.brewr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * AddFriend Activity is the Activity that gets a allows you to
 * get find and add a friend.
 *
 * @Author Martin Liriano
 * @Author Jacob Richard
 *
 * @Version 1.0
 */

public class AddFriend extends Activity {

    private EditText mUserNameEntry;
    private String userNameText;
    private String userID;
    private Button addButton;
    private Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_friend);

        //button for adding a friend
        addButton = (Button) findViewById(R.id.friend_search_button);
        //textfield for friend who is added
        mUserNameEntry = (EditText) findViewById(R.id.enter_search);

        //Bundle has userID that was passed from HomePageActivity
        Bundle b = getIntent().getExtras();
        if (b != null) {
            userID = b.getString("a");
        }

        //Application Context for the FollowWorker
        c = this.getApplicationContext();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userNameText = mUserNameEntry.getText().toString();
                FollowWorker fw = new FollowWorker();
                fw.execute(userID, userNameText);
            }
        });
    }

    /**
     * FollowWorker a class that connects with PHP script in order to
     * work with Front-End Android Application.
     *
     * @Author Martin Liriano
     * @Author Jacob Richard
     * @Version 1.0
     */
    class FollowWorker extends AsyncTask<String, Void, String> {

        /**
         * doInBackground runs after execute is called. This calls the follow.php
         * script and gets whats passed in through the bufferedWriter.
         *
         * @param params params[0] = user_id, params[1] = user_name
         * @return result String - result is what gets echo'ed from the PHP script
         */
        @Override
        protected String doInBackground(String... params) {
            String URL = "http://student.cs.appstate.edu/lirianom/capstone/follow.php";
            String user_id = params[0];
            String user_name = params[1];

            try {
                java.net.URL url = new URL(URL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8")+ "&"
                        + URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(user_id, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * onPostExecute is the last step in execute.
         *
         * @param result String - result is the string that is returned in
         *                        doInBackground
         */
        @Override
        protected void onPostExecute(String result) {
            if (result.equals("User found and added!")) {
                Toast.makeText(getApplicationContext(),
                        result, Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(c, HomePageActivity.class);
                startActivity(myIntent);
            }
            else {
                Toast.makeText(getApplicationContext(),
                        "User Not Found", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
