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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    String genderResult = "Male";
    private final String AUTHENTICATE_BASE_URL = "https://driver1-web-app.herokuapp.com/api/authenticate/";
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
        EditText qualifications = this.findViewById(R.id.qualificationEditTextRegister);
        SharedPreferences prefs = getSharedPreferences("myPrefs.xml", MODE_PRIVATE);

        Spinner gender = (Spinner) this.findViewById(R.id.genderChoiceRegister);
        gender.setOnItemSelectedListener(this);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.genderOptions, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        gender.setAdapter(adapter);

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
                editor.putString("age", age.getText().toString());
                editor.putString("gender", genderResult);
                editor.putString("qualifications", qualifications.getText().toString());
                editor.apply();
                registerCall(v.getContext(), password.getText().toString());
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void registerCall(Context c, String pass) {
        RequestQueue mRequestQueue = Volley.newRequestQueue(c);
        // can be launched in a separate asynchronous job
        String url = "https://driver1-web-app.herokuapp.com/api/drivers/";
        SharedPreferences prefs = getSharedPreferences("myPrefs.xml", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        // Instantiate the RequestQueue.
        JSONObject body = new JSONObject();
        String username = prefs.getString("username", "failed");
        String address = prefs.getString("address", "failed");
        String phoneNumber = prefs.getString("phoneNumber", "failed");
        String emailPost = prefs.getString("email", "ERROR");
        String agePost = prefs.getString("age", "ERROR");
        String genderPost = prefs.getString("gender", "ERROR");
        String qualification = prefs.getString("qualifications", "ERROR");
        try {
                body.put("name", username);
                body.put("address", address);
                body.put("phone", phoneNumber);
                body.put("email", emailPost);
                body.put("password", pass);
                body.put("age", agePost);
                body.put("driver_gender", genderPost);
                body.put("qualifications", qualification);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String driverUrl = Uri.parse(url).buildUpon().build().toString();
        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.POST, driverUrl, body, response -> {
                    Toast.makeText(this, "Sending Registration Data...", Toast.LENGTH_SHORT);
                }, error -> {});
        mRequestQueue.add(request);

        // Make another authenticate request to get driver id
        url = Uri.parse(AUTHENTICATE_BASE_URL).buildUpon().build().toString();
        JSONObject body2 = new JSONObject();
        try {
            //POST JSON body
            body2.put("email", emailPost);
            body2.put("password", pass);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Requests the data and puts it on the queue
        JsonObjectRequest request2 = new JsonObjectRequest
                (Request.Method.POST, url, body, response -> {
                    try {
                        editor.putString("id", response.getString("id"));
                        editor.putString("sponsor", null);
                        editor.putInt("points", 0);
                        editor.apply();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {}
                );
        mRequestQueue.add(request2);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        genderResult = (String) parent.getItemAtPosition(position);
    }


    public void onNothingSelected(AdapterView<?> parent) {
        // TODO: Implement Nothing
    }
}