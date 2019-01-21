package com.example.user301.startandroidtesting;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

// взаимодействие с сетью
public class FlickrFetchr {

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
}
