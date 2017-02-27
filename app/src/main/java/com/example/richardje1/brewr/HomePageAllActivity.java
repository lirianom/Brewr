package com.example.richardje1.brewr;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import static android.graphics.Color.LTGRAY;
import static android.graphics.Color.WHITE;


/**
 * Created by richardje1 on 2/24/17.
 */

public class HomePageAllActivity extends FragmentActivity {
    /** Called when the activity is first created. */

    private static final int NUM_PAGES = 2;
    private static final int ALL_PAGE_NUM = 0;
    private static final int SELF_PAGE_NUM = 1;

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private TextView allActivityTextButton;
    private TextView selfActivityTextButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screens_slide);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        allActivityTextButton = (TextView) findViewById(R.id.all_activity_textbutton);
        selfActivityTextButton = (TextView) findViewById(R.id.self_activity_textbutton);

        allActivityTextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "all",
                        Toast.LENGTH_SHORT).show();
            }
        });

        selfActivityTextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "self",
                        Toast.LENGTH_SHORT).show();
            }
        });


    }
    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        
        @Override
        public Fragment getItem(int position) {
            return new ScreenSlidePageFragment();
        }

        /**
        @Override
        public Fragment getItem(int i) {
            Fragment fragment = new ScreenSlidePageFragment();
            Bundle args = new Bundle();
            // Our object is just an integer :-P
            args.putInt(ScreenSlidePagerAdapter.ARG_OBJECT, i + 1);
            fragment.setArguments(args);
            return fragment;
        }
         **/


        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

}
