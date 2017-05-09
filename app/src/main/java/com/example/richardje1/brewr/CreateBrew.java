package com.example.richardje1.brewr;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
 * CreateBrew creates and instantiates a Brew.
 *
 * @Author Martin Liriano
 * @Author Jacob Richard
 * @Version 1.0
 */
public class CreateBrew extends Activity {

    private EditText enterTitle, enterDescription, enterRoaster, enterBean;
    private Spinner enterMethod;
    private Button createButton;
    private View viewer;
    private String title, description, method, roaster, bean, userID;

    protected void onCreate(Bundle savedInstanceState) {

        //Bundle holds the data that gets passed in
        Bundle b = getIntent().getExtras();
        if (b != null) {
            userID = b.getString("a");
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_brew);

        enterTitle = (EditText) findViewById(R.id.brew_title);
        enterDescription = (EditText) findViewById(R.id.description);
        enterRoaster = (EditText) findViewById(R.id.roaster_text);
        enterBean = (EditText) findViewById(R.id.bean_text);
        enterMethod = (Spinner) findViewById(R.id.brew_method);
        createButton = (Button) findViewById(R.id.button);

        //Allows method to be set to whatever method is set in the spinner
        enterMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                method = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.brew_method_array,
                android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        enterMethod.setAdapter(adapter);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = enterTitle.getText().toString();
                description = enterDescription.getText().toString();
                bean = enterBean.getText().toString();
                roaster = enterRoaster.getText().toString();
                viewer = v;
                if(title.isEmpty() ||description.isEmpty() || bean.isEmpty() ||roaster.isEmpty()){
                    Toast.makeText(getApplicationContext(),
                            "Missing one or more fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    ActivityWorker aw = new ActivityWorker();
                    aw.execute(userID, title, description, roaster, bean, method);
                }
            }
        });
    }


    /**
     * ActivityWorker a class that connects with PHP script in order to
     * work with Front-End Android Application.
     *
     * @Author Martin Liriano
     * @Author Jacob Richard
     * @Version 1.0
     */
    class ActivityWorker extends AsyncTask<String, Void, String> {

        /**
         * doInBackground runs after execute is called. This calls the insertPost.php
         * script and gets whats passed in through the bufferedWriter.
         *
         * @param params params[0] = user_id, params[1] = post_name, params[2] = post_text
         *               params[3] = post_roaster, params[4] = post_bean, params[5] = post_method
         * @return result String - result is what gets echo'ed from the PHP script
         */
        @Override
        protected String doInBackground(String... params) {
            String URL = "http://student.cs.appstate.edu/lirianom/capstone/insertPost.php";
            String id = params[0];
            String title = params[1];
            String description = params[2];
            String roaster = params[3];
            String bean = params[4];
            String method = params[5];
            try {

                java.net.URL url = new URL(URL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8") + "&"
                        + URLEncoder.encode("post_name", "UTF-8") + "=" + URLEncoder.encode(title, "UTF-8") + "&"
                        + URLEncoder.encode("post_text", "UTF-8") + "=" + URLEncoder.encode(description, "UTF-8") + "&"
                        + URLEncoder.encode("post_roaster", "UTF-8") + "=" + URLEncoder.encode(roaster, "UTF-8") + "&"
                        + URLEncoder.encode("post_bean", "UTF-8") + "=" + URLEncoder.encode(bean, "UTF-8") + "&"
                        + URLEncoder.encode("post_method", "UTF-8") + "=" + URLEncoder.encode(method, "UTF-8") + "&";
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
         * onPostExecute passes a Toast and returns the user to the HomePageActivity
         * if their entry was valid
         *
         * @param result
         */
        @Override
            protected void onPostExecute(String result) {
            if (result.equals("Activity Created!")) {
                Toast.makeText(getApplicationContext(),
                        result, Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(viewer.getContext(), HomePageActivity.class);
                myIntent.putExtra("a", userID);
                startActivity(myIntent);
            }
        }
    }
}

