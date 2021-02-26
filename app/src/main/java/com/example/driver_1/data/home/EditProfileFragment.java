// Joshua Lin C15684772 jlin5@g.clemson.edu
// Code referenced from Zybooks: Mobile App Development, official Java Documentation (Oracle), and official Android documentation

package com.example.driver_1.data.home;

import android.app.Dialog;
import android.content.Context;
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
    public interface EditProfileListener {
        // Callback for "OK" button
        void onEditProfileDialogPositiveClick(String username, String address, String phoneNumber, String email, String birthday, String gender);
        // Callback for "Cancel" button
        void onEditProfileDialogNegativeClick();
    }

    EditProfileListener listener;
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
        // Making a new dialog using a fragment from fragment_username
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Edit Profile");
        // Telling Dialog which layout to use and to use the EditText
        View inflater = LayoutInflater.from(getContext()).inflate(R.layout.fragment_edit_profile, (ViewGroup) getView(), false);
        username = inflater.findViewById(R.id.usernameEditText);
        address = inflater.findViewById(R.id.addressEditText);
        phoneNumber = inflater.findViewById(R.id.phoneNumberEditText);
        email = inflater.findViewById(R.id.emailEditText);
        birthday = inflater.findViewById(R.id.birthdayEditText);
        gender = inflater.findViewById(R.id.genderEditText);

        // Creating the "OK" and "Default" buttons
        builder.setView(inflater)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onEditProfileDialogPositiveClick(username.getText().toString(), address.getText().toString(), phoneNumber.getText().toString(), email.getText().toString(), birthday.getText().toString(), gender.getText().toString());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onEditProfileDialogNegativeClick();
                    }
                });
        return builder.create();
    }

    /**
     *
     * @param context Interface to global information about an application environment
     * @pre
     * context = NULL or context = [some data]
     * @post
     * [listener will be attached to context]
     */
    // Inspired from Zybooks
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (EditProfileListener) context;
    }
}