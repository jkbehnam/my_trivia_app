package com.trivia.trivia.home.gameActivity.getLocation;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import static android.content.Context.LOCATION_SERVICE;

public class getlocation {
    protected static final int MY_PERMISSIONS_ACCESS_FINE_LOCATION = 1;
    AlertDialog ad;
    LocationManager locationManager;
   public void getlocation3(Context c){


        locationManager = (LocationManager) c.getSystemService(LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(c, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) c,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Explanation not needed, since user requests this themmself

            } else {
                ActivityCompat.requestPermissions((Activity) c,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_ACCESS_FINE_LOCATION);

            }

        } else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Dialog_loaction_loading aa;
            aa = new Dialog_loaction_loading();
            ad = aa.qrcode_reader(c);
            ad.show();
            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, loc_lis(c));
            }
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, loc_lis(c));
            }
        } else {
            showLocationSettingsDialog(c);
        }
    }

    private void showLocationSettingsDialog(Context c) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(c);
        alertDialog.setTitle("تنظیم موقعیت مکانی");
        alertDialog.setMessage("مکان نمای " +
                "گوشی فعال نیست. آیا میخواید در تنظیمات آن را فعال کنید؟");
        alertDialog.setPositiveButton("تنظیمات", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                c.startActivity(intent);
            }
        });
        alertDialog.setNegativeButton("خیر", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }
    public LocationListener loc_lis(Context c) {

        LocationListener a = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

               // ad.dismiss();

                try {
                    locationManager.removeUpdates(this);
                } catch (SecurityException e) {
                    Log.e("LocationManager", "Error while trying to stop listening for location updates. This is probably a permissions issue", e);
                }
                Log.i("LOCATION (" + location.getProvider().toUpperCase() + ")", location.getLatitude() + ", " + location.getLongitude());
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(c.getApplicationContext());
                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                prefsEditor.putFloat("latitude", (float) latitude);
                prefsEditor.putFloat("longitude", (float) longitude);
                prefsEditor.commit();
               // getJSON("lat=" + (float) latitude + "&" + "lon=" + (float) longitude);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        return a;
    }
}
