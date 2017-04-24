package com.example.richardje1.brewr;

import android.app.Activity;
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
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by martin on 2/26/17.
 */

public class BackgroundWorker extends AsyncTask <String, Void, String> {
    Context context;
    AlertDialog alertDialog;
    private String login;
    private String[] URL;
    //MainActivity m;

    BackgroundWorker(Context ctx) {
        login = "";
        context = ctx;
        //this.m = m;
        URL = new String[] {
                "http://student.cs.appstate.edu/lirianom/capstone/register.php",
                "http://student.cs.appstate.edu/lirianom/capstone/login.php",
                "http://student.cs.appstate.edu/lirianom/capstone/post.php",
                "http://student.cs.appstate.edu/lirianom/capstone/getFollow.php"
        };
    }

    public String retURL(String index){
        int i = Integer.parseInt(index);
        return URL[i];
    }

    /**
    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String register_url = "http://student.cs.appstate.edu/lirianom/capstone/register.php";
        String fetch_url = "http://student.cs.appstate.edu/lirianom/capstone/fetch.php";
        String login_url = "http://student.cs.appstate.edu/lirianom/capstone/login.php";
        if (type.equals("register")) {
            try {
                String user_name = params[1];
                String password = params[2];
                URL url = new URL(register_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&"
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
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
        } else if (type.equals("fetch")) {
            try {
                String user_id = "";
                String user_name = params[1];
                String password = "";
                URL url = new URL(fetch_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8");
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
        } else if (type.equals("login")) {
            try {
                String user_name = params[1];
                String password = params[2];
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&"
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
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
                //setLogin(result);
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }*/
    @Override
    protected String doInBackground(String ...params) {
        //String type = params[0];
        //int index = Integer.parseInt(params[1]);
        String URL = retURL(params[0]);
        String param1 = "";
        String param2 = "";
        String param3 = "";
        String param4 = "";
        String param5 = "";
        String param6 = "";
        String param7 = "";
        String param8 = "";
        if (params[0].equals("0")) {
            param1 = params[1];
            param2 = params[2];
            param3 = params[3];
            param4 = params[4];
            param5 = params[5];
        }
        else if (params[0].equals("1")) {
            param1 = params[1];
            param2 = params[2];
        }
        else if (params[0].equals("3")) {
            param1 = params[1];
        }
        //int user_id = Integer.parseInt(params[3]);
        try {
            //String user_name = params[2];
            //String password = params[3];
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
            else if (params[0].equals("3")) {
                post_data = URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(param1, "UTF-8");
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


    @Override
    protected void onPostExecute(String result) {
        if (result.equals("-1")) {
            Toast.makeText(context, "Wrong Credentials",
                    Toast.LENGTH_SHORT).show();
            Intent myIntent = new Intent(context, MainActivity.class);
            context.startActivity(myIntent);
        }
        else if (result.equals("Account Created")) {
            Intent myIntent = new Intent(context, HomePageActivity.class);
            context.startActivity(myIntent);
        }
        else {
            //System.out.println(result);
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
