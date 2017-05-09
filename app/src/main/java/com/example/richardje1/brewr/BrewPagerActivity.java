package com.example.richardje1.brewr;

import java.util.ArrayList;
import java.util.UUID;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

/**
 * BrewPagerActivity NOT CALLED ANYWHERE
 *
 * @Author Jacob Richard
 * @Version 1.0
 */
public class BrewPagerActivity extends FragmentActivity {
    ViewPager mViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        final ArrayList<Brew> brews = BrewLab.get(this).getBrews();
        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public int getCount() {
                return brews.size();
            }
            @Override
            public Fragment getItem(int pos) {
                UUID brewId =  brews.get(pos).getId();
                return BrewFragment.newInstance(brewId);
            }
        });

        UUID brewId = (UUID)getIntent()
                .getSerializableExtra(BrewFragment.EXTRA_BREW_ID);
        for (int i = 0; i < brews.size(); i++) {
           if (brews.get(i).getId().equals(brewId)) {
                     mViewPager.setCurrentItem(i);
           }
                break;
        }
    }

}

