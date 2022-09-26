package com.example.nutechwallet.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

        public class LocationModule {

        protected Context context;
        private Activity activity;
        private LocationRequest mLocationRequest;
        private FusedLocationProviderClient mFusedLocationClient;
        private LocationCallback mLocationCallback;
        private Location mLocation;
        private int UPDATE_INTERVAL = 10000; // 10 sec
        private int FATEST_INTERVAL = 5000; // 5 sec
        private int DISPLACEMENT = 10; // 10 meters
        private Location lastMockLocation;
        private int numGoodReadings, PERMISSION_REQUEST = 8008, GPS_REQUEST = 8009;

        private boolean changeSettings;
        private LocationRequest locationRequest;
        private Status locationStatus;
        private boolean mockLocationsEnabled;
        private int numTimesPermissionDeclined;
        protected LocationResult onResult = null;
        private GoogleApiClient googleApiClient;
        private LocationSettingsRequest mLocationSettingsRequest;


        public void setOnResultApi(LocationResult onResultApi){
        this.onResult = onResultApi;
        }

        public LocationModule(Context context){
        this.context = context;
        if (context instanceof Activity)
        this.activity = (Activity) context;
        init();
        }

        public interface LocationResult{
        void onGetLocation(Location location);
        void onMockLocationsDetected(String message);
        }

        private void init(){
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(com.google.android.gms.location.LocationResult locationResult) {
        onLocationChanged(locationResult.getLastLocation());
        }
        };
        }

        public void getLocation(){
        getLocation(null);
        }

        public void getLocation(LocationResult result){
        if (result == null && onResult == null) return;

        if (result != null) this.onResult = result;

        if ((ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) &&
        (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
        requestPermission();
        return;
        }

        startLocation();
        }

        public void startLocation(){
        if (!checkProviders()) {
        turnGPSOn();
        return;
        }

        createLocationRequest();
        getLastLocation();
        requestLocationUpdates();
        }

        private void requestPermission(){
        if (numTimesPermissionDeclined >= 2){
        onResult.onMockLocationsDetected("Aksi ini ditolak. Mohon aktifkan perizinan lokasi melalui pengaturan aplikasi.");
        return;
        }

        ActivityCompat.requestPermissions((Activity) context, new String[] {
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        }, PERMISSION_REQUEST);
        }

        public void onPermissionsUpdated(int requestCode, int[] grantResults) {
        if (requestCode != PERMISSION_REQUEST) return;
        Boolean isGranted = true;
        if (grantResults.length > 0) {
        for (int grantResult : grantResults) {
        if (grantResult != PackageManager.PERMISSION_GRANTED) {
        isGranted = false;
        break;
        }
        }
        if (isGranted)
        startLocation();
        else {numTimesPermissionDeclined ++;requestPermission();}
        } else {
        numTimesPermissionDeclined++;
        requestPermission();
        }
        }

        public void onActivityResult(int requestCode, int resultCode) {
        if (requestCode != GPS_REQUEST) return;
        if (resultCode == Activity.RESULT_OK) {
        startLocation();
        } else if (resultCode == Activity.RESULT_CANCELED){
        onResult.onMockLocationsDetected("Mohon Aktifkan Lokasi Agar Dapat Melanjutkan Aksi ini");
        }
        }

        private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FATEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
        .addLocationRequest(locationRequest);
        mLocationSettingsRequest = builder.build();
        builder.setAlwaysShow(true);
        }

        private void getLastLocation() {
        try {
        mFusedLocationClient.getLastLocation()
        .addOnCompleteListener(new OnCompleteListener<Location>() {
        @Override
        public void onComplete(Task<Location> task) {
        if (task.isSuccessful() && task.getResult() != null) {
        mLocation = task.getResult();
        }
        }
        });
        } catch (SecurityException unlikely) {

        }
        }

        private boolean isLocationPlausible(Location location) {
        if (location == null) return false;
        boolean isMock = isMockLocation() || (Build.VERSION.SDK_INT >= 18 && location.isFromMockProvider());
        if (isMock) {
        lastMockLocation = location;
        numGoodReadings = 0;
        } else
        numGoodReadings = Math.min(numGoodReadings + 1, 1000000); // Prevent overflow
        // We only clear that incident record after a significant show of good behavior
        if (numGoodReadings >= 20) lastMockLocation = null;
        // If there's nothing to compare against, we have to trust it
        if (lastMockLocation == null) return true;
        // And finally, if it's more than 1km away from the last known mock, we'll trust it
        double d = location.distanceTo(lastMockLocation);
        return (d > 1000);
        }

        private Boolean checkProviders() {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        return (gps || network);
        }

        private void turnGPSOn() {
        googleApiClient = new GoogleApiClient.Builder((Activity) context)
        .addApi(LocationServices.API)
        .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
        @Override
        public void onConnected(@Nullable Bundle bundle) {

        }

        @Override
        public void onConnectionSuspended(int i) {
        googleApiClient.connect();
        }
        })
        .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
        @Override
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        }
        }).build();
        googleApiClient.connect();

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
        mLocationRequest.setFastestInterval(FATEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new
        LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> task=LocationServices.getSettingsClient(context).checkLocationSettings(builder.build());
        task.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
        @Override
        public void onComplete(Task<LocationSettingsResponse> task) {
        try {
        LocationSettingsResponse response = task.getResult(ApiException.class);
        } catch (ApiException e) {
        int statusCode = ((ApiException) e).getStatusCode();
        switch (statusCode) {
        case LocationSettingsStatusCodes.SUCCESS:
        startLocation();
        break;
        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
        try {
        ResolvableApiException rae = (ResolvableApiException) e;
        rae.startResolutionForResult((Activity) context, GPS_REQUEST);
        } catch (IntentSender.SendIntentException sie) {
        String errorMessage = "Mohon Aktifkan Lokasi di pengaturan";
        onResult.onMockLocationsDetected(errorMessage);
        }
        break;
        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
        String errorMessage = "Mohon Aktifkan Lokasi di pengaturan";
        onResult.onMockLocationsDetected(errorMessage);
        break;
        }
        }
        }
        });
        }


        private boolean isMockLocation(){
        if (Settings.Secure.getString(context.getContentResolver(),
        Settings.Secure.ALLOW_MOCK_LOCATION).equals("0"))
        return false;
        else return true;
        }

        private void requestLocationUpdates() {
        try {
        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
        mLocationCallback, null);
        } catch (SecurityException unlikely) {
        }
        }

        private void removeLocationUpdates() {
        try {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        } catch (SecurityException unlikely) {}
        }

        private void onLocationChanged(Location location) {
        try {
        mLocation = location;
        if(!isLocationPlausible(location) || (location.getLatitude() == 0.0D || location.getLongitude() == 0.0D)) {
        if (onResult != null) onResult.onMockLocationsDetected("Lokasi Anda Tidak Diketahui");
        return;
        }

        if (onResult != null) {
        onResult.onGetLocation(location);
        removeLocationUpdates();
        }
        } catch (Exception e){
        e.printStackTrace();
        }
        }
        }
