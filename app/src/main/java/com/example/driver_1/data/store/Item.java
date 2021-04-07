package com.example.driver_1.data.store;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;

import com.example.driver_1.data.ASyncResponse;
import com.example.driver_1.data.RetrieveItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;

public class Item {
    private int mId;
    private String mName;
    private Drawable mImage;

    // Default constructor
    public Item() {}

    /** Adapted from ZyBooks band class contructor
     * @pre item database exists
     * @param id the int id of the item
     * @param name The name of the item
     * @param c the context of the application ot allow for editing of TextViews from API call response
     * @post item is created and necessary info is filled or will be filled by API call response
     */
    public Item(int id, String name, Context c) {
        mId = id;
        mName = name;

    }

    public Item(String info) {
        try {
            JSONObject obj = new JSONObject(info.toString());
            mId = obj.getInt("id");
            mName = obj.getString("name");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Below are getters and setters for this class

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public Drawable getImage() {
        return mImage;
    }

    public void setImage(Drawable mImage) {
        this.mImage = mImage;
    }

}
