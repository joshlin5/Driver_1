package com.example.driver_1.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.driver_1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class HomeFragment extends Fragment{

    int driverID, points;
    int sponsorID = -1;
    // Buttons in fragment_home.xml
    Button editProfileButton, applySponsor;
    // TextViews that need to be edited after edit profile
    TextView usernameText, addressText, phoneNumberText, emailText, ageText, genderText, sponsorText, pointsText, qualiText;
    // Strings for the TextViews that need to be changed
    String username, address, phoneNumber, gender, qualifications, age;
    private RequestQueue mRequestQueue;
    private final String SPONSOR_BASE_URL = "https://driver1-web-app.herokuapp.com/api/sponsors/";

    // Initializes Buttons and TextViews
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Getting the layout associated with this file
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mRequestQueue = Volley.newRequestQueue(getContext());
        SharedPreferences prefs = this.getActivity().getSharedPreferences("myPrefs.xml", Context.MODE_PRIVATE);
        driverID = Integer.valueOf(prefs.getString("id", "-1"));
        String sponsorString = prefs.getString("sponsor", "-1");
        Toast.makeText(getContext(), sponsorString, Toast.LENGTH_SHORT);
        if(sponsorString.equals(null)) {
            sponsorText.setText("Sponsor: None");
        }
        else {
            sponsorID = Integer.valueOf(sponsorString);
            String sponsorUrl = Uri.parse(SPONSOR_BASE_URL + sponsorID).buildUpon().build().toString();
            JsonObjectRequest request = new JsonObjectRequest
                    (Request.Method.GET, sponsorUrl, null, response -> {
                        // Stores data into a Dictionary and sends it to the listener
                        try {
                            sponsorText.setText("Sponsor: " + response.getString("sponsor_name"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }, error -> {
                    });
            mRequestQueue.add(request);
        }
        // Initialize the TextViews
        usernameText = root.findViewById(R.id.usernameTextView);
        usernameText.setText("Name: " + prefs.getString("username", "ERROR"));
        qualiText = root.findViewById(R.id.qualiTextView);
        qualiText.setText("Qualifications: " + prefs.getString("qualifications", "ERROR"));
        addressText = root.findViewById(R.id.addressTextView);
        addressText.setText("Address: " + prefs.getString("address", "ERROR"));
        phoneNumberText = root.findViewById(R.id.phoneNumberTextView);
        phoneNumberText.setText("Phone Number: " + prefs.getString("phoneNumber", "ERROR"));
        emailText = root.findViewById(R.id.emailTextView);
        emailText.setText("Email: " + prefs.getString("email", "ERROR"));
        ageText = root.findViewById(R.id.ageTextView);
        age = prefs.getString("age", "ERROR");
        ageText.setText("Age: " + age);
        genderText = root.findViewById(R.id.genderTextView);
        genderText.setText("Gender: " + prefs.getString("gender", "ERROR"));
        pointsText = root.findViewById(R.id.points);
        points = prefs.getInt("points", -1);
        pointsText.setText("Points: " + String.valueOf(prefs.getInt("points", -1)));
        sponsorText = root.findViewById(R.id.sponsor);

        // Initialize the Buttons
        editProfileButton = root.findViewById(R.id.editProfile);
        applySponsor = root.findViewById(R.id.applyForSponsor);

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
            age = bundle.getString("age");
            gender = bundle.getString("gender");
            qualifications = bundle.getString(("qualifications"));

            // Sets data to corresponding TextViews in fragment_home.xml
            if(!username.equals(""))
                usernameText.setText("Username: " + username);
            if(!address.equals(""))
                addressText.setText("Address: " + address);
            if(!phoneNumber.equals(""))
                phoneNumberText.setText("Phone Number: " + phoneNumber);
            if(!age.equals("")) {
                ageText.setText("Age: " + age);
            }
            if(!qualifications.equals(""))
                qualiText.setText("Qualifications: " + qualifications);
            if(!qualifications.equals(""))
                qualiText.setText("Qualifications: " + qualifications);
            if(!gender.equals(""))
                genderText.setText("Gender: " + gender);
        });
        return root;
    }


}