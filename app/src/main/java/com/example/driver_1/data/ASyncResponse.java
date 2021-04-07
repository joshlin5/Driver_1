package com.example.driver_1.data;

import android.graphics.drawable.Drawable;

import androidx.core.util.Pair;

public interface ASyncResponse {
    void processFinish(Pair<Drawable, Integer> output);
}
