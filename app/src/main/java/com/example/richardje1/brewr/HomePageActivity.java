package com.example.richardje1.brewr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
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
 * HomePageActivity displays everything for a user all based on their userid that is passed from the
 * BackgroundWorker
 *
 * @Author Martin Liriano
 * @Author Jacob Richard
 * @Version 1.0
 */
public class HomePageActivity extends AppCompatActivity {


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private static String[] friends;
    private static String[] soloFriend;
    private FloatingActionButton mFloatingAddActivity;
    private FloatingActionButton mFloatingAddFriend;
    private FloatingActionButton mFloatingRefresh;
    private ViewPager mViewPager;
    public static String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            userID = b.getString("a");
        }
        FriendWorker fw = new FriendWorker();

        fw.execute(userID);

        ImageView logo = (ImageView)findViewById(R.id.imageView3);

        soloFriend = new String[]{ userID };



        SharedPreferences bb = getSharedPreferences("my_prefs", 0);
        String m = bb.getString("FID", "");

        friends = m.split("-");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mFloatingAddActivity = (FloatingActionButton) findViewById(R.id.add_activity);


        mFloatingAddActivity.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(v.getContext(), CreateBrew.class);
                myIntent.putExtra("a", userID);
                startActivityForResult(myIntent, 0);
                finish();


            }

        });
        mFloatingAddFriend = (FloatingActionButton) findViewById(R.id.add_friend);
        mFloatingAddFriend.setSize(FloatingActionButton.SIZE_MINI);
        mFloatingAddFriend.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(v.getContext(), AddFriend.class);
                myIntent.putExtra("a", userID);
                startActivityForResult(myIntent, 0);
                finish();


            }

        });
        mFloatingRefresh = (FloatingActionButton) findViewById(R.id.refresh);
        mFloatingRefresh.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(v.getContext(), HomePageActivity.class);
                myIntent.putExtra("a", userID);
                startActivityForResult(myIntent, 0);
                finish();


            }

        });


        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);

        //getMenuInflater().inflate(R.menu.menu_home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.add_activity) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void createUser(MenuItem item){
        CreateUser cr = new CreateUser();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static BrewListFragment newInstance(int sectionNumber) {
            BrewListFragment fragment = null;
            if (sectionNumber == 1) {
                fragment = new BrewListFragment();
                fragment.setUserID(userID);
                fragment.setFriends(friends);
            }
            else if (sectionNumber == 2) {
                fragment = new BrewListFragment();
                fragment.setUserID(userID);
                fragment.setFriends(soloFriend);
            }
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_screen_slide_page, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Friends";
                case 1:
                    return "Self";

            }
            return null;
        }
    }

    /**
     * FriendWorker a class that connects with PHP script in order to
     * work with Front-End Android Application.
     *
     * @Author Martin Liriano
     * @Author Jacob Richard
     * @Version 1.0
     */
    class FriendWorker extends AsyncTask<String, Void, String> {


        //Querries for all the user the given user follows
        @Override
        protected String doInBackground(String... params) {
            String URL = "http://student.cs.appstate.edu/lirianom/capstone/getFollow.php";
            String id = params[0];
            try {
                URL url = new URL(URL);
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
            SharedPreferences prefs = getSharedPreferences("my_prefs", MODE_PRIVATE);
            SharedPreferences.Editor edit = prefs.edit();
            edit.putString("FID", result);
            edit.commit();

        }

    }

}