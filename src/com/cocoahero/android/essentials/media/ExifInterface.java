package com.cocoahero.android.essentials.media;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.location.Location;
import android.location.LocationManager;

public class ExifInterface extends android.media.ExifInterface {

    // ------------------------------------------------------------------------
    // Constructors
    // ------------------------------------------------------------------------

    public ExifInterface(File file) throws IOException {
        super(file.getAbsolutePath());
    }

    // ------------------------------------------------------------------------
    // Public Methods
    // ------------------------------------------------------------------------

    public Location getLocation() {
        float[] latlon = new float[2];
        if (this.getLatLong(latlon)) {
            Location location = new Location(LocationManager.GPS_PROVIDER);
            location.setLatitude(latlon[0]);
            location.setLongitude(latlon[1]);
            return location;
        }
        return null;
    }

    public void setLocation(Location location) {
        this.setLatitude(location.getLatitude());
        this.setLongitude(location.getLongitude());

        if (location.hasAltitude()) {
            this.setAltitude(location.getAltitude());
        }

        Date date = new Date(location.getTime());

        String dateStamp = new SimpleDateFormat("yyyy:MM:dd").format(date);
        String timeStamp = new SimpleDateFormat("HH:mm:ss.SS").format(date);

        this.setAttribute(TAG_GPS_DATESTAMP, dateStamp);
        this.setAttribute(TAG_GPS_TIMESTAMP, timeStamp);
    }

    public void setAltitude(double altitude) {
        this.setAttribute(TAG_GPS_ALTITUDE_REF, (altitude > 0 ? "0" : "1"));
        this.setAttribute(TAG_GPS_ALTITUDE, String.valueOf(Math.abs(altitude)));
    }

    public void setLatitude(double latitude) {
        int latDeg = (int) Math.abs(latitude);
        int latMin = (int) Math.abs(((latitude % 1) * 60));
        int latSec = (int) Math.abs(((((latitude % 1) * 60) % 1) * 60));

        String latStr = String.format("%d/1,%d/1,%d/1", latDeg, latMin, latSec);

        this.setAttribute(TAG_GPS_LATITUDE, latStr);
        this.setAttribute(TAG_GPS_LATITUDE_REF, ((latitude > 0) ? "N" : "S"));
    }

    public void setLongitude(double longitude) {
        int lonDeg = (int) Math.abs(longitude);
        int lonMin = (int) Math.abs(((longitude % 1) * 60));
        int lonSec = (int) Math.abs(((((longitude % 1) * 60) % 1) * 60));

        String lonStr = String.format("%d/1,%d/1,%d/1", lonDeg, lonMin, lonSec);

        this.setAttribute(TAG_GPS_LONGITUDE, lonStr);
        this.setAttribute(TAG_GPS_LONGITUDE_REF, ((longitude > 0) ? "E" : "W"));
    }
}
