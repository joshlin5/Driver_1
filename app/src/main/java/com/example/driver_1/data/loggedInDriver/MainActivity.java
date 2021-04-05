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
import com.example.driver_1.ui.store.StoreFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements StoreFragment.OnItemSelectedListener {
    /**
     * @pre app is opened
     * @param savedInstanceState reloads the app if it was closed
     * @post home page is displayed
     */

    private AppBarConfiguration mAppBarConfiguration;
    private int mItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        SharedPreferences prefs = getSharedPreferences("myPrefs.xml", MODE_PRIVATE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
                R.id.nav_home, R.id.nav_sponsor, R.id.nav_store, R.id.nav_settings, R.id.nav_logout)
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

        /*FragmentManager manager = getFragmentManager();
        LogoutFragmentDialog dialog = new LogoutFragmentDialog();
        dialog.show(manager, "logoutFragmentDialog");*/

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    /** Adapted from ZyBooks band app
     * @pre the book list has been created and contains valid books
     * @pre the selected book has valid info
     * @param bookId the key that corresponds to the book being called
     * @post the transition/creation of the details fragment begins
     */
    @Override
    public void onItemSelected(int bookId) {

        mItemId = bookId;

        /*if (findViewById(R.id.details_fragment_container) == null) {
            // Must be in portrait, so start activity
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra(DetailsActivity.EXTRA_BOOK_ID, bookId);
            startActivity(intent);
        } else {
            // Replace previous fragment (if one exists) with a new fragment
            Fragment bookFragment = com.example.lendinglibrary.ui.details.DetailsFragment.newInstance(bookId);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.details_fragment_container, bookFragment)
                    .commit();
        }*/
    }
}