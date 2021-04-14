package com.example.driver_1.ui.store;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CategoryViewModel extends AndroidViewModel {

    private String TAG = "CategoryViewModel";
    private List<String> mCats;
    private MutableLiveData<List<String>> categories;

    public CategoryViewModel(@NonNull Application application) {
        super(application);

        mCats = new ArrayList<>();
    }

    public LiveData<List<String>> getCats() {
        if(categories == null){
            categories = new MutableLiveData<List<String>>();
            loadCategories();
        }
        return categories;
    }

    /**
     * Loads possible search categories from API call
     */
    public void loadCategories() {
        // can be launched in a separate asynchronous job
        SharedPreferences prefs = this.getApplication().getApplicationContext().getSharedPreferences("myPrefs.xml", Context.MODE_PRIVATE);
        String s_id = prefs.getString("sponsor_id", "1");
        String url = "https://driver1-web-app.herokuapp.com/api/catalog_params/" + s_id;
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
                                mCats.add(jArray.getString(i));
                            }
                            categories.postValue(mCats);
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
}
