package com.example.siakunin.settingshelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

/**
 * Created by siakunin on 01.03.2018.
 */

public class LocationSettings {

    public static final int REQUEST_CHECK_SETTINGS = 1001;
    private final String TAG = "Location Settings";

    private Activity mActivity;

    private OnLocationSettingsListener mSettingsListener;

    private Task<LocationSettingsResponse> mTask;

    public LocationSettings(@NonNull Activity activity){
        mActivity = activity;
    }

    protected LocationRequest getLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        return mLocationRequest;
    }

    protected void checkCurrentLocationSettings(){
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(getLocationRequest());

        SettingsClient client = LocationServices.getSettingsClient(mActivity);

        mTask = client.checkLocationSettings(builder.build());
        mTask.addOnSuccessListener(mActivity, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                Log.i("TAG","We got required location settings");
                if (mSettingsListener == null){
                    Log.i("TAG","Please set up onLocationSettingsListener");
                }
                else mSettingsListener.onLocationSettingsGranted();

            }
        });
        mTask.addOnFailureListener(mActivity, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(mActivity,
                                REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }
        });
    }

    public LocationSettings addLocationSettingsListener(OnLocationSettingsListener onLocationSettingsListener){
        mSettingsListener = onLocationSettingsListener;
        return this;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CHECK_SETTINGS) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    Log.i(TAG,"User agreed to make required location settings changes.");
                    if (mSettingsListener == null){
                        Log.i("TAG","Please set up onLocationSettingsListener");
                    }
                    else
                    mSettingsListener.onLocationSettingsGranted();

                    break;
                case Activity.RESULT_CANCELED:
                    Log.i(TAG,"User chose not to make required location settings changes.");
                    if (mSettingsListener == null){
                        Log.i("TAG","Please set up onLocationSettingsListener");
                    }
                    else
                    mSettingsListener.onLocationSettingsDenied();

                    break;
            }

        }
    }
}
