package com.example.driver_1.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.driver_1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
//import com.example.driver_1.ui.editProfile.EditProfileFragment;

public class HomeFragment extends Fragment{

    // Buttons in fragment_home.xml
    Button resetPasswordButton, editProfileButton;
    // TextViews that need to be edited after edit profile
    TextView usernameEditText, addressEditText, phoneNumberEditText, emailEditText, birthdayEditText, genderEditText;
    // Strings for the TextViews that need to be changed
    String username, address, phoneNumber, email, birthday, gender;

    // Initializes Buttons and TextViews
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Getting the layout associated with this file
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize the TextViews
        usernameEditText = root.findViewById(R.id.usernameEditText);
        addressEditText = root.findViewById(R.id.addressEditText);
        phoneNumberEditText = root.findViewById(R.id.phoneNumberEditText);
        emailEditText = root.findViewById(R.id.emailEditText);
        birthdayEditText = root.findViewById(R.id.birthdayEditText);
        genderEditText = root.findViewById(R.id.genderEditText);

        // Initialize the Buttons
        editProfileButton = root.findViewById(R.id.editProfile);
        resetPasswordButton = root.findViewById(R.id.resetPassword);

        // onCLick Listener for the resetPasswordButton
        resetPasswordButton.setOnClickListener(v -> {
            // Do something in response to button click
        });

        // onCLick Listener for the editProfileButton
        // Shows the fragment_edit_profile.xml using the EditProfileFragment.java file
        // Sets a tag for future identification
        editProfileButton.setOnClickListener(v -> new EditProfileFragment().show(getChildFragmentManager(), "Edit Profile"));

        // Sets result data from fragment_edit_profile.xml EditText field to the corresponding TextViews in fragment_home.xml
        getChildFragmentManager().setFragmentResultListener("requestKey", this, (requestKey, bundle) -> {
            // Data from fragment_edit_profile.xml EditText fields
            username = bundle.getString("username");
            address = bundle.getString("address");
            phoneNumber = bundle.getString("phoneNumber");
            email = bundle.getString("email");
            birthday = bundle.getString("birthday");
            gender = bundle.getString("gender");

            // Sets data to corresponding TextViews in fragment_home.xml
            usernameEditText.setText(username);
            addressEditText.setText(address);
            phoneNumberEditText.setText(phoneNumber);
            emailEditText.setText(email);
            birthdayEditText.setText(birthday);
            genderEditText.setText(gender);
        });
        String url = "https://driver1-web-app.herokuapp.com/api/drivers";

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getContext());

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // get JSONObject from JSON file
                        // Display the response string.
                        try {
                            // get JSONObject from JSON file
                            JSONObject obj = new JSONObject(response.toString());
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
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
        return root;
    }


}