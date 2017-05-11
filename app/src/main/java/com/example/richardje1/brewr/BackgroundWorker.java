package com.example.richardje1.brewr;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
import java.security.spec.KeySpec;

import android.util.Base64;
import android.widget.Toast;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * BackgroundWorker handles the login and register activities and queries.
 *
 * BackgrondWorker instantiates a new Asynchronous thread that does the connnection
 * to the student machine to run the script.
 *
 * @Author Martin Liriano
 * @Author Jacob Richard
 * @Version 1.0
 */
public class BackgroundWorker extends AsyncTask <String, Void, String> {
    Context context;
    AlertDialog alertDialog;
    private String[] URL;

    BackgroundWorker(Context ctx) {
        context = ctx;
        URL = new String[] {
                "http://student.cs.appstate.edu/lirianom/capstone/register.php",
                "http://student.cs.appstate.edu/lirianom/capstone/login.php",
        };
    }

    /**
     * retURL returns the correct String from the URL array
     *
     * @param index String - index of the string that needs to get passed in from
     *                       the URL array
     * @return String - url
     */
    public String retURL(String index){
        int i = Integer.parseInt(index);
        return URL[i];
    }

    @Override
    protected String doInBackground(String ...params) {
        String URL = retURL(params[0]);
        String param1 = "";
        String param2 = "";
        String param3 = "";
        String param4 = "";
        String param5 = "";
        String temp;
        //Implemented Hashing class
        Password p = new Password();
        if (params[0].equals("0")) {

            try {
                //converts text into hash used to store in a plain database
                temp = p.getSaltedHash(params[5]);
            }
            catch (Exception e){
                temp = "";
            }

            param1 = params[1];
            param2 = params[2];
            param3 = params[3];
            param4 = params[4];
            param5 = temp;
        }
        else if (params[0].equals("1")) {

            try {
                //converts text into hash used to store in a plain database
                temp = p.getSaltedHash(params[2]);
            }
            catch (Exception e){
                temp = "";
            }

            param1 = params[1];
            param2 = temp;
        }
        try {
            URL url = new URL(URL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = "";
            if (params[0].equals("0")) {
                post_data = URLEncoder.encode("first_name", "UTF-8") + "=" + URLEncoder.encode(param1, "UTF-8") + "&"
                        + URLEncoder.encode("last_name", "UTF-8") + "=" + URLEncoder.encode(param2, "UTF-8") + "&"
                        + URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(param3, "UTF-8") + "&"
                        + URLEncoder.encode("user_email", "UTF-8") + "=" + URLEncoder.encode(param4, "UTF-8") + "&"
                        + URLEncoder.encode("user_password", "UTF-8") + "=" + URLEncoder.encode(param5, "UTF-8");
            }
            else if (params[0].equals("1")) {
                post_data = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(param1, "UTF-8") + "&"
                        + URLEncoder.encode("user_password", "UTF-8") + "=" + URLEncoder.encode(param2, "UTF-8");
            }
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
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");
    }

    /**
     * Handles what needs to happen depending on what the PHP script returned
     *
     * @param result String
     */
    @Override
    protected void onPostExecute(String result) {
        if (result.equals("-1")) {
            Toast.makeText(context, "Wrong Credentials",
                    Toast.LENGTH_SHORT).show();
            Intent myIntent = new Intent(context, MainActivity.class);
            context.startActivity(myIntent);
        }
        else if (result.equals("Account Created")) {
            Intent myIntent = new Intent(context, MainActivity.class);
            context.startActivity(myIntent);
        }
        else if (result.equals("Error!")) {
            Intent myIntent = new Intent(context, CreateUser.class);
            context.startActivity(myIntent);
        }
        else {
            Toast.makeText(context,
                    "Login Successful", Toast.LENGTH_SHORT).show();

            Intent myIntent = new Intent(context, HomePageActivity.class);
            myIntent.putExtra("a", result);
            context.startActivity(myIntent);
        }
    }

    @Override
    protected void onProgressUpdate(Void... values){
        super.onProgressUpdate(values);
    }
}
