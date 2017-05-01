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
 * Created by martin on 4/30/17.
 */

public class AddFriend extends Activity {

    EditText mUserNameEntry;
    String userNameText;
    String userID;
    Button addButton;
    Context c;
    View viewer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_friend);


        addButton = (Button) findViewById(R.id.friend_search_button);
        mUserNameEntry = (EditText) findViewById(R.id.enter_search);


        Bundle b = getIntent().getExtras();
        if (b != null) {
            userID = b.getString("a");
        }

        c = this.getApplicationContext();


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userNameText = mUserNameEntry.getText().toString();

                FollowWorker fw = new FollowWorker();
                fw.execute(userID, userNameText);

                viewer = v;

                //com.example.richardje1.brewr.CreateComment.CommentWorker cw = new com.example.richardje1.brewr.CreateComment.CommentWorker();
                //cw.execute(b.getmViewerID(), b.getmAID(), commentText);


                //finish();
                //aw.execute(userID, title, description, roaster, bean, method);
            }
        });
    }



        /*
        fName = enterFname.getText().toString();
        lName = enterLname.getText().toString();
        username = enterUsername.getText().toString();
        passwordConf = passwordConfirm.getText().toString();
        password = enterPassword.getText().toString();
        email = enterEmail.getText().toString();
        */

    //backgroundWorker = new BackgroundWorker(this);

    class FollowWorker extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String URL = "http://student.cs.appstate.edu/lirianom/capstone/follow.php";
            String user_id = params[0];
            String user_name = params[1];

            try {
                //String user_name = params[2];
                //String password = params[3];
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
