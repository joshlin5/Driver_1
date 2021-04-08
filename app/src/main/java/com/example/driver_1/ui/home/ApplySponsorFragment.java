// Joshua Lin C15684772 jlin5@g.clemson.edu
// Code referenced from Zybooks: Mobile App Development, official Java Documentation (Oracle), and official Android documentation

package com.example.driver_1.ui.home;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONArray;
import org.json.JSONObject;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.driver_1.R;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class ApplySponsorFragment extends DialogFragment implements AdapterView.OnItemSelectedListener{
    int sponsorSelectedId = 1;
    Spinner sponsorSpinner;
    Button detailButton;
    List<JSONObject> sponsorList = new ArrayList<>();
    ArrayList<String> sponsorNameList = new ArrayList<>();
    SharedPreferences prefs;

    // Base URL for fetching the weather report data
    private final String SPONSOR_BASE_URL = "https://driver1-web-app.herokuapp.com/api/sponsors/";
    private final String Application_BASE_URL = "https://driver1-web-app.herokuapp.com/api/application/";
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
        builder.setTitle("Sponsor Application Form");
        // Using fragment_edit_profile.xml to make the dialog
        View inflater = LayoutInflater.from(getContext()).inflate(R.layout.fragment_sponsor_application, (ViewGroup) getView(), false);
        prefs = this.getActivity().getSharedPreferences("myPrefs.xml", Context.MODE_PRIVATE);
        detailButton = inflater.findViewById(R.id.sponsor_details);
        detailButton.setOnClickListener(v -> {
            Bundle data = new Bundle();
            data.putInt("sponsorId", sponsorSelectedId);
            try {
                System.out.println("APPLICATION SPONSOR NAME: " + sponsorList.get(sponsorSelectedId).getString("sponsor_name"));
                data.putString("sponsor_name", sponsorList.get(sponsorSelectedId).getString("sponsor_name"));
                data.putInt("exchange_rate", sponsorList.get(sponsorSelectedId).getInt("exchange_rate"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            getChildFragmentManager().setFragmentResult("requestKey", data);
            new SponsorDetailFragment().show(getChildFragmentManager(), "Sponsor Detail");
        });
        mRequestQueue = Volley.newRequestQueue(getContext());


        // The Edit Text that I need to get the changed data from
        sponsorSpinner = inflater.findViewById(R.id.sponsorSpinner);
        sponsorSpinner.setOnItemSelectedListener(this);
        sponsorSpinner.setOnItemSelectedListener(this);
        // Create an ArrayAdapter using the string array and a default spinner layout

        // Builds the final URL for api data fetching
        String sponsorUrl = Uri.parse(SPONSOR_BASE_URL).buildUpon().build().toString();
        // Requests the location data and puts it on the queue
        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, sponsorUrl, null, response -> {
                    // Stores data into a Dictionary and sends it to the listener
                    try {
                        JSONArray sponsorArray = response.getJSONArray("response");
                        for (int i = 0; i < sponsorArray.length(); i++) {
                            JSONObject sponsor = sponsorArray.getJSONObject(i);
                            sponsorNameList.add(sponsor.getString("sponsor_name"));
                            System.out.println("SPONSOR NAMES: " + sponsorNameList.get(0));
                            sponsorList.add(sponsor);
                            // Test for correct response?
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, sponsorNameList);
                        // Specify the layout to use when the list of choices appears
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        // Apply the adapter to the spinner
                        sponsorSpinner.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {});
        mRequestQueue.add(request);

        // Creating the "OK" and "Cancel" buttons
        builder.setView(inflater)
                .setPositiveButton("Send Form", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Builds the final URL for api data fetching
                        String url = Uri.parse(Application_BASE_URL).buildUpon().build().toString();
                        JSONObject body = new JSONObject();
                        try {
                            //POST JSON body
                            body.put("driver_id", prefs.getInt("id", 1));
                            body.put("sponsor_id", sponsorSelectedId);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // Requests the location data and puts it on the queue
                        JsonObjectRequest request2 = new JsonObjectRequest
                                (Request.Method.POST, url, body, response -> {
                                    // Test for correct response?
                                }, error -> {}
                        );
                        mRequestQueue.add(request2);
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
        sponsorSelectedId = (int) id;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}