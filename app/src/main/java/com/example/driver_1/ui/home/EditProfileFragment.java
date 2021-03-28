// Joshua Lin C15684772 jlin5@g.clemson.edu
// Code referenced from Zybooks: Mobile App Development, official Java Documentation (Oracle), and official Android documentation

package com.example.driver_1.ui.home;

import android.app.Dialog;
import android.content.DialogInterface;
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

import com.example.driver_1.R;

public class EditProfileFragment extends DialogFragment implements AdapterView.OnItemSelectedListener{
    // The EditText for the Dialog
    EditText username, address, phoneNumber, email, age;
    Spinner gender;
    String genderResult = "Male";

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

        // The Edit Text that I need to get the changed data from
        username = inflater.findViewById(R.id.usernameEditText);
        address = inflater.findViewById(R.id.addressEditText);
        phoneNumber = inflater.findViewById(R.id.phoneNumberEditText);
        email = inflater.findViewById(R.id.emailEditText);
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
                        if(!username.getText().toString().equals("") && username.getText().toString().length() > 0)
                            result.putString("username", username.getText().toString());
                        else
                            result.putString("username", "");

                        if(!address.getText().toString().equals("") && address.getText().toString().length() > 0)
                            result.putString("address", address.getText().toString());
                        else
                            result.putString("address", "");

                        if(!phoneNumber.getText().toString().equals("") && phoneNumber.getText().toString().length() > 0)
                            result.putString("phoneNumber", phoneNumber.getText().toString());
                        else
                            result.putString("phoneNumber", "");

                        if(!email.getText().toString().equals("") && email.getText().toString().length() > 0)
                            result.putString("email", email.getText().toString());
                        else
                            result.putString("email", "");

                        if(!age.getText().toString().equals("") && age.getText().toString().length() > 0)
                            result.putString("age", age.getText().toString());
                        else
                            result.putString("age", "");

                        result.putString("gender", genderResult);
                        // Sending the data to the parent fragment (the HomeFragment.java)
                        getParentFragmentManager().setFragmentResult("EditProfileResult", result);
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