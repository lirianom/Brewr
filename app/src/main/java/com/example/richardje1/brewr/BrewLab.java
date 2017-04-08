package com.example.richardje1.brewr;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by richardje1 on 3/6/17.
 */

public class BrewLab {
    private ArrayList<Brew> mBrews;

    private static BrewLab sBrewLab;
    private Context mAppContext;

    private BrewLab(Context appContext){
        mAppContext = appContext;
        mBrews = new ArrayList<Brew>();


        /**
        for(int i = 0; i <50; i++){
            Brew b = new Brew();
            b.setTitle("Activity #" + i);
            mBrews.add(b);
        }
         **/
    }
    public static BrewLab get(Context c){
        if(sBrewLab == null){
            sBrewLab = new BrewLab(c.getApplicationContext());
        }
        return sBrewLab;
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
}
