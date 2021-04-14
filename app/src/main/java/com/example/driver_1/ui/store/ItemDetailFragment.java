package com.example.driver_1.ui.store;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.driver_1.R;

import org.json.JSONException;
import org.json.JSONObject;

// Gets a JSON Object of item to display
// Ask user whether you want to buy it or not
// Display item name, image, and price
// Send to API request for purchase, pass in driverId, ItemId, and item price
public class ItemDetailFragment extends DialogFragment{
    String itemName, itemUrl;
    int driverPoints, itemId, itemPrice, sponsorId;
    TextView nameText, priceText;
    ImageView itemImage;
    SharedPreferences prefs;
    private final String PURCHASE_BASE_URL = "https://driver1-web-app.herokuapp.com/api/purchase/";
    // Sends the fetch request to the main thread
    private RequestQueue mRequestQueue ;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Making a new dialog fragment
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Buy Item?");
        // Using fragment_edit_profile.xml to make the dialog
        View inflater = LayoutInflater.from(getContext()).inflate(R.layout.fragment_item_detail, (ViewGroup) getView(), false);
        prefs = this.getActivity().getSharedPreferences("myPrefs.xml", Context.MODE_PRIVATE);
        driverPoints = prefs.getInt("points", -1);
        nameText = inflater.findViewById(R.id.itemName);
        priceText = inflater.findViewById(R.id.priceTextView);
        itemImage = inflater.findViewById(R.id.itemImage);
        getParentFragmentManager().setFragmentResultListener("requestKey", this, (requestKey, bundle) -> {
            itemName = bundle.getString("name");
            itemId = bundle.getInt("id");
            itemUrl = bundle.getString("url");
            itemPrice = bundle.getInt("price");
        });
        mRequestQueue = Volley.newRequestQueue(getContext());

        // Creating the "OK" and "Cancel" buttons
        builder.setView(inflater)
                .setPositiveButton("Buy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String url = Uri.parse(PURCHASE_BASE_URL).buildUpon().build().toString();
                        JSONObject body = new JSONObject();
                        try {
                            //POST JSON body
                            body.put("driver_id", prefs.getInt("id", 1));
                            body.put("item_id", itemId);
                            body.put("cost", itemPrice);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // Requests the location data and puts it on the queue
                        JsonObjectRequest request2 = new JsonObjectRequest
                                (Request.Method.POST, url, body, response -> {
                                    Toast.makeText(getContext(), "Item Purchase Request Sent", Toast.LENGTH_SHORT);
                                    }, error -> {}
                                );
                        mRequestQueue.add(request2);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getContext(), "Item Purchase Canceled", Toast.LENGTH_SHORT);
                    }
                });
        return builder.create();
    }
}