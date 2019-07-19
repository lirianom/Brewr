package com.example.richardje1.brewr;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import static android.content.Context.MODE_PRIVATE;

/**
 * BrewFragment creates and instantiates a BrewFragment Object
 *
 * @Author Martin Liriano
 * @Author Jacob Richard
 * @Version 1.0
 */
public class BrewFragment extends Fragment {

    private TextView mTitleField;
    private TextView mBrewDate;
    private TextView mUsername;
    private TextView mBrewMethod;
    private TextView mBrewDescription;
    private TextView mBrewLikes;
    private TextView mRoaster;
    private TextView mBean;
    private Button mLikeButton;
    private FloatingActionButton mCommentButton;
    private ListView mCommentList;
    private Brew b;
    private ArrayAdapter<String> listAdapter ;
    private Context c;
    public static final String EXTRA_BREW_ID = "brewr.BREW_ID";
    private static final String ARG_BREW_ID = "brew_id";

    public static BrewFragment newInstance(UUID brewId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_BREW_ID, brewId);

        BrewFragment fragment = new BrewFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        this.getContext();
        b = (Brew) getActivity().getIntent().getSerializableExtra("Brew");
        c = this.getContext();
        CommentWorker cw = new CommentWorker();
        cw.execute(b.getmAID());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_brew, parent, false);
        mTitleField = (TextView)v.findViewById(R.id.brew_title);
        mTitleField.setText(b.getmTitle());
        mBrewDate = (TextView)v.findViewById(R.id.brew_date);
        mCommentList = (ListView)v.findViewById(R.id.comment_list);
        mRoaster = (TextView)v.findViewById(R.id.roaster_display_actual);
        mRoaster.setText(b.getmRoaster());
        mBean = (TextView)v.findViewById(R.id.bean_display_actual);
        mBean.setText(b.getmBean());
        mBrewDate.setText(b.getmDate());
        mUsername = (TextView)v.findViewById(R.id.username);
        mUsername.setText(b.getmUserName());
        mBrewMethod = (TextView)v.findViewById(R.id.brew_method);
        mBrewMethod.setText(b.getmMethod());
        mBrewDescription = (TextView)v.findViewById(R.id.brew_description);
        mBrewDescription.setText(b.getmText());
        mLikeButton = (Button)v.findViewById(R.id.like_button);
        mLikeButton.setText("Likes: " + b.getmLikes());
        //int likes = Integer.parseInt(b.getmLikes());


        mLikeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                isFound found = new isFound();
                found.execute(b.getmViewerID(), b.getmAID());

                SharedPreferences bb = c.getSharedPreferences("refs", 0);
                String m = bb.getString("ID", "");

                int likes = Integer.parseInt(b.getmLikes());

                if(m.equals("false")){
                    //set likes in database
                            likes++;
                }
                mLikeButton.setText("Likes: " + likes);

                // redundant loop to help speed of display
                if(m.equals("false")){
                    updateLikes uL = new updateLikes();
                    uL.execute(b.getmViewerID(), b.getmAID());
                    Likes l = new Likes();
                    l.execute(b.getmAID(), likes + "");
                }

            }

        });

        mCommentButton = (FloatingActionButton)v.findViewById(R.id.add_comment);
        mCommentList = (ListView)v.findViewById(R.id.comment_list);

        mCommentButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), CreateComment.class);
                myIntent.putExtra("a", b);
                startActivityForResult(myIntent, 0);
            }

        });
        return v;
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
         * doInBackground runs after execute is called. This calls the getComment.php
         * script and gets whats passed in through the bufferedWriter.
         *
         * @param params params[0] = post_id
         * @return result String - result is what gets echo'ed from the PHP script
         */
        @Override
        protected String doInBackground(String... params) {
            String URL = "http://student.cs.appstate.edu/lirianom/capstone/getComment.php";
            String id = params[0];
            try {
                java.net.URL url = new URL(URL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("post_id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
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
         * onPostExecute handles what gets displayed in the comment section
         * of the BrewActivityFragment
         *
         * @param result
         */
        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                String[] parts = result.split("%`%");
                String[] nA = new String[parts.length / 2];
                int j = 0;
                for (int i = 0; i < parts.length - 1; i += 2) {

                    String temp = parts[i];
                    temp += "\n    " + parts[i + 1];
                    nA[j] = temp;
                    j++;
                }
                ArrayList<String> commentList = new ArrayList<String>();
                commentList.addAll(Arrays.asList(nA));
                listAdapter = new ArrayAdapter<String>(c, R.layout.comment_fragment2, commentList);
                mCommentList.setAdapter(listAdapter);
            }
        }
    }

    class isFound extends AsyncTask<String, Void, String> {

        /**
         * doInBackground runs after execute is called. This calls the getComment.php
         * script and gets whats passed in through the bufferedWriter.
         *
         * @param params params[0] = post_id
         * @return result String - result is what gets echo'ed from the PHP script
         */
        @Override
        protected String doInBackground(String... params) {
            String URL = "http://student.cs.appstate.edu/lirianom/capstone/userLiked.php";
            String user_id = params[0];
            String post_id = params[1];
            try {
                java.net.URL url = new URL(URL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(user_id, "UTF-8") + "&"
                        + URLEncoder.encode("post_name", "UTF-8") + "=" + URLEncoder.encode(post_id, "UTF-8");
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
         * onPostExecute handles what gets displayed in the comment section
         * of the BrewActivityFragment
         *
         * @param result
         */
        @Override
        protected void onPostExecute(String result) {
            SharedPreferences prefs = c.getSharedPreferences("refs", MODE_PRIVATE);
            SharedPreferences.Editor edit = prefs.edit();
            edit.putString("ID", result);
            edit.commit();
        }
    }

    class updateLikes extends AsyncTask<String, Void, String> {

        /**
         * doInBackground runs after execute is called. This calls the getComment.php
         * script and gets whats passed in through the bufferedWriter.
         *
         * @param params params[0] = post_id
         * @return result String - result is what gets echo'ed from the PHP script
         */
        @Override
        protected String doInBackground(String... params) {
            String URL = "http://student.cs.appstate.edu/lirianom/capstone/insertUserLikes.php";
            String user_id = params[0];
            String post_id = params[1];
            try {
                java.net.URL url = new URL(URL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(user_id, "UTF-8") + "&"
                        + URLEncoder.encode("post_id", "UTF-8") + "=" + URLEncoder.encode(post_id, "UTF-8");
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
         * onPostExecute handles what gets displayed in the comment section
         * of the BrewActivityFragment
         *
         * @param result
         */
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(c.getApplicationContext(),
                    "Liked!", Toast.LENGTH_SHORT).show();
        }
    }

    class Likes extends AsyncTask<String, Void, String> {

        /**
         * doInBackground runs after execute is called. This calls the getComment.php
         * script and gets whats passed in through the bufferedWriter.
         *
         * @param params params[0] = post_id
         * @return result String - result is what gets echo'ed from the PHP script
         */
        @Override
        protected String doInBackground(String... params) {
            String URL = "http://student.cs.appstate.edu/lirianom/capstone/like.php";
            String post_likes = params[1];
            String post_id = params[0];
            try {
                java.net.URL url = new URL(URL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("post_id", "UTF-8") + "=" + URLEncoder.encode(post_id, "UTF-8") + "&"
                        + URLEncoder.encode("post_likes", "UTF-8") + "=" + URLEncoder.encode(post_likes, "UTF-8");
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
         * onPostExecute handles what gets displayed in the comment section
         * of the BrewActivityFragment
         *
         * @param result
         */
        @Override
        protected void onPostExecute(String result) {
            //Toast.makeText(c.getApplicationContext(),
            //        "Liked!", Toast.LENGTH_SHORT).show();
        }
    }
}