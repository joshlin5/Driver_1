package com.example.driver_1.data;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import androidx.core.util.Pair;

import java.io.InputStream;
import java.net.URL;


/**
 * class uses pairs in order to keep track of which item the image belongs to when the image is found
 * Also, class makes an asynchronous call to get the image from the URL returned by catalog API call
 */
public class RetrieveItem extends AsyncTask<Pair<String, Integer>, Void, Pair<Drawable, Integer>> {
    public ASyncResponse delegate = null;
    private Exception exception;

    @SafeVarargs
    @Override
    protected final Pair<Drawable, Integer> doInBackground(Pair<String, Integer>... pairs) {
        Drawable mImage = null;
        try {
            InputStream is = (InputStream) new URL(pairs[0].first).getContent();
            mImage = Drawable.createFromStream(is, "src name");
            return new Pair<>(mImage, pairs[0].second);
        } catch (Exception e) {
            this.exception = e;

            return null;
        }
    }

    @Override
    protected void onPostExecute(Pair<Drawable, Integer> p) {
        super.onPostExecute(p);
        delegate.processFinish(p);
    }
}