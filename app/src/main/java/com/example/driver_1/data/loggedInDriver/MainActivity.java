package com.example.driver_1.data.loggedInDriver;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.driver_1.R;
import com.example.driver_1.ui.login.LoginActivity;
import com.example.driver_1.ui.store.CategoryFragment;
import com.example.driver_1.ui.store.StoreActivity;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements CategoryFragment.OnItemSelectedListener {
    /**
     * @pre app is opened
     * @param savedInstanceState reloads the app if it was closed
     * @post home page is displayed
     */

    private AppBarConfiguration mAppBarConfiguration;
    private String mItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences prefs = getSharedPreferences("myPrefs.xml", MODE_PRIVATE);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        // Set the side drawer text fields
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView drawerEmail = (TextView) headerView.findViewById(R.id.drawerEmail);
        drawerEmail.setText(prefs.getString("email", "email"));
        TextView drawerName = (TextView) headerView.findViewById(R.id.drawerName);
        drawerName.setText(prefs.getString("username", "username"));
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_store, R.id.nav_settings, R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void onClick(MenuItem item) {

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    /** Adapted from ZyBooks band app
     * @pre the item list has been created and contains valid books
     * @pre the selected book has valid info
     * @param itemId the key that corresponds to the item being called
     * @post the transition/creation of the details fragment begins
     */
    @Override
    public void onItemSelected(String itemId) {
        mItemId = itemId;
        if (findViewById(R.id.store_fragment_container) == null) {
            // Must be in portrait, so start activity
            Intent intent = new Intent(this, StoreActivity.class);
            intent.putExtra(StoreActivity.SEARCH_TERM, itemId);
            startActivity(intent);
        } else {
            Fragment StoreFragment = com.example.driver_1.ui.store.StoreFragment.newInstance(itemId);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.store_fragment_container, StoreFragment)
                    .commit();
        }
    }

}