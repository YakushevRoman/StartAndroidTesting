package com.example.user301.startandroidtesting;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PhotoGalleryActivity extends SingleFragment {


    @Override
    public Fragment newFragment() {
        return PhotoGalleryFragment.newInstance();
    }
}
