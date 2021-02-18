package com.example.driver_1.data.loggedInDriver;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.driver_1.R;

public class MainActivity extends AppCompatActivity {
    /**
     * @pre app is opened
     * @param savedInstanceState reloads the app if it was closed
     * @post home page is displayed
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
