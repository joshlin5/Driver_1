package com.example.driver_1.data.model;

import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.driver_1.data.loggedInDriver.MainActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String userId;
    private String displayName;
    private String address;
    private String phoneNumber;
    private String email;
    private int age;
    private int gender;
    private int points;


    public LoggedInUser(String userInfo) {
        this.userId = userInfo;

    }


    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }
}