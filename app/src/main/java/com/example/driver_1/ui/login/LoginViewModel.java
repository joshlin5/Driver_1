package com.example.driver_1.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.driver_1.data.LoginRepository;
import com.example.driver_1.data.Result;
import com.example.driver_1.data.loggedInDriver.MainActivity;
import com.example.driver_1.data.model.LoggedInUser;
import com.example.driver_1.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginViewModel extends ViewModel {

    private final String TAG = "LoginViewModel";
    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;
    private final String AUTHENTICATE_BASE_URL = "https://driver1-web-app.herokuapp.com/api/authenticate/";

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
        // Instantiate the RequestQueue.
        RequestQueue mRequestQueue = Volley.newRequestQueue(c);

        String url = Uri.parse(AUTHENTICATE_BASE_URL).buildUpon().build().toString();
        JSONObject body = new JSONObject();
        try {
            //POST JSON body
            body.put("email", username);
            Toast.makeText(c, username, Toast.LENGTH_SHORT);
            body.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Requests the data and puts it on the queue
        JsonObjectRequest request2 = new JsonObjectRequest
                (Request.Method.POST, url, body, response -> {
                    Toast.makeText(c, "Authenticating...", Toast.LENGTH_SHORT);

                    SharedPreferences prefs = c.getSharedPreferences("myPrefs.xml", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("email", username);
                    try {
                        JSONObject obj = response.getJSONObject("response");
                        editor.putString("id", Integer.toString(obj.getInt("id")));
                        System.out.println("ID: " + obj.getString("id"));
                        editor.putString("username", obj.getString("name"));
                        System.out.println("USERNAME: " + obj.getString("name"));
                        editor.putString("address", obj.getString("address"));
                        System.out.println("ADDRESS: " + obj.getString("address"));
                        editor.putString("phoneNumber", obj.getString("phone"));
                        System.out.println("PHONE NUMBER: " + obj.getString("phone"));
                        editor.putString("age", "22");
                        if(obj.isNull( "sponsor") || obj.equals(JSONObject.NULL)) {
                            editor.putString("sponsor", null);
                        }
                        else {
                            editor.putString("sponsor", obj.getString("sponsor"));
                        }
                        editor.putInt("points", obj.getInt("credits"));
                        editor.putString("gender", obj.getString("driver_gender"));
                        editor.putString("qualifications", obj.getString("qualifications"));
                        editor.apply();
                        Result<LoggedInUser> result = loginRepository.login(obj.getString("id"));
                        LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
                        loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
                        c.startActivity(new Intent(c.getApplicationContext(), MainActivity.class));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {}
                );
        mRequestQueue.add(request2);
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
}