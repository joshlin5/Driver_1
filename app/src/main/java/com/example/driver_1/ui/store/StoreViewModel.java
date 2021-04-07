package com.example.driver_1.ui.store;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.driver_1.data.store.Item;

import java.util.List;

public class StoreViewModel extends ViewModel {

    private MutableLiveData<List<Item>> items;
    public LiveData<List<Item>> getItems() {
        if (items == null) {
            items = new MutableLiveData<List<Item>>();
            loadItems();
        }
        return items;
    }

    private void loadItems() {
    }
}