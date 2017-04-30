package com.example.richardje1.brewr;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

//import static android.content.Context.MODE_PRIVATE;

/**
 * Created by richardje1 on 3/6/17.
 */

public class BrewLab {
    private ArrayList<Brew> mBrews;

    private static BrewLab sBrewLab1;
    private static BrewLab sBrewLab2;
    private Context mAppContext;
    private static String[] friends;
    private static String self;

    private BrewLab(Context appContext){
        mAppContext = appContext;
        mBrews = new ArrayList<Brew>();

        ActivityWorker aw = new ActivityWorker();
        String query = friendsQuery();
        aw.execute(query);

        //todo
        /**
        for(int i = 0; i <15; i++){
            Brew b = new Brew();
            b.setTitle("Activity #" + i);
            b.setUser("Martin");
            mBrews.add(b);
        }
         **/

    }

    public String friendsQuery() {
        String temp = "post.user_id = ";
        for (int i = 0; i < friends.length ; i++){
            temp += friends[i];
            if (i != friends.length - 1) {
                temp += " OR post.user_id = ";
            }
        }
        return temp;
    }

    public static BrewLab get(Context c, String[] f, String s){
        boolean isSBL1 = true;
        if(sBrewLab1 == null){
            isSBL1 = true;
            friends = Arrays.copyOf(f, f.length);
            self = s;
            sBrewLab1 = new BrewLab(c.getApplicationContext());
            return sBrewLab1;
        }
        else if (sBrewLab2 == null){
            isSBL1 = false;
            friends = Arrays.copyOf(f, f.length);
            self = s;
            sBrewLab2 = new BrewLab(c.getApplicationContext());
            return sBrewLab2;
        }
        return isSBL1?sBrewLab1:sBrewLab2;
    }

    public static BrewLab get(Context c) {
        if(sBrewLab1 == null){
            sBrewLab1 = new BrewLab(c.getApplicationContext());
            return sBrewLab1;
        }
        else if (sBrewLab2 == null){
            sBrewLab2 = new BrewLab(c.getApplicationContext());
            return sBrewLab2;
        }
        return sBrewLab1;
    }

    public ArrayList<Brew> getBrews(){
        return mBrews;
    }
    public Brew getBrew(UUID id){
        for (Brew b : mBrews){
            if(b.getId().equals(id))
                return b;
        }
        return null;
    }

    public void addBrew(Brew b){
        mBrews.add(b);
    }

    class ActivityWorker extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String URL = "http://student.cs.appstate.edu/lirianom/capstone/getPost.php";
            String id = params[0];
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
                String post_data = URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
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
            String[] parts = result.split("%`%");
            int currentBrew = -1;
            for (int i = 0; i < parts.length; i++)
            {
                Brew b;
                if(i / 10 != currentBrew) {
                    b = new Brew();
                    b.setmViewerID(self);
                    b.setmUserName(parts[i]);
                    b.setmUID(parts[i+1]);
                    b.setmAID(parts[i+2]);
                    b.setmTitle(parts[i+3]);
                    b.setmText(parts[i+4]);
                    b.setmRoaster(parts[i+5]);
                    b.setmBean(parts[i+6]);
                    b.setmDate(parts[i+7]);
                    b.setmLikes(parts[i+8]);
                    b.setmMethod(parts[i+9]);
                    currentBrew++;
                    mBrews.add(b);
                }
            }
        }

    }
}
