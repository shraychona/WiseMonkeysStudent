package com.shray.wisemonkeysstudent;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Shray on 5/21/2017.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/LondonBetween.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

}
