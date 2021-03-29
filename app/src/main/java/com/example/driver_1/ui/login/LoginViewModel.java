package com.example.driver_1.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.Patterns;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.driver_1.data.LoginRepository;
import com.example.driver_1.data.Result;
import com.example.driver_1.data.loggedInDriver.MainActivity;
import com.example.driver_1.data.model.LoggedInUser;
import com.example.driver_1.R;

import java.util.HashMap;
import java.util.Map;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password, Context c) {
        // can be launched in a separate asynchronous job
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

                        //String temp = obj.toString();

                        getUserInfo(response, c);

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
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null;
    }

    public void getUserInfo(String userId, Context c){
        String url = "https://driver1-web-app.herokuapp.com/api/drivers/" + userId;
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(c);
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // get JSONObject from JSON file
                        // Display the response string.

                        // get JSONObject from JSON file
                        //JSONObject obj = new JSONObject(response.toString());

                        //String temp = obj.toString();

                        // Call with response as JSON will contain the driver info
                        Result<LoggedInUser> result = loginRepository.login(response);
                        LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
                        loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
                        c.startActivity(new Intent(c.getApplicationContext(), MainActivity.class));


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                Log.d("TAG", "" + error.getMessage() + "," + error.toString());

            }
        });


// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}