package com.example.richardje1.brewr;

import java.util.Date;
import java.util.UUID;

/**
 * Created by richardje1 on 3/5/17.
 */

public class Brew {
    private UUID mId;
    private String mTitle;
    private Date mDate;

    public Brew(){
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public Date getDate(){
        return mDate;
    }

    public void setDate(Date date){

    }

    public String getTitle(){
        return mTitle;
    }

    public void setTitle(String title){
        mTitle = title;
    }

    public UUID getId(){
        return mId;
    }
}
