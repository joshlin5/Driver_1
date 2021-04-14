package com.example.driver_1.ui.home;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import com.example.driver_1.R;

public class SponsorDetailFragment extends DialogFragment {
    int sponsorId;
    TextView nameText, exchangeText;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {// Making a new dialog fragment
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Details");
        // Using fragment_edit_profile.xml to make the dialog
        View inflater = LayoutInflater.from(getContext()).inflate(R.layout.fragment_sponsor_detail, (ViewGroup) getView(), false);
        nameText = inflater.findViewById(R.id.sponsorName);
        exchangeText = inflater.findViewById(R.id.sponsorExchangeRate);
        getParentFragmentManager().setFragmentResultListener("requestKey", this, (requestKey, bundle) -> {
            sponsorId = bundle.getInt("sponsorId");
            nameText.setText("Company Name: " + bundle.getString("sponsor_name"));
            exchangeText.setText("Exchange rate: " + Integer.toString(bundle.getInt("exchange_rate")));
                });

        // Creating the "OK" and "Cancel" buttons
        builder.setView(inflater)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
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