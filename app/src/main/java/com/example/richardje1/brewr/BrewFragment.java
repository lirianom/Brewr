package com.example.richardje1.brewr;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

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

import static android.R.attr.fragment;

/**
 * Created by richardje1 on 3/5/17.
 */

public class BrewFragment extends Fragment {
    private Brew mBrew;
    private TextView mTitleField;
    private TextView mBrewDate;
    private TextView mUsername;
    private TextView mBrewMethod;
    private TextView mBrewDescription;
    private TextView mBrewLikes;
    private FloatingActionButton mCommentButton;
    private ListView mCommentList;
    private Brew b;
    private ArrayAdapter<String> listAdapter ;
    Context c;

    private static final String ARG_SECTION_NUMBER = "section_number";
    public static final String EXTRA_BREW_ID = "brewr.CRIME_ID";
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
        UUID brewId = (UUID) getActivity().getIntent()
                .getSerializableExtra(BrewActivity.EXTRA_BREW_ID);
        mBrew = BrewLab.get(getActivity()).getBrew(brewId);


        b = (Brew) getActivity().getIntent().getSerializableExtra("Brew");
        c = this.getContext();
        CommentWorker cw = new CommentWorker();
        cw.execute(b.getmAID());
     /**  // Create and populate a List of planet names.
        String[] comments = new String[] { "User 1 \n    this is the text data", "User 2 \n    this is the text data"};
        ArrayList<String> commentList = new ArrayList<String>();
        commentList.addAll( Arrays.asList(comments) );

        // Create ArrayAdapter using the planet list.
        listAdapter = new ArrayAdapter<String>(this, R.layout.comment_fragment2, commentList);
**/
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_brew, parent, false);
        mTitleField = (TextView)v.findViewById(R.id.brew_title);
        mTitleField.setText(b.getmTitle());
        mBrewDate = (TextView)v.findViewById(R.id.brew_date);
        mCommentList = (ListView)v.findViewById(R.id.comment_list);

        mBrewDate.setText(b.getmDate());
        mUsername = (TextView)v.findViewById(R.id.username);
        mUsername.setText(b.getmUserName());
        mBrewMethod = (TextView)v.findViewById(R.id.brew_method);
        mBrewMethod.setText(b.getmMethod());
        mBrewDescription = (TextView)v.findViewById(R.id.brew_description);
        mBrewDescription.setText(b.getmText());
        mBrewLikes = (TextView)v.findViewById(R.id.likes);
        mBrewLikes.setText(b.getmLikes());
        mCommentButton = (FloatingActionButton)v.findViewById(R.id.add_comment);
        mCommentList = (ListView)v.findViewById(R.id.comment_list);
        mCommentButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String activityID; // add activity #
                //Intent myIntent = new Intent(v.getContext(), HomePageAllActivity.class);
                Intent myIntent = new Intent(v.getContext(), CreateComment.class);
                myIntent.putExtra("a",b);
                //myIntent.putExtra("a", activityID);
                startActivityForResult(myIntent, 0);
                //finish();


            }

        });



        // Create and populate a List of planet names.
        String[] comments = new String[] { "User 1 \n    this is the text data", "User 2 \n    this is the text data", "User 2 \n    this is the text data",
                "User 2 \n    this is the text data","User 2 \n    this is the text data","User 2 \n    this is the text data","User 2 \n    this is the text data",
                "User 2 \n    this is the text data","User 2 \n    this is the text data"};
        ArrayList<String> commentList = new ArrayList<String>();
        commentList.addAll( Arrays.asList(comments) );
        //Context c = new Context().c;
        // Create ArrayAdapter using the planet list.
        listAdapter = new ArrayAdapter<String>(this.getContext(), R.layout.comment_fragment2, commentList);
        mCommentList.setAdapter( listAdapter );


        // Set the ArrayAdapter as the ListView's adapter.
    //    mainListView.setAdapter( listAdapter );
    //    mCommentList.add


        /*
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //todo
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBrew.setmTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                //todo
            }
        });*/
        return v;
    }


    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */

    class CommentWorker extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String URL = "http://student.cs.appstate.edu/lirianom/capstone/getComment.php";
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

        @Override
        protected void onPostExecute(String result) {
            String[] parts = result.split("%`%");
            String[] nA = new String[parts.length/2];
            for (int i = 0; i < nA.length; i+=2) {
                String temp = parts[i];
                temp += "\n    " + parts[i + 1];
                nA[i] = temp;
            }
            ArrayList<String> commentList = new ArrayList<String>();
            commentList.addAll( Arrays.asList(nA) );
            //Context c = new Context().c;
            //Create ArrayAdapter using the planet list.
            listAdapter = new ArrayAdapter<String>(c, R.layout.comment_fragment2, commentList);
            mCommentList.setAdapter( listAdapter );
        }

    }


}
 // 153