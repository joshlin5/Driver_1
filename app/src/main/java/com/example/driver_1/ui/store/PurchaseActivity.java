package com.example.driver_1.ui.store;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.driver_1.R;
import com.example.driver_1.data.store.Item;

public class PurchaseActivity extends AppCompatActivity {

    public Item item_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.item_details);

        if (fragment == null) {
            // Use search from category fragment to instantiate store fragment
            String name = getIntent().getStringExtra("name");
            double price = getIntent().getDoubleExtra("price", 0);
            int id = getIntent().getIntExtra("id", 0);
            fragment = com.example.driver_1.ui.store.ItemDetailFragment.newInstance(new Item(id, name, price));
            fragmentManager.beginTransaction()
                    .add(R.id.store_fragment_container, fragment)
                    .commit();
        }
    }
}