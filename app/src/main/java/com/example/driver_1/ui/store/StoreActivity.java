package com.example.driver_1.ui.store;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.driver_1.R;

public class StoreActivity extends AppCompatActivity implements StoreFragment.OnItemSelectedListener {

    private TextView mTextView;
    public static String SEARCH_TERM = "cityId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_store);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.store_fragment_container);

        if (fragment == null) {
            // Use band ID from ListFragment to instantiate DetailsFragment
            String searchTerm = getIntent().getStringExtra(SEARCH_TERM);
            fragment = com.example.driver_1.ui.store.StoreFragment.newInstance(searchTerm);
            fragmentManager.beginTransaction()
                    .add(R.id.store_fragment_container, fragment)
                    .commit();
        }
    }

    @Override
    public void onItemSelected(int ItemId) {

    }
}