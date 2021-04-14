package com.example.driver_1.data.store;

import android.content.Context;
import android.graphics.drawable.Drawable;

import org.json.JSONException;
import org.json.JSONObject;

public class Item {
    private int mId;
    private String mName;
    private Double mPrice;
    private Drawable mImage;

    // Default constructor
    public Item() {}

    /** Adapted from ZyBooks band class contructor
     * @pre item database exists
     * @param id the int id of the item
     * @param name The name of the item
     * @post item is created and necessary info is filled or will be filled by API call response
     */
    public Item(int id, String name, Double price) {
        mId = id;
        mName = name;
        mPrice = price;

    }

    public Item(String info) {
        try {
            JSONObject obj = new JSONObject(info.toString());
            mId = obj.getInt("id");
            mName = obj.getString("name");
            mPrice = obj.getDouble("price");
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

    public Double getPrice() {
        return mPrice;
    }

    public void setPrice(Double price) {
        this.mPrice = price;
    }
}
