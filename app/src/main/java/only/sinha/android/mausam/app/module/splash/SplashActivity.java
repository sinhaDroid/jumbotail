package only.sinha.android.mausam.app.module.splash;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import butterknife.BindView;
import only.sinha.android.mausam.app.Constants;
import only.sinha.android.mausam.app.Mausam;
import only.sinha.android.mausam.app.R;
import only.sinha.android.mausam.app.module.MainActivity;
import only.sinha.android.mausam.app.module.common.MausamActivity;
import only.sinha.android.mausam.app.module.model.response.geocode.AddressComponentsItem;
import only.sinha.android.mausam.app.module.model.response.geocode.GeoCodeResponse;
import only.sinha.android.mausam.app.module.offline.MausamDataHandler;
import only.sinha.android.mausam.app.webservice.MausamCallBack;
import only.sinha.android.mausam.app.webservice.MyWebService;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by deepanshu on 5/6/17.
 */

public class SplashActivity extends MausamActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private static final int PERMISSION_REQUEST_CODE_LOCATION = 1;

    private static final int REQUEST_CHECK_SETTINGS = 1000;

    @BindView(R.id.splash_gif_one)
    ImageView mSplashImageOne;

    @BindView(R.id.msg)
    TextView mMsg;

    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;

    Location mLastLocation;

    LocationRequest mLocationRequest;

    @Override
    protected int getContentView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onViewCreated(Bundle savedInstanceState) {
        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        final String appLinkAction = appLinkIntent.getAction();
        final Uri appLinkData = appLinkIntent.getData();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Intent.ACTION_VIEW.equals(appLinkAction) && null != appLinkData) {
                    showMessage(appLinkData.getLastPathSegment());
                    callWeatherApi(appLinkData.getLastPathSegment());
                } else if (MausamDataHandler.getInstance().isFirstTime()) {
                    callGooglePlayServicesApi();
                } else {
                    startMainActivity();
                }
            }
        }, 3000);

        setGifUsingGlide(mSplashImageOne, "https://media.giphy.com/media/26u6dryuZH98z5KuY/giphy.gif");
//        setGifUsingGlide(mSplashImageTwo, "http://www.animatedimages.org/data/media/148/animated-weather-image-0004.gif");
//        setGifUsingGlide(mSplashImageThree, "http://www.animatedimages.org/data/media/148/animated-weather-image-0005.gif");
//        setGifUsingGlide(mSplashImageFour, "http://www.animatedimages.org/data/media/148/animated-weather-image-0062.gif");
    }

    @Override
    protected void onStart() {
        super.onStart();
        // register the broadcast
        IntentFilter intentFilter = new IntentFilter(Constants.IntentActions.ACTION_SPLASH);

        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            super.onPause();

            /*
            * Stop retrieving locations when we go out of the application.
            * */
            if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
                stopLocationUpdates();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    protected void onStop() {

        //Disconnect the google client api connection.
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }

        // unregister the broadcast
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);

        super.onStop();
    }

    private void callGeoCodeApi(String latLng) {
        if (Mausam.getInstance().hasNetworkConnection()) {

            MyWebService.getInstance().callGeoApi(latLng)
                    .enqueue(new MausamCallBack<GeoCodeResponse>() {
                        @Override
                        public void onResponse(Call<GeoCodeResponse> call, Response<GeoCodeResponse> response) {

                            if (response.isSuccessful()) {
                                String cityName = "Bengaluru";

                                for (AddressComponentsItem addressComponentsItem : response.body().getResults().get(0).getAddressComponents()) {

                                    if (addressComponentsItem.getTypes().get(0).equalsIgnoreCase("locality")) {
                                        cityName = addressComponentsItem.getLongName();
                                    }
                                }

                                callWeatherApi(cityName);
                            } else {
                                showMessage(response.message());
                            }
                        }
                    });
        } else {
            showMessage(R.string.no_internet_connection_available);

            // if no internet start the service
            mMsg.setText(getString(R.string.no_internet_connection_available));
        }
    }

    private void callWeatherApi(String cityName) {

        if (Mausam.getInstance().hasNetworkConnection()) {

            MyWebService.getInstance().callWeatherApi(Constants.IntentActions.ACTION_SPLASH, cityName);
        } else {
            showMessage(R.string.no_internet_connection_available);

            // if no internet start the service
            mMsg.setText(getString(R.string.no_internet_connection_available));
        }
    }

    private void callGooglePlayServicesApi() {
        /*
        * Setting up the runtime permission. We should implement when we target  Android Marshmallow (API 23) as a target Sdk.
        * */
        requestPermission(PERMISSION_REQUEST_CODE_LOCATION, this);

        try {

            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();

            mLocationRequest = LocationRequest.create();
            mLocationRequest.setInterval(1000);

            mLocationRequest.setFastestInterval(1000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

            checkForLocationReuqestSetting(mLocationRequest);
            mGoogleApiClient.connect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            MausamDataHandler.getInstance().setFirstTimeOver();
            startMainActivity();
        }
    };

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        try {

            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

            callGeoCodeApi("" + mLastLocation.getLatitude() + "," + mLastLocation.getLongitude());

        } catch (SecurityException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    public static void requestPermission(int perCode, Activity _a) {

        String fineLocationPermissionString = Manifest.permission.ACCESS_FINE_LOCATION;
        String coarseLocationPermissionString = Manifest.permission.ACCESS_COARSE_LOCATION;

        if (ContextCompat.checkSelfPermission(_a, fineLocationPermissionString) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(_a, coarseLocationPermissionString) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(_a, new String[]{fineLocationPermissionString, coarseLocationPermissionString}, perCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            case PERMISSION_REQUEST_CODE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callGooglePlayServicesApi();
                } else {
                    requestPermission(PERMISSION_REQUEST_CODE_LOCATION, this);
                }
                break;
        }
    }

    /**
     * Google Fused API require Runtime permission. Runtime permission is available for Android Marshmallow
     * or Greater versions.
     *
     * @param locationRequest needed to check whether we need to prompt settings alert.
     */
    private void checkForLocationReuqestSetting(LocationRequest locationRequest) {
        try {
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());

            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
                    final Status status = locationSettingsResult.getStatus();

                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            // All location settings are satisfied. The client can
                            // initialize location requests here.
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied, but this can be fixed
                            // by showing the user a dialog.
                            try {
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                status.startResolutionForResult(
                                        SplashActivity.this,
                                        REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have no way
                            // to fix the settings so we won't show the dialog.

                            break;
                    }

                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CHECK_SETTINGS){
            Toast.makeText(this, "Setting has changed...", Toast.LENGTH_SHORT).show();
            requestPermission(PERMISSION_REQUEST_CODE_LOCATION, this);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void setGifUsingGlide(ImageView imageView, String url) {
        Glide.with(this)
                .load(url)
                .asGif()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }

    private void startMainActivity() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }
}
