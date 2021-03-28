package com.example.driver_1.data;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.driver_1.data.loggedInDriver.MainActivity;
import com.example.driver_1.data.model.LoggedInUser;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    private static class myParams {
        String username;
        String password;
        Context c;

        myParams(String u, String p, Context c){
            this.username = u;
            this.password = p;
            this.c = c;
        }
    }


    public Result<LoggedInUser> login(String userInfo) {

        try {

            LoggedInUser fakeUser =
                    new LoggedInUser(
                            userInfo);
            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }



    public void logout() {
        // TODO: revoke authentication
    }

}