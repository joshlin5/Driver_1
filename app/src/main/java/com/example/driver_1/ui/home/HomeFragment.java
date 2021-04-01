package com.example.driver_1.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.driver_1.R;

import org.json.JSONArray;
import org.json.JSONException;
//import com.example.driver_1.ui.editProfile.EditProfileFragment;
// Purchase status
public class HomeFragment extends Fragment{

    // Buttons in fragment_home.xml
    Button resetPasswordButton, editProfileButton, applySponsor;
    // TextViews that need to be edited after edit profile
    TextView usernameText, addressText, phoneNumberText, emailText, ageText, genderText, sponsorText;
    // Strings for the TextViews that need to be changed
    String username, address, phoneNumber, email, age, gender, sponsor;

    // Initializes Buttons and TextViews
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Getting the layout associated with this file
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        SharedPreferences prefs = this.getActivity().getSharedPreferences("myPrefs.xml", Context.MODE_PRIVATE);
        // Initialize the TextViews
        usernameText = root.findViewById(R.id.usernameTextView);
        usernameText.setText(prefs.getString("username", "username"));
        addressText = root.findViewById(R.id.addressTextView);
        addressText.setText(prefs.getString("address", "address"));
        phoneNumberText = root.findViewById(R.id.phoneNumberTextView);
        phoneNumberText.setText(prefs.getString("phoneNumber", "111-111-1111"));
        emailText = root.findViewById(R.id.emailTextView);
        emailText.setText(prefs.getString("email", "email"));
        ageText = root.findViewById(R.id.ageTextView);
        age = String.valueOf(prefs.getInt("age", 0));
        ageText.setText(age);
        genderText = root.findViewById(R.id.genderTextView);

        sponsorText = root.findViewById(R.id.sponsor);


        // Initialize the Buttons
        editProfileButton = root.findViewById(R.id.editProfile);
        resetPasswordButton = root.findViewById(R.id.resetPassword);
        applySponsor = root.findViewById(R.id.applyForSponsor);

        // onCLick Listener for the resetPasswordButton
        resetPasswordButton.setOnClickListener(v -> {
            // Do something in response to button click
        });

        // onCLick Listener for the resetPasswordButton
        applySponsor.setOnClickListener(v -> new ApplySponsorFragment().show(getChildFragmentManager(), "Sponsor Application"));

        // onCLick Listener for the editProfileButton
        // Shows the fragment_edit_profile.xml using the EditProfileFragment.java file
        // Sets a tag for future identification
        editProfileButton.setOnClickListener(v -> new EditProfileFragment().show(getChildFragmentManager(), "Edit Profile"));

        // Sets result data from fragment_edit_profile.xml EditText field to the corresponding TextViews in fragment_home.xml
        getChildFragmentManager().setFragmentResultListener("EditProfileResult", this, (requestKey, bundle) -> {
            // Data from fragment_edit_profile.xml EditText fields
            username = bundle.getString("username");
            address = bundle.getString("address");
            phoneNumber = bundle.getString("phoneNumber");
            email = prefs.getString("email", bundle.getString("email"));
            age = bundle.getString("age");
            gender = bundle.getString("gender");

            // Sets data to corresponding TextViews in fragment_home.xml
            if(!username.equals(""))
                usernameText.setText("Username: " + username);
            if(!address.equals(""))
                addressText.setText("Address: " + address);
            if(!phoneNumber.equals(""))
                phoneNumberText.setText("Phone Number: " + phoneNumber);
            if(!email.equals(""))
                emailText.setText("Email: " + email);
            if(!age.equals(""))
                ageText.setText("Age: " + age);
            genderText.setText("Gender: " + gender);
        });
        return root;
    }


}