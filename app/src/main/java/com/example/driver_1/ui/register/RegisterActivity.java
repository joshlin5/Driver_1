package com.example.driver_1.ui.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.driver_1.R;
import com.example.driver_1.data.loggedInDriver.MainActivity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button registerButton = findViewById(R.id.register2);

        // The Edit Text that I need to get the changed data from
        EditText username = this.findViewById(R.id.usernameEditTextRegister);
        EditText password = this.findViewById(R.id.passwordEditTextRegister);
        EditText address = this.findViewById(R.id.addressEditTextRegister);
        EditText phoneNumber = this.findViewById(R.id.phoneNumberEditTextRegister);
        EditText email = this.findViewById(R.id.emailEditTextRegister);
        EditText age = this.findViewById(R.id.ageEditTextRegister);
        SharedPreferences prefs = getSharedPreferences("myPrefs.xml", MODE_PRIVATE);
        //Spinner gender = this.findViewById(R.id.genderChoiceRegister);
        //gender.setOnItemClickListener(this);
        // Create an ArrayAdapter using the string array and a default spinner layout
        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
        //        R.array.genderOptions, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        //gender.setAdapter(adapter);

        // When the user has filled out registration and is ready to actually register. Need to add
        // checks to make sure all fields have been filled.
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("email", email.getText().toString());
                editor.putString("username", username.getText().toString());
                editor.putString("address", address.getText().toString());
                editor.putString("phoneNumber", phoneNumber.getText().toString());
                editor.putInt("age", Integer.parseInt(age.getText().toString()));
                editor.apply();
                registerCall(v.getContext(), password.getText().toString());
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void registerCall(Context c, String pass) {
        RequestQueue mRequestQueue ;
        mRequestQueue = Volley.newRequestQueue(c);
        // can be launched in a separate asynchronous job
        String url = "https://driver1-web-app.herokuapp.com/api/drivers/";
        SharedPreferences prefs = getSharedPreferences("myPrefs.xml", MODE_PRIVATE);
        // Instantiate the RequestQueue.
        // Getting data from the Edit Text and putting it in the Bundle
        Bundle result = new Bundle();
        JSONObject body = new JSONObject();
        String username = prefs.getString("username", "failed");
        String address = prefs.getString("address", "failed");
        String phoneNumber = prefs.getString("phoneNumber", "failed");
        String qualification = "temp";
        try {
            //String emailInput = email.getText().toString();
            //String ageInput = age.getText().toString();
            if (!username.equals("") && username.length() > 0) {
                result.putString("username", username);
                body.put("name", username);
            } else {
                result.putString("username", "");
                body.put("name", prefs.getString("username", "ERROR"));
            }

            if (!address.equals("") && address.length() > 0) {
                result.putString("address", address);
                body.put("address", address);
            } else {
                result.putString("address", "");
                body.put("address", prefs.getString("address", "ERROR"));
            }

            if (!phoneNumber.equals("") && phoneNumber.length() > 0) {
                result.putString("phoneNumber", phoneNumber);
                body.put("phone", phoneNumber);
            } else {
                result.putString("phoneNumber", "");
                body.put("phone", prefs.getString("phoneNumber", "ERROR"));
            }

                            /*if(!emailInput.equals("") && emailInput.length() > 0) {
                                result.putString("email", emailInput);
                                editor.putString("phoneNumber", emailInput);
                                //body.put("phone", emailInput);
                            }
                            else {
                                result.putString("email", "");
                                //body.put("phone", prefs.getString("phoneNumber", "ERROR"));
                            }*/

            // Not sync with pref file and web server
            /*if (!ageInput.equals("") && ageInput.length() > 0) {
                result.putString("age", ageInput);
                //editor.putString("age", ageInput);
                //body.put("age", ageInput);
            } else {
                result.putString("age", "");
                //body.put("age", prefs.getString("age", "ERROR"));
            }*/

            // Not sync with pref file and web server
            if (!qualification.equals("") && qualification.length() > 0) {
                result.putString("qualifications", qualification);
                //editor.putString("age", qualiInput);
                body.put("qualifications", qualification);
            } else {
                result.putString("qualifications", "");
                body.put("qualifications", "None");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Not sync with web server
        //result.putString("gender", genderResult);


        // Sending the data to the parent fragment (the HomeFragment.java)
        //getParentFragmentManager().setFragmentResult("EditProfileResult", result);


        String driverUrl = Uri.parse(url).buildUpon().build().toString();
        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.POST, driverUrl, body, response -> {
                }, error -> {
                });
        mRequestQueue.add(request);
    }

}