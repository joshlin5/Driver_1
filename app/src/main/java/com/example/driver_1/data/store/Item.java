package com.example.driver_1.data.store;

import android.content.Context;

public class Item {
    private int mId;
    private String mName;

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

}
