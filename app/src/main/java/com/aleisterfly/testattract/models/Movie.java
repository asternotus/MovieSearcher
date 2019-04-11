package com.aleisterfly.testattract.models;

import android.graphics.Bitmap;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Movie {

    private int id;
    private String name;
    private String image;
    private String description;
    private long time;

    private String formattedTime;

    private Bitmap imageBitmap;


    public Movie(JSONObject jsonObject) throws JSONException {
        id = jsonObject.getInt("itemId");
        name = jsonObject.getString("name");
        image = jsonObject.getString("image");
        description = jsonObject.getString("description");
        time = jsonObject.getLong("time");

        formattedTime = convertTime(time);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public String getFormattedTime() {
        return formattedTime;
    }

    private String convertTime(long time){
        Date date = new Date(time);
        Format format = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
        return format.format(date);
    }
}
