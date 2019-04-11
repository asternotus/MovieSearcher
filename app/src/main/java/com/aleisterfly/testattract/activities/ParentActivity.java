package com.aleisterfly.testattract.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

public class ParentActivity extends AppCompatActivity {

    public boolean isTablet() {
        try {

            Context context = this;
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            float screenWidth  = dm.widthPixels / dm.xdpi;
            float screenHeight = dm.heightPixels / dm.ydpi;
            double size = Math.sqrt(Math.pow(screenWidth, 2) +
                    Math.pow(screenHeight, 2));

            return size >= 6;
        } catch(Throwable t) {

            return false;
        }
    }
}
