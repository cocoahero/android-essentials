package com.cocoahero.android.essentials.graphics;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * This class is a specialized implementation of {@link android.util.LruCache}
 * designed for caching {@link Bitmap}s.
 * 
 * Instead of limiting the number of cached objects, it limits based on the
 * amount of memory used by the cached bitmaps.
 */
public class BitmapCache extends LruCache<String, Bitmap> {

    /**
     * Constructs a new {@link BitmapCache} allowing you to specify the maximum
     * amount of memory used by the cache.
     * 
     * @param bytes The maximum amount of memory in bytes this cache should use.
     */
    public BitmapCache(int bytes) {
        super(bytes);
    }

    /**
     * {@inheritDoc android.util.LruCache}
     */
    @Override
    protected int sizeOf(String key, Bitmap bitmap) {
        return (bitmap.getRowBytes() * bitmap.getHeight());
    }

}
