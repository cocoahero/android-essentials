package com.cocoahero.android.essentials.networking;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;

public class HttpFileDownload extends AsyncTask<Void, Float, Void> {

    // ------------------------------------------------------------------------
    // Public Interfaces
    // ------------------------------------------------------------------------

    public interface Listener {
        public void onDownloadProgress(URL source, float progress);

        public void onDownloadFinished(URL source, File destination);

        public void onDownloadFailed(URL source, int statusCode, Exception exception);
    }

    // ------------------------------------------------------------------------
    // Public Constants
    // ------------------------------------------------------------------------

    public static final int DEFAULT_BUFFER_SIZE = 1024;

    // ------------------------------------------------------------------------
    // Instance Variables
    // ------------------------------------------------------------------------

    private final URL mSource;
    private final File mDestination;
    private Listener mListener;
    private HttpURLConnection mConnection;
    private Exception mException;
    private int mStatusCode;
    private int mBufferSize;
    private int mBytesTotal;
    private int mBytesRead;
    private int mBytesDownloaded;
    private byte[] mBuffer;
    private BufferedInputStream mInputStream;
    private BufferedOutputStream mOutputStream;

    // ------------------------------------------------------------------------
    // Constructors
    // ------------------------------------------------------------------------

    public HttpFileDownload(URL source, File destination, Listener listener) {
        this.mSource = source;
        this.mDestination = destination;
        this.mListener = listener;
        this.mBufferSize = DEFAULT_BUFFER_SIZE;
    }

    // ------------------------------------------------------------------------
    // Public Methods
    // ------------------------------------------------------------------------

    public void setBufferSize(int size) {
        this.mBufferSize = (size > 0 ? size : DEFAULT_BUFFER_SIZE);
    }

    public void setListener(Listener listener) {
        this.mListener = listener;
    }

    // ------------------------------------------------------------------------
    // Protected Methods
    // ------------------------------------------------------------------------

    @Override
    protected Void doInBackground(Void... params) {
        try {
            this.mConnection = (HttpURLConnection) this.mSource.openConnection();
            this.mStatusCode = this.mConnection.getResponseCode();
            if (this.mStatusCode == HttpURLConnection.HTTP_OK) {
                this.mBytesTotal = this.mConnection.getContentLength();
                this.mInputStream = new BufferedInputStream(this.mConnection.getInputStream(), this.mBufferSize);
                this.mOutputStream = new BufferedOutputStream(new FileOutputStream(this.mDestination), this.mBufferSize);
                this.mBuffer = new byte[this.mBufferSize];
                while (!this.isCancelled() && ((this.mBytesRead = this.mInputStream.read(this.mBuffer)) != -1)) {
                    this.mBytesDownloaded += this.mBytesRead;
                    if (this.mBytesTotal > 0) {
                        this.publishProgress(((float) this.mBytesDownloaded) / ((float) this.mBytesTotal));
                    }
                }
            }
        }
        catch (IOException e) {
            this.mException = e;
        }
        finally {
            try {
                if (this.mConnection != null) {
                    this.mConnection.disconnect();
                    this.mConnection = null;
                }
                if (this.mInputStream != null) {
                    this.mInputStream.close();
                    this.mInputStream = null;
                }
                if (this.mOutputStream != null) {
                    this.mOutputStream.flush();
                    this.mOutputStream.close();
                    this.mOutputStream = null;
                }
                this.mBuffer = null;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Float... progress) {
        if (progress[0] != null) {
            this.notifyProgress(this.mSource, progress[0]);
        }
    }

    @Override
    protected void onPostExecute(Void result) {
        if (this.mStatusCode == HttpURLConnection.HTTP_OK && this.mException == null) {
            this.notifyCompletion(this.mSource, this.mDestination);
        }
        else {
            this.notifyFailure(this.mSource, this.mStatusCode, this.mException);
        }
    }

    // ------------------------------------------------------------------------
    // Private Methods
    // ------------------------------------------------------------------------

    private void notifyProgress(URL source, float progress) {
        if (this.mListener != null) {
            this.mListener.onDownloadProgress(source, progress);
        }
    }

    private void notifyCompletion(URL source, File destination) {
        if (this.mListener != null) {
            this.mListener.onDownloadFinished(source, destination);
        }
    }

    private void notifyFailure(URL source, int statusCode, Exception exception) {
        if (this.mListener != null) {
            this.mListener.onDownloadFailed(source, statusCode, exception);
        }
    }

}
