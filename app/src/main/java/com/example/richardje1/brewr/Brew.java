package com.example.richardje1.brewr;

import java.io.Serializable;
import java.util.UUID;

/**
 * Brew is the object that holds all the information needed to process and
 * display a post on the page.
 *
 * Contains getters and setters for al the fields
 *
 * @Author Jacob Richard
 * @Author Martin Liriano
 * @Version 1.0
 */
public class Brew implements Serializable {

    //All the fields needed for the Brew
    private UUID mId;
    private String mViewerID;
    private String mAID;
    private String mUID;
    private String mTitle;
    private String mText;
    private String mRoaster;
    private String mBean;
    private String mLikes;
    private String mMethod;
    private String mDate;
    private String mUserName;

    public String getmViewerID() {
        return mViewerID;
    }

    public void setmViewerID(String mViewerID) {
        this.mViewerID = mViewerID;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getmText() {
        return mText;
    }

    public void setmText(String mText) {
        this.mText = mText;
    }

    public String getmAID() {
        return mAID;

    }

    public void setmAID(String mAID) {
        this.mAID = mAID;
    }

    public String getmUID() {
        return mUID;
    }

    public String getmRoaster() {
        return mRoaster;
    }

    public void setmRoaster(String mRoaster) {
        this.mRoaster = mRoaster;
    }

    public String getmBean() {
        return mBean;
    }

    public void setmBean(String mBean) {
        this.mBean = mBean;
    }

    public String getmLikes() {
        return mLikes;
    }

    public void setmLikes(String mLikes) {
        this.mLikes = mLikes;
    }

    public String getmMethod() {
        return mMethod;
    }

    public void setmMethod(String mMethod) {
        this.mMethod = mMethod;
    }

    public void setmUID(String mUID) {
        this.mUID = mUID;
    }

    public Brew(){
        mId = UUID.randomUUID();
    }

    public void setmUserName(String user){
        mUserName = user;
    }

    public String getmUserName(){
        return mUserName;
    }

    public String getmTitle(){
        return mTitle;
    }

    public void setmTitle(String title){
        mTitle = title;
    }

    public UUID getId(){
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }
}
