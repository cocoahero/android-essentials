package com.cocoahero.android.essentials.graphics;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class BitmapCache extends LruCache<String, Bitmap> {

    public BitmapCache(int bytes) {
        super(bytes);
    }

    @Override
    protected int sizeOf(String key, Bitmap bitmap) {
        return (bitmap.getRowBytes() * bitmap.getHeight());
    }
    
}
