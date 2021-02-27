package com.example.driver_1.ui.home;

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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.driver_1.R;

public class HomeFragment extends Fragment{

    //private HomeViewModel homeViewModel;
    Button resetPasswordButton;
    Button editProfileButton;
    String username, address, phoneNumber, email, birthday, gender;
    TextView usernameEditText, addressEditText, phoneNumberEditText, emailEditText, birthdayEditText, genderEditText;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //homeViewModel =
        //        new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        usernameEditText = root.findViewById(R.id.usernameEditText);
        addressEditText = root.findViewById(R.id.addressEditText);
        phoneNumberEditText = root.findViewById(R.id.phoneNumberEditText);
        emailEditText = root.findViewById(R.id.emailEditText);
        birthdayEditText = root.findViewById(R.id.birthdayEditText);
        genderEditText = root.findViewById(R.id.genderEditText);

        editProfileButton = (Button) root.findViewById(R.id.editProfile);
        resetPasswordButton = (Button) root.findViewById(R.id.resetPassword);
        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
            }
        });

        editProfileButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new EditProfileFragment().show(getChildFragmentManager(), "Edit Profile");
            }
        });

        /*homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });*/

        getChildFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                username = bundle.getString("username");
                address = bundle.getString("address");
                phoneNumber = bundle.getString("phoneNumber");
                email = bundle.getString("email");
                birthday = bundle.getString("birthday");
                gender = bundle.getString("gender");

                usernameEditText.setText(username);
                addressEditText.setText(address);
                phoneNumberEditText.setText(phoneNumber);
                emailEditText.setText(email);
                birthdayEditText.setText(birthday);
                genderEditText.setText(gender);
            }
        });
        return root;
    }
}