package com.cocoahero.android.essentials.location;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;

public class ReverseGeocodeTask extends AsyncTask<Void, Void, List<Address>> {

    // ------------------------------------------------------------------------
    // Interfaces
    // ------------------------------------------------------------------------

    public interface Callback {
        public void onReverseGeocodeFinished(Location location, List<Address> addresses);

        public void onReverseGeocodeFailed(Location location, Exception e);
    }

    // ------------------------------------------------------------------------
    // Public Constants
    // ------------------------------------------------------------------------
    
    public static final int DEFAULT_MAX_RESULTS = 5;

    // ------------------------------------------------------------------------
    // Instance Variables
    // ------------------------------------------------------------------------

    private final Geocoder mGeocoder;
    private final Location mLocation;
    private Callback mCallback;
    private Exception mException;
    private int mMaxResults;

    // ------------------------------------------------------------------------
    // Constructors
    // ------------------------------------------------------------------------

    public ReverseGeocodeTask(Context context, Location location, Callback callback) {
        this.mGeocoder = new Geocoder(context.getApplicationContext());
        this.mLocation = location;
        this.mCallback = callback;
        this.mMaxResults = DEFAULT_MAX_RESULTS;
    }

    // ------------------------------------------------------------------------
    // Public Methods
    // ------------------------------------------------------------------------

    public Location getLocation() {
        return this.mLocation;
    }

    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    public Exception getException() {
        return this.mException;
    }

    public void setMaxResults(int maxResults) {
        this.mMaxResults = maxResults;
    }

    // ------------------------------------------------------------------------
    // Protected Methods
    // ------------------------------------------------------------------------

    @Override
    protected List<Address> doInBackground(Void... args) {
        if (this.mLocation != null && this.mGeocoder != null) {
            double lat = this.mLocation.getLatitude();
            double lon = this.mLocation.getLongitude();
            try {
                return this.mGeocoder.getFromLocation(lat, lon, this.mMaxResults);
            }
            catch (IOException e) {
                this.mException = e;
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Address> addresses) {
        if (this.mCallback != null) {
            if (addresses != null) {
                this.mCallback.onReverseGeocodeFinished(this.mLocation, addresses);
            }
            else {
                this.mCallback.onReverseGeocodeFailed(this.mLocation, this.mException);
            }
        }
    }
}
