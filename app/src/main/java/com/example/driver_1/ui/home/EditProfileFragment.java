// Joshua Lin C15684772 jlin5@g.clemson.edu
// Code referenced from Zybooks: Mobile App Development, official Java Documentation (Oracle), and official Android documentation

package com.example.driver_1.ui.home;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.driver_1.R;

public class EditProfileFragment extends DialogFragment {
    // The EditText for the Dialog
    EditText username, address, phoneNumber, email, birthday, gender;

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
        birthday = inflater.findViewById(R.id.birthdayEditText);
        gender = inflater.findViewById(R.id.genderEditText);

        // Creating the "OK" and "Cancel" buttons
        builder.setView(inflater)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Getting data from the Edit Text and putting it in the Bundle
                        Bundle result = new Bundle();
                        result.putString("username", username.getText().toString());
                        result.putString("address", address.getText().toString());
                        result.putString("phoneNumber", phoneNumber.getText().toString());
                        result.putString("email", email.getText().toString());
                        result.putString("birthday", birthday.getText().toString());
                        result.putString("gender", gender.getText().toString());
                        // Sending the data to the parent fragment (the HomeFragment.java)
                        getParentFragmentManager().setFragmentResult("requestKey", result);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // No action required if canceled
                    }
                });
        return builder.create();
    }
}