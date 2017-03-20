package com.example.richardje1.brewr;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import java.util.UUID;

/**
 * Created by richardje1 on 3/5/17.
 */

public class BrewActivity extends SingleFragmentActivity{

    public static final String EXTRA_BREW_ID =
            "com.example.richardje1.brewr.brew_id";

    public static Intent newIntent(Context packageContext, UUID brewId){
        Intent intent = new Intent(packageContext, BrewActivity.class);
        intent.putExtra(EXTRA_BREW_ID, brewId);
        return intent;
    }

    @Override
    protected Fragment createFragment(){
        //return new BrewFragment();
        UUID brewId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_BREW_ID);
        return BrewFragment.newInstance(brewId);
    }
}
