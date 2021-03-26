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


    public Result<LoggedInUser> login(String username, String password, Context c) {
        final int check = 0;




        try {
            // TODO: handle loggedInUser authentication
            String url = "https://driver1-web-app.herokuapp.com/api/authenticate/";
            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(c);
// Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // get JSONObject from JSON file
                            // Display the response string.

                                // get JSONObject from JSON file
                                //JSONObject obj = new JSONObject(response.toString());
                                // fetch JSONObject named employee
                                //String temp = obj.toString();
                                if(response.equals("good")){
                                    c.startActivity(new Intent(c.getApplicationContext(), MainActivity.class));
                                }
                                else{
                                    //check = 2;
                                }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("TAG", "Error: " + error.getMessage());
                    Log.d("TAG", ""+error.getMessage()+","+error.toString());

                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("email", username);
                    params.put("password", password);
                    return params;
                }
            };

// Add the request to the RequestQueue.
            queue.add(stringRequest);
            LoggedInUser fakeUser =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            "Jane Doe");
            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }

}