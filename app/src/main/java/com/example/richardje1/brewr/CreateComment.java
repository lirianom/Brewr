package com.example.richardje1.brewr;

import android.app.Activity;
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
 * CreateComment displays text entry boxes for the comment a user would
 * enter. Uses the information to create a comment on the given activity.
 *
 * @Author Martin Liriano
 * @Author Jacob Richard
 * @Version 1.0
 */
public class CreateComment extends Activity {

    private EditText mComment;
    private String commentText;
    private Button createButton;
    private Brew b;
    private View viewer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_item);

        createButton = (Button) findViewById(R.id.add_button);
        mComment = (EditText) findViewById(R.id.comment_text) ;

        Intent intent = getIntent();

        b = (Brew) intent.getExtras().getSerializable("a");


        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentText = mComment.getText().toString();

                viewer = v;

                CommentWorker cw = new CommentWorker();
                cw.execute(b.getmViewerID(), b.getmAID(), commentText);
            }
        });
    }
    @Override
    public void onBackPressed()
    {
        // code here to show dialog
        super.onBackPressed();

        Intent myIntent = new Intent(getApplicationContext(), HomePageActivity.class);
        startActivityForResult(myIntent, 0);
        finish();
        // optional depending on your needs
    }

    /**
     * CommentWorker a class that connects with PHP script in order to
     * work with Front-End Android Application.
     *
     * @Author Martin Liriano
     * @Author Jacob Richard
     * @Version 1.0
     */
    class CommentWorker extends AsyncTask<String, Void, String> {

        /**
         * doInBackground runs after execute is called. This calls the insertComment.php
         * script and gets whats passed in through the bufferedWriter.
         *
         * @param params params[0] = user_id, params[1] = post_id, params[2] = comment_text
         * @return result String - result is what gets echo'ed from the PHP script
         */
        @Override
        protected String doInBackground(String... params) {
            String URL = "http://student.cs.appstate.edu/lirianom/capstone/insertComment.php";
            String user_id = params[0];
            String post_id = params[1];
            String comment_text = params[2];

            try {
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

        /**
         * onPostExecute passes a Toast if their entry was valid.
         * Then it calls finish
         *
         * @param result
         */
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
