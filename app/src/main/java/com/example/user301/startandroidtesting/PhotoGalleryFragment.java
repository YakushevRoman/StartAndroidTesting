package com.example.user301.startandroidtesting;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;

public class PhotoGalleryFragment extends Fragment {
    public static final String TAG = "PhotoGalleryFragment";
    RecyclerView rPhotoRecyclerView;

    public static PhotoGalleryFragment newInstance() {
        return new PhotoGalleryFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        new FetchItemsTask().execute();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_photo_gallery, container, false);

        rPhotoRecyclerView = (RecyclerView) view
                .findViewById(R.id.fragment_photo_gallery_recycler_view);
        rPhotoRecyclerView.setLayoutManager(
                new GridLayoutManager(getActivity(), 3));
        return view;
    }

    // класс для работа с многопоточностью
    private class FetchItemsTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                String result = new FlickrFetchr()
                        .getURLString("http://www.google.com");
                Log.i(TAG, "doInBackground: " + result);
            }catch (IOException e){
                Log.e(TAG, "Failed to fetch URL:", e );
            }
            return null;
        }
    }
}
