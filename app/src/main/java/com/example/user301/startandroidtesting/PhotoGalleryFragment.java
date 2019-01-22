package com.example.user301.startandroidtesting;

import android.graphics.drawable.Drawable;
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
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PhotoGalleryFragment extends Fragment {
    public static final String TAG = "PhotoGalleryFragment";
    RecyclerView rPhotoRecyclerView;
    private List <GalleryItems> rItems = new ArrayList<>();

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

        setupAdapter();

        return view;
    }

    private void setupAdapter() {
        if (isAdded()){
            rPhotoRecyclerView.setAdapter(new PhotoAdapter(rItems));
        }
    }

    // класс для работа с многопоточностью
    private class FetchItemsTask extends AsyncTask<Void, Void, List<GalleryItems>>{

        @Override
        protected List<GalleryItems> doInBackground(Void... voids) {
            return new FlickrFetchr().fetchItems();
            //return null;
        }

        @Override
        protected void onPostExecute(List<GalleryItems> items) {
            rItems = items;
            setupAdapter();
        }
    }
    // adapter
    private  class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder>{

        private List <GalleryItems> rGalleryItems;

        public PhotoAdapter(List<GalleryItems> rGalleryItems) {
            this.rGalleryItems = rGalleryItems;
        }

        @NonNull
        @Override
        public PhotoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            //TextView textView = new TextView(getActivity());
            //return new PhotoHolder(textView);
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            View view = layoutInflater.inflate(R.layout.gallery_item, viewGroup, false);
            return new PhotoHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PhotoHolder photoHolder, int i) {
            GalleryItems galleryItems = rGalleryItems.get(i);
           //photoHolder.bindGallery(galleryItems);
            Drawable drawable = getResources().getDrawable(R.drawable.ic_launcher_background);
            photoHolder.bindDrawable(drawable);
        }

        @Override
        public int getItemCount() {
            return rGalleryItems.size();
        }
    }

    //разбираемся со списками
    private  class PhotoHolder extends RecyclerView.ViewHolder{
        //private TextView rTitleTextView;
        private ImageView rImageView;
        public PhotoHolder(@NonNull View itemView) {
            super(itemView);
            this.rImageView = (ImageView) itemView.findViewById(R.id.fragment_photo_gallery_image_view);
        }

        /*public void bindGallery (GalleryItems galleryItems){
            rTitleTextView.setText(galleryItems.toString());
        }*/

        public void bindDrawable (Drawable drawable){
            rImageView.setImageDrawable(drawable);

        }
    }

}
