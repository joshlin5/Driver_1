// Joshua Lin C15684772 jlin5@g.clemson.edu
// Code referenced from Zybooks: Mobile App Development, official Java Documentation (Oracle), and official Android documentation

package com.example.driver_1.ui.home;

import android.app.Dialog;
import android.content.DialogInterface;
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

import java.io.IOException;
import java.util.Dictionary;

public class ApplySponsorFragment extends DialogFragment implements AdapterView.OnItemSelectedListener{
    // The EditText for the Dialog
    int sponsorId;
    Spinner sponsors;
    int driverId;

    // Base URL for fetching the weather report data
    private final String WEBAPI_BASE_URL = "https://driver1-web-app.herokuapp.com/api";
    // Tag for error messages
    private final String TAG = "Application Post";
    // Sends the fetch request to the main thread
    private RequestQueue mRequestQueue ;

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
        builder.setTitle("Create Application");
        // Using fragment_edit_profile.xml to make the dialog
        View inflater = LayoutInflater.from(getContext()).inflate(R.layout.fragment_sponsor_application, (ViewGroup) getView(), false);

        mRequestQueue = Volley.newRequestQueue(getContext());
        // The Edit Text that I need to get the changed data from
        sponsors = inflater.findViewById(R.id.sponsorSpinner);

        sponsors.setOnItemSelectedListener(this);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        sponsors.setAdapter(adapter);

        // Creating the "OK" and "Cancel" buttons
        builder.setView(inflater)
                .setPositiveButton("Send Form", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Builds the final URL for api data fetching
                        String url = Uri.parse(WEBAPI_BASE_URL).buildUpon()
                                .appendQueryParameter("driver_id", String.valueOf(driverId))
                                .appendQueryParameter("sponsor_id", String.valueOf(sponsorId)).build().toString();

                        // Requests the location data and puts it on the queue
                        JsonObjectRequest request = new JsonObjectRequest
                                (Request.Method.POST, url, null, response -> {
                                    // Stores data into a Dictionary and sends it to the listener
                                    Dictionary weatherReport = parseJsonSubjects(response);
                                    try {
                                        listener.onWeatherRequest(weatherReport);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }, error -> listener.onErrorResponse(error));

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    // Get list of all sponsors
    // Beside sponsors have a details button where they can find more details about the sponsor
}