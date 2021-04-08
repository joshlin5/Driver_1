// Joshua Lin C15684772 jlin5@g.clemson.edu
// Code referenced from Zybooks: Mobile App Development, official Java Documentation (Oracle), and official Android documentation

package com.example.driver_1.ui.home;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.driver_1.R;

import org.json.JSONException;
import org.json.JSONObject;

public class EditProfileFragment extends DialogFragment implements AdapterView.OnItemSelectedListener{
    // The EditText for the Dialog
    String driverId;
    EditText username, address, phoneNumber, email, age, qualification;
    Spinner gender;
    String genderResult = "Male";
    private RequestQueue mRequestQueue ;
    private final String DRIVER_BASE_URL = "https://driver1-web-app.herokuapp.com/api/drivers/";

    /**
     *
     * @param savedInstanceState State of application to save
     * @pre
     * savedInstanceState = NULL or savedInstanceState = [some data]
     * @post
     * [dialog will be created using fragment_username]
     * [dialog will have a "OK" and "Cancel" button]
     */
    // Inspired from Zybooks and Android Developer documentation
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Making a new dialog fragment
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Edit Profile");
        // Using fragment_edit_profile.xml to make the dialog
        View inflater = LayoutInflater.from(getContext()).inflate(R.layout.fragment_edit_profile, (ViewGroup) getView(), false);
        SharedPreferences prefs = this.getActivity().getSharedPreferences("myPrefs.xml", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        driverId = prefs.getString("id", "ERROR");
        mRequestQueue = Volley.newRequestQueue(getContext());
        // The Edit Text that I need to get the changed data from
        username = inflater.findViewById(R.id.usernameEditText);
        address = inflater.findViewById(R.id.addressEditText);
        phoneNumber = inflater.findViewById(R.id.phoneNumberEditText);
        qualification = inflater.findViewById(R.id.qualiEditText);
        //email = inflater.findViewById(R.id.emailEditText);
        age = inflater.findViewById(R.id.ageEditText);

        gender = (Spinner) inflater.findViewById(R.id.genderChoice);
        gender.setOnItemSelectedListener(this);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.genderOptions, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        gender.setAdapter(adapter);

        // Creating the "OK" and "Cancel" buttons
        builder.setView(inflater)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Getting data from the Edit Text and putting it in the Bundle
                        Bundle result = new Bundle();
                        JSONObject body = new JSONObject();
                        try {
                            String nameInput = username.getText().toString();
                            String addressInput = address.getText().toString();
                            String phoneNumberInput = phoneNumber.getText().toString();
                            String qualiInput = qualification.getText().toString();
                            //String emailInput = email.getText().toString();
                            String ageInput = age.getText().toString();
                            if(!nameInput.equals("") && nameInput.length() > 0) {
                                result.putString("username", nameInput);
                                editor.putString("username", nameInput);
                                body.put("name", nameInput);
                            }
                            else {
                                result.putString("username", "");
                                body.put("name", prefs.getString("username", "ERROR"));
                            }

                            if(!addressInput.equals("") && addressInput.length() > 0) {
                                result.putString("address", addressInput);
                                editor.putString("address", addressInput);
                                body.put("address", addressInput);
                            }
                            else {
                                result.putString("address", "");
                                body.put("address", prefs.getString("address", "ERROR"));
                            }

                            if(!phoneNumberInput.equals("") && phoneNumberInput.length() > 0) {
                                result.putString("phoneNumber", phoneNumberInput);
                                editor.putString("phoneNumber", phoneNumberInput);
                                body.put("phone", phoneNumberInput);
                            }
                            else {
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
                            if(!ageInput.equals("") && ageInput.length() > 0) {
                                result.putString("age", ageInput);
                                //editor.putString("age", ageInput);
                                //body.put("age", ageInput);
                            }
                            else {
                                result.putString("age", "");
                                //body.put("age", prefs.getString("age", "ERROR"));
                            }

                            // Not sync with pref file and web server
                            if(!qualiInput.equals("") && qualiInput.length() > 0) {
                                result.putString("qualifications", qualiInput);
                                //editor.putString("age", qualiInput);
                                body.put("qualifications", qualiInput);
                            }
                            else {
                                result.putString("qualifications", "");
                                body.put("qualifications", "None");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // Not sync with web server
                        result.putString("gender", genderResult);
                        editor.putString("gender", genderResult);

                        // Sending the data to the parent fragment (the HomeFragment.java)
                        getParentFragmentManager().setFragmentResult("EditProfileResult", result);


                        String driverUrl = Uri.parse(DRIVER_BASE_URL + driverId).buildUpon().build().toString();
                        JsonObjectRequest request = new JsonObjectRequest
                                (Request.Method.PATCH, driverUrl, body, response -> {}, error -> {});
                        mRequestQueue.add(request);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // No action required if canceled
                    }
                });
        return builder.create();
    }


    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        genderResult = (String) parent.getItemAtPosition(position);
    }


    public void onNothingSelected(AdapterView<?> parent) {
        //
    }
}