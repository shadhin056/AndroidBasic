package com.example.shadhin.helloworldonlyjava.util;

import android.app.Application;

public class GlobalVariable extends Application {

    private String userEmail="";
    private String username="";
    private String sessionid="";
    private String cuscode="";
    private String waletid="";
    private String currentbalance="";
    private String mobileno="";
    private String email="";

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public String getCuscode() {
        return cuscode;
    }

    public void setCuscode(String cuscode) {
        this.cuscode = cuscode;
    }

    public String getWaletid() {
        return waletid;
    }

    public void setWaletid(String waletid) {
        this.waletid = waletid;
    }

    public String getCurrentbalance() {
        return currentbalance;
    }

    public void setCurrentbalance(String currentbalance) {
        this.currentbalance = currentbalance;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }
}
