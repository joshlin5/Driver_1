package com.example.driver_1.ui.sponsor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SponsorViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SponsorViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is sponsor fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}