package com.example.richardje1.brewr;

import java.util.UUID;

/**
 * Created by richardje1 on 4/10/17.
 */

public class BrewrUser {

    private String fName;
    private String lName;
    // may change to int
    private UUID userID;
    private String userName;
    private String email;
    private String password;

    public BrewrUser(){
        fName = "";
        lName = "";
        userID = new UUID(0,31);
        userName = "";
        String email = "";
        String password = "";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public UUID getUserID() {
        return userID;
    }

    public void setUserID(UUID userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String description;


}
