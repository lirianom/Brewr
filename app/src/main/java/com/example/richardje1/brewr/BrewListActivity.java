package com.example.richardje1.brewr;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

/**
 * Created by richardje1 on 3/6/17.
 */

public class BrewListActivity extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment(){

        return new BrewListFragment();
    }
}
