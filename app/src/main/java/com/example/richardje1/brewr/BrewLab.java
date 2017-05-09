package com.example.richardje1.brewr;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;

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
import java.util.UUID;

/**
 * BrewLab is what collects and holds the Brews that are created.
 *
 * @Author Martin Liriano
 * @Author Jacob Richard
 * @Version 1.0
 */
public class BrewLab {

    private ArrayList<Brew> mBrews;
    private static BrewLab sBrewLab1;
    private static BrewLab sBrewLab2;
    private static String[] friends;
    private static String self;

    private BrewLab(Context appContext){
        mBrews = new ArrayList<Brew>();
        sBrewLab1 = null;
        sBrewLab2 = null;

        ActivityWorker aw = new ActivityWorker();
        String query = friendsQuery();
        aw.execute(query);
    }

    /**
     * friendsQuery turns the friends array into a string where
     * it can get queried from.
     *
     * @return temp String - returns the query ready string
     */
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

    /**
     *  get is a getter for the entire class. Returns a brewLab with the context and friends
     *  that are passed in from BrewListFragment.
     *
     *  get returns two different BrewLabs for self page fragment and friends page fragment
     *
     * @param c Context - the context that is passed in from the BrewListFragment
     * @param friendsArg String[] - friends array that is passed from BrewListFragment
     * @param selfArg String - userID that is passed in from BrewListFragment
     *
     * @return BrewLab
     */
    public static BrewLab get(Context c, String[] friendsArg, String selfArg){
        boolean isSBL1 = true;
        if(sBrewLab1 == null){
            isSBL1 = true;
            friends = Arrays.copyOf(friendsArg, friendsArg.length);
            self = selfArg;
            sBrewLab1 = new BrewLab(c.getApplicationContext());
            return sBrewLab1;
        }
        else if (sBrewLab2 == null){
            isSBL1 = false;
            friends = Arrays.copyOf(friendsArg, friendsArg.length);
            self = selfArg;
            sBrewLab2 = new BrewLab(c.getApplicationContext());
            return sBrewLab2;
        }
        return isSBL1?sBrewLab2:sBrewLab1;
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

    /**
     * getBrews returns a list of Brews.
     *
     * @return ArrayList<Brew>
     */
    public ArrayList<Brew> getBrews(){
        return mBrews;
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
         * doInBackground runs after execute is called. This calls the getPost.php
         * script and gets whats passed in through the bufferedWriter.
         *
         * @param params params[0] = user_id
         * @return result String - result is what gets echo'ed from the PHP script
         */
        @Override
        protected String doInBackground(String... params) {
            String URL = "http://student.cs.appstate.edu/lirianom/capstone/getPost.php";
            String id = params[0];
            try {
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

        /**
         * onPostExecute is the last step of execute. Instantiates the
         * Brew objects and adds them to the mBrew list.
         *
         * @param result
         */
        @Override
        protected void onPostExecute(String result) {
            if (!result.equals("0 results")) {
                String[] parts = result.split("%`%");
                int currentBrew = -1;
                for (int i = 0; i < parts.length; i++) {
                    Brew b;
                    if (i / 10 != currentBrew) {
                        b = new Brew();
                        b.setmViewerID(self);
                        b.setmUserName(parts[i]);
                        b.setmUID(parts[i + 1]);
                        b.setmAID(parts[i + 2]);
                        b.setmTitle(parts[i + 3]);
                        b.setmText(parts[i + 4]);
                        b.setmRoaster(parts[i + 5]);
                        b.setmBean(parts[i + 6]);
                        b.setmDate(parts[i + 7]);
                        b.setmLikes(parts[i + 8]);
                        b.setmMethod(parts[i + 9]);
                        currentBrew++;
                        mBrews.add(b);
                    }
                }
            }
        }

    }
}
