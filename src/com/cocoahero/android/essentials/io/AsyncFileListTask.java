package com.cocoahero.android.essentials.io;

import java.io.File;
import java.io.FileFilter;

import android.os.AsyncTask;

/**
 * An {@link AsyncTask} subclass designed to fetch the list of files contained
 * within a directory.
 * 
 * To filter which files get returned, set the {@link FileFilter} property prior
 * to executing the task.
 */
public class AsyncFileListTask extends AsyncTask<Void, Void, File[]> {

    // ------------------------------------------------------------------------
    // Callback Interface
    // ------------------------------------------------------------------------

    public interface Callback {
        public void onFileListCompleted(File directory, File[] files);
    }

    // ------------------------------------------------------------------------
    // Instance Variables
    // ------------------------------------------------------------------------

    private File mDirectory;
    private FileFilter mFilter;
    private AsyncFileListTask.Callback mCallback;

    // ------------------------------------------------------------------------
    // Constructors
    // ------------------------------------------------------------------------

    public AsyncFileListTask(File directory, FileFilter filter, AsyncFileListTask.Callback callback) {
        this.mDirectory = directory;
        this.mFilter = filter;
        this.mCallback = callback;
    }

    // ------------------------------------------------------------------------
    // Public Methods
    // ------------------------------------------------------------------------

    public void setDirectory(File directory) {
        this.mDirectory = directory;
    }

    public void setFilter(FileFilter filter) {
        this.mFilter = filter;
    }

    public void setCallback(AsyncFileListTask.Callback callback) {
        this.mCallback = callback;
    }

    // ------------------------------------------------------------------------
    // Protected Methods
    // ------------------------------------------------------------------------

    @Override
    protected File[] doInBackground(Void... params) {
        return this.mDirectory.listFiles(this.mFilter);
    }

    @Override
    protected void onPostExecute(File[] results) {
        if (this.mCallback != null) {
            this.mCallback.onFileListCompleted(this.mDirectory, results);
        }
    }

}
