package com.cocoahero.android.essentials.graphics;

import java.io.File;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

public class BitmapResizeTask extends AsyncTask<Void, Void, Bitmap> {

    // ------------------------------------------------------------------------
    // Interfaces
    // ------------------------------------------------------------------------

    public interface Callback {
        public void onBitmapResizeFinished(Bitmap resizedBitmap);
    }

    // ------------------------------------------------------------------------
    // Instance Variables
    // ------------------------------------------------------------------------

    private final String mPath;
    private final int mWidth;
    private final int mHeight;
    private Callback mCallback;

    // ------------------------------------------------------------------------
    // Constructors
    // ------------------------------------------------------------------------

    public BitmapResizeTask(File file, int width, int height, Callback callback) {
        this(file.getAbsolutePath(), width, height, callback);
    }

    public BitmapResizeTask(String path, int width, int height, Callback callback) {
        this.mPath = path;
        this.mWidth = width;
        this.mHeight = height;
        this.mCallback = callback;
    }

    // ------------------------------------------------------------------------
    // Public Methods
    // ------------------------------------------------------------------------

    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    // ------------------------------------------------------------------------
    // Protected Methods
    // ------------------------------------------------------------------------

    @Override
    protected Bitmap doInBackground(Void... params) {
        if (this.mPath != null && this.mPath.length() > 0) {
            BitmapFactory.Options opts = new BitmapFactory.Options();

            opts.inJustDecodeBounds = true;

            BitmapFactory.decodeFile(this.mPath, opts);

            final int w = opts.outWidth;
            final int h = opts.outHeight;

            opts.inSampleSize = 1;
            opts.inJustDecodeBounds = false;

            if (h > this.mHeight || w > this.mWidth) {
                if (w > h) {
                    opts.inSampleSize = Math.round(h / this.mHeight);
                }
                else {
                    opts.inSampleSize = Math.round(w / this.mWidth);
                }
            }
            return BitmapFactory.decodeFile(this.mPath, opts);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        if (this.mCallback != null) {
            this.mCallback.onBitmapResizeFinished(result);
        }
    }
}
