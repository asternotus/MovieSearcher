package com.aleisterfly.testattract.managers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.aleisterfly.testattract.models.Movie;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DataManager {

    private static final String DATA_URL = "http://test.php-cd.attractgroup.com/test.json";

    private boolean isLoaded = false;

    private static DataManager instance;

    private ArrayList<Movie> movies = new ArrayList<>();

    private DataManager() {
    }

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    private void loadMovieData() throws Exception {
        ArrayList<Movie> movies = new ArrayList<>();
        if (!isLoaded) {
            String response = getResponseBody(DATA_URL);

            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                Movie movie = new Movie(jsonObject);
                movies.add(movie);
                movie.setImageBitmap(getBitmapFromURL(movie.getImage()));
            }
            this.movies = movies;
        }
        isLoaded = true;
    }

    public void loadMovieDataAsync(final MovieLoadingObserver observer) {
        if (isLoaded) {
            observer.onSuccess();
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    loadMovieData();
                    observer.onSuccess();
                } catch (Exception e) {
                    e.printStackTrace();
                    observer.onError(e);
                }
            }
        }).start();
    }

    private static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getResponseBody(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();

        int responseCode = connection.getResponseCode();
        InputStream inputStream;
        if (200 <= responseCode && responseCode <= 299) {
            inputStream = connection.getInputStream();
        } else {
            inputStream = connection.getErrorStream();
        }

        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        inputStream));

        StringBuilder response = new StringBuilder();
        String currentLine;

        while ((currentLine = in.readLine()) != null)
            response.append(currentLine);

        in.close();

        return response.toString();
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public boolean isLoaded() {
        return isLoaded;
    }
}
