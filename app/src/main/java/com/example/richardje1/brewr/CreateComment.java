package com.example.richardje1.brewr;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.util.UUID;

/**
 * Created by martin on 4/29/17.
 */

public class CreateComment extends Activity {

    EditText mComment;
    String commentText;
    String userID;
    String activityID;
    Button createButton;
    Brew b;


    View viewer;

    CreateBrew.ActivityWorker aw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_item);


        createButton = (Button) findViewById(R.id.add_button);
        mComment = (EditText) findViewById(R.id.comment_text) ;
        b = (Brew) getIntent().getSerializableExtra("a");



        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentText = mComment.getText().toString();

                viewer = v;

                CommentWorker cw = new CommentWorker();
                cw.execute(b.getmViewerID(), b.getmAID(), commentText);


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

    class CommentWorker extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String URL = "http://student.cs.appstate.edu/lirianom/capstone/insertComment.php";
            String user_id = params[0];
            String post_id = params[1];
            String comment_text = params[2];

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
                String post_data = URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(user_id, "UTF-8") + "&"
                        + URLEncoder.encode("post_id", "UTF-8") + "=" + URLEncoder.encode(post_id, "UTF-8") + "&"
                        + URLEncoder.encode("comment_text", "UTF-8") + "=" + URLEncoder.encode(comment_text, "UTF-8");
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
            if (result.equals("Comment Created!")) {
                Toast.makeText(getApplicationContext(),
                        result, Toast.LENGTH_SHORT).show();
                finish();
            }

        }
    }
}
