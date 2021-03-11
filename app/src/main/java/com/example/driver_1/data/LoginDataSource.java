package com.example.driver_1.data;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.driver_1.data.model.LoggedInUser;

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

    public Result<LoggedInUser> login(String username, String password) {
        /*String url = "https://driver1-web-app.herokuapp.com/api/authenticate";
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this.getContext());
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("email", username);
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
        try {
            jsonBody.put("password", password);
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }
        final String requestBody = jsonBody.toString();
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // get JSONObject from JSON file
                        // Display the response string.
                        try {
                            // get JSONObject from JSON file
                            JSONArray obj = new JSONArray(response.toString());
                            // fetch JSONObject named employee
                            String temp = obj.toString();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //textView.setText("That didn't work!");
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("_username", "admin");
                params.put("_password", "root");
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "multipart/form-data");
                return headers;
            }
        };


// Add the request to the RequestQueue.
        queue.add(stringRequest);*/
        try {
            // TODO: handle loggedInUser authentication

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