package com.example.driver_1.ui.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.driver_1.R;
import com.example.driver_1.data.loggedInDriver.MainActivity;

import org.jetbrains.annotations.NotNull;
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

    private void registerCall(Context c, String pass){
        // can be launched in a separate asynchronous job
        String url = "https://driver1-web-app.herokuapp.com/api/drivers/";
        SharedPreferences prefs = getSharedPreferences("myPrefs.xml", MODE_PRIVATE);
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

                        String a = response;

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                Log.d("TAG", ""+error.getMessage()+","+error.toString());

            }
        }) {
            // The object to be sent with the post request is constructed here
            @NotNull
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", prefs.getString("email", "failed to locate email"));
                params.put("password", pass);
                return params;
            }
        };

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

}