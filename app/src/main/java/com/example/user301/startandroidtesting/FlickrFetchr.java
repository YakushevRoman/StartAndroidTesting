package com.example.user301.startandroidtesting;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

// взаимодействие с сетью
public class FlickrFetchr {

    public static final String TAG = "FlickrFetchr";
    public static final String API_KEY = "a1fa9eeaea69d4b914b1887bbcb5391f";

    public byte[] getURLBytes (String urlSpec) throws IOException {
        // формируем юрл
        URL url = new URL(urlSpec);
        // создаем конекшен
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        //
        try {
            ByteArrayOutputStream out =
                    new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            //
            if (connection.getResponseCode() != HttpsURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() + " with:" + urlSpec);
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }

            out.close();
            return out.toByteArray();
        }finally {
            connection.disconnect();
        }
    }
    // проблема с конекшеном
    public String getURLString (String urlSpec) throws  IOException{
        return new String(getURLBytes(urlSpec));
    }
    //
    public List<GalleryItems> fetchItems(){
        List <GalleryItems> galleryItems = new ArrayList<>();
        String url = Uri.parse("https://api.flickr.com/services/rest/")
                .buildUpon()
                .appendQueryParameter("method", "flickr.photos.getRecent")
                .appendQueryParameter("api_key", API_KEY)
                .appendQueryParameter("format", "json")
                .appendQueryParameter("nojsoncallback", "1")
                .appendQueryParameter("extras", "url_s")
                .build()
                .toString();
        try {
            String jsonString = getURLString(url);
            Log.i(TAG, "fetchItems: Received JSON:" + jsonString);
            JSONObject jsonObject = new JSONObject(jsonString);
            parseItems(galleryItems, jsonObject);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "fetchItems: " , e );
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "fetchItems: ", e );
        }
        return galleryItems;
    }
    //
    private void parseItems (List<GalleryItems> items, JSONObject jsonObject) throws  IOException, JSONException{
        JSONObject photosJsonObject = jsonObject.getJSONObject("photos");
        JSONArray photoJsonArray = photosJsonObject.getJSONArray("photo");

        for (int i = 0; i < photoJsonArray.length(); i++) {
            JSONObject photoJsonObject = photoJsonArray.getJSONObject(i);
            GalleryItems galleryItems = new GalleryItems();
            galleryItems.setrId(photoJsonObject.getString("id"));
            galleryItems.setrCaption(photoJsonObject.getString("title"));

            if (!photoJsonObject.has("url_s")){
                continue;
            }

            galleryItems.setrUrl(photoJsonObject.getString("url_s"));

            items.add(galleryItems);
        }

    }
}
