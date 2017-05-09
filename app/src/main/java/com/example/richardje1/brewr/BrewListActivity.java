package com.example.richardje1.brewr;

import android.support.v4.app.Fragment;

/**
 * BrewListActivity creates a new BrewListFragment
 *
 * @Author Martin Liriano
 * @Author Jacob Richard
 * @Version 1.0
 */
public class BrewListActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment(){

        return new BrewListFragment();
    }
}
