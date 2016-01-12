package com.bolooo.carlogo;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bolooo.carlogo.misc.OkHttpStack;

import okhttp3.OkHttpClient;

/**
 * Created by liqin on 2016/1/6.
 */
public class VolleyClient {
    private static RequestQueue requestQueue;

    public VolleyClient() {

    }

    public static void init(Context context) {
        requestQueue = Volley
                .newRequestQueue(context,
                        new OkHttpStack(new OkHttpClient()));
    }


    public static RequestQueue getRequestQueue() {
        if (requestQueue != null) {
            return requestQueue;
        } else {
            throw new IllegalStateException("RequestQueue not initialized");
        }
    }
}
