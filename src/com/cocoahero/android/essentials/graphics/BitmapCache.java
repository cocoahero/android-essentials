package com.cocoahero.android.essentials.graphics;

import android.app.ActivityManager;
import android.content.Context;
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

    /**
     * A factory for creating a {@link BitmapCache} based on the application's
     * memory class.
     */
    public static class BitmapCacheFactory {

        /**
         * Returns a new {@link BitmapCache} instance configured to use 1/8th of
         * the application's memory class.
         * 
         * @param context
         * @return a new {@link BitmapCache} instance configured to use 1/8th of
         *         the application's memory class.
         */
        public static BitmapCache getCache(Context context) {
            return getCache(context, 0.125f);
        }

        /**
         * Returns a new {@link BitmapCache} instance configured to use the
         * specified percentage of the application's memory class.
         * 
         * @param context
         * @param percentage A floating value between 1.0f and 0.0f.
         * @return a new {@link BitmapCache} instance configured to use the
         *         given percentage of the application's memory class.
         */
        public static BitmapCache getCache(Context context, float percentage) {
            ActivityManager mgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

            final int memoryClass = mgr.getMemoryClass();
            final int cacheSize = (int) (1048576 * memoryClass * percentage);

            return new BitmapCache(cacheSize);
        }

    }

}
