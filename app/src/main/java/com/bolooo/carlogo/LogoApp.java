package com.bolooo.carlogo;

import android.app.Application;

/**
 * Created by liqin on 2016/1/11.
 */
public class LogoApp  extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        VolleyClient.init(this);
    }
}
