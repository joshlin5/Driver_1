package com.example.driver_1.ui.store;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.driver_1.R;
import com.example.driver_1.data.store.Item;

public class StoreActivity extends AppCompatActivity implements StoreFragment.OnItemSelectedListener {

    public static String SEARCH_TERM = "cityId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_store);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.store_fragment_container);

        if (fragment == null) {
            // Use search from catergory fragment to instantiate store fragment
            String searchTerm = getIntent().getStringExtra(SEARCH_TERM);
            fragment = com.example.driver_1.ui.store.StoreFragment.newInstance(searchTerm);
            fragmentManager.beginTransaction()
                    .add(R.id.store_fragment_container, fragment)
                    .commit();
        }
    }

    @Override
    public void onItemSelected(Item item) {
        if (findViewById(R.id.store_fragment_container) == null) {
            // Must be in portrait, so start activity
            Intent intent = new Intent(this, StoreActivity.class);
            startActivity(intent);
        } else {
            Fragment StoreFragment = com.example.driver_1.ui.store.ItemDetailFragment.newInstance(item);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.store_fragment_container, StoreFragment)
                    .commit();
        }
    }

}