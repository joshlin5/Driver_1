package com.example.driver_1.data.store;

import android.content.Context;
import android.content.res.Resources;

import com.example.driver_1.R;

import java.util.ArrayList;
import java.util.List;

public class ItemDatabase {
    private static com.example.driver_1.data.store.ItemDatabase sItemDatabase;
    private List<com.example.driver_1.data.store.Item> mItems;

    public static ItemDatabase get(Context context) {
        if (sItemDatabase == null) {
            sItemDatabase = new ItemDatabase(context);
        }
        return sItemDatabase;
    }

    /** Adapted from ZyBooks Band database constructor
     * @pre the app has started and has context
     * @param context app context to pass to book constructor
     * @post book database is filled with books from the provided list
     */
    private ItemDatabase(Context context) {
        mItems = new ArrayList<>();
        Resources res = context.getResources();
        String[] items = res.getStringArray(R.array.items);

        for (int i = 0; i < items.length; i++) {
            mItems.add(new com.example.driver_1.data.store
                    .Item(i + 1, items[i], context));
        }
    }

    // Gets list of cities
    public List<com.example.driver_1.data.store
            .Item> getItems() {
        return mItems;
    }

    // Gets a specific city
    public com.example.driver_1.data.store
            .Item getBook(int itemId) {
        for (com.example.driver_1.data.store
                .Item book : mItems) {
            if (book.getId() == itemId) {
                return book;
            }
        }
        return null;
    }
}

