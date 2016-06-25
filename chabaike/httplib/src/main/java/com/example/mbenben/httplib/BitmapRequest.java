package com.example.mbenben.httplib;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.lang.reflect.Method;

/**
 * Created by MBENBEN on 2016/6/22.
 */
public class BitmapRequest extends Request<Bitmap> {

    public BitmapRequest(String url, Method method, Callback callback) {
        super(url, method, callback);
    }

    public BitmapRequest() {
        super();
    }

    @Override
    public void dispatchContent(byte[] content) {
        Bitmap bp = BitmapFactory.decodeByteArray(content,0,content.length);
        onResponse(bp);
    }
}
