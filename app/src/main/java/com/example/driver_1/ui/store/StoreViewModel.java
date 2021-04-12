package com.example.driver_1.ui.store;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.driver_1.data.ASyncResponse;
import com.example.driver_1.data.RetrieveItem;
import com.example.driver_1.data.store.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StoreViewModel extends AndroidViewModel implements ASyncResponse {
    private List<RetrieveItem> asyncTask;
    private final String TAG = "StoreViewModel";
    private MutableLiveData<List<Item>> items;
    private List<Item> mItems;

    public StoreViewModel(@NonNull Application application) {
        super(application);
        asyncTask = new ArrayList<>();
        //this to set delegate/listener back to this class
        //asyncTask.delegate = this;
        mItems = new ArrayList<>();
    }

    public LiveData<List<Item>> getItems() {
        if (items == null) {
            items = new MutableLiveData<List<Item>>();
            loadItems();
        }
        return items;
    }

    private void loadItems() {
// can be launched in a separate asynchronous job
        SharedPreferences prefs = this.getApplication().getApplicationContext().getSharedPreferences("myPrefs.xml", Context.MODE_PRIVATE);
        String s_id = prefs.getString("sponsor_id", "1");
        String url = "https://driver1-web-app.herokuapp.com/api/catalog/" + "computer";
        Context c = getApplication().getApplicationContext();
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(c);

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Likely going to need to be a JSON array
                        // get JSONObject from JSON file
                        try {

                            JSONObject obj = new JSONObject(response.toString());
                            //JSONObject temp = obj.getJSONObject("response");
                            JSONArray jArray = obj.getJSONArray("response");
                            for (int i=0;i<jArray.length();i++){
                                //Log.d(TAG, jArray.getString(i));
                                imageGet(new Pair<>(new JSONObject(jArray.getString(i)).getString("imageURL"), i));
                                mItems.add(new Item(jArray.getString(i)));
                            }
                            items.postValue(mItems);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Log.d(TAG, ""+error.getMessage()+","+error.toString());

            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void imageGet(Pair<String, Integer> p) {
        asyncTask.add(new RetrieveItem());
        asyncTask.get(p.second).delegate = this;
        asyncTask.get(p.second).execute(p);
    }

    private void searchItems(String query) {
// can be launched in a separate asynchronous job
        String url = "https://driver1-web-app.herokuapp.com/api/catalog/" + query;
        Context c = getApplication().getApplicationContext();
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(c);

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // get JSONObject from JSON file
                        try {
                            JSONObject obj = new JSONObject(response.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Log.d(TAG, ""+error.getMessage()+","+error.toString());

            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public LiveData<String> getText() {
        return new LiveData<String>() {
            @Override
            public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super String> observer) {
                super.observe(owner, observer);
            }
        };
    }

    @Override
    public void processFinish(Pair<Drawable, Integer> output) {
        mItems.get(output.second).setImage(output.first);
        items.postValue(mItems);
    }
}