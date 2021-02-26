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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.driver_1.R;

public class HomeFragment extends Fragment implements EditProfileFragment.EditProfileListener{

    private HomeViewModel homeViewModel;
    Button resetPasswordButton;
    Button editProfileButton;
    String username, address, phoneNumber, email, birthday, gender;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.username);

        resetPasswordButton = (Button) root.findViewById(R.id.resetPassword);
        editProfileButton = (Button) root.findViewById(R.id.editProfile);
        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
            }
        });
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showEditProfileDialog();
            }
        });

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        return root;
    }

    /**
     * @post
     * [Shows the Dialog asking for target points using the targetPointsDialog class and the target_points layout]
     */
    // Inspired by zyBooks
    public void showEditProfileDialog() {
        EditProfileFragment dialog = new EditProfileFragment();
        dialog.show(getChildFragmentManager(), "Edit Profile Dialog");
    }

    @Override
    public void onEditProfileDialogPositiveClick(String username_input, String address_input, String phoneNumber_input, String email_input, String birthday_input, String gender_input) {
        username = username_input;
        address = address_input;
        phoneNumber = phoneNumber_input;
        email = email_input;
        birthday = birthday_input;
        gender = gender_input;
    }

    @Override
    public void onEditProfileDialogNegativeClick() {

    }
}