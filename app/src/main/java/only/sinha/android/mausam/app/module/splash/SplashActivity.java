package only.sinha.android.mausam.app.module.splash;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import only.sinha.android.mausam.app.Mausam;
import only.sinha.android.mausam.app.R;
import only.sinha.android.mausam.app.module.MainActivity;
import only.sinha.android.mausam.app.module.common.MausamActivity;
import only.sinha.android.mausam.app.module.model.request.LocalWeatherRequest;
import only.sinha.android.mausam.app.module.model.response.localweather.LocalWeatherResponse;
import only.sinha.android.mausam.app.module.offline.LocalWeatherDataHandler;
import only.sinha.android.mausam.app.module.offline.MausamDataHandler;
import only.sinha.android.mausam.app.webservice.MausamCallBack;
import only.sinha.android.mausam.app.webservice.MyWebService;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by deepanshu on 5/6/17.
 */

public class SplashActivity extends MausamActivity implements LocationListener {

    private LocationManager mLocationManager;

    private String bestProvider;

    String[] permission = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};

    private static final int requestPermissionCode = 200;

    @BindView(R.id.splash_gif_one)
    ImageView mSplashImageOne;

    @BindView(R.id.splash_gif_two)
    ImageView mSplashImageTwo;

    @BindView(R.id.splash_gif_three)
    ImageView mSplashImageThree;

    @BindView(R.id.splash_gif_four)
    ImageView mSplashImageFour;

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
                    callWeatherApi(appLinkData.getLastPathSegment());
                } else if (MausamDataHandler.getInstance().isFirstTime()) {
                    askForPermission();
                } else {
                    startMainActivity();
                }
            }
        }, 3000);

        setGifUsingGlide(mSplashImageOne, "http://www.animatedimages.org/data/media/148/animated-weather-image-0012.gif");
        setGifUsingGlide(mSplashImageTwo, "http://www.animatedimages.org/data/media/148/animated-weather-image-0004.gif");
        setGifUsingGlide(mSplashImageThree, "http://www.animatedimages.org/data/media/148/animated-weather-image-0005.gif");
        setGifUsingGlide(mSplashImageFour, "http://www.animatedimages.org/data/media/148/animated-weather-image-0062.gif");
    }

    private void setGifUsingGlide(ImageView imageView, String url) {
        Glide.with(this)
                .load(url)
                .asGif()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }

    private void askForPermission() {
        if (ContextCompat.checkSelfPermission(this, permission[0]) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, permission[1]) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, permission[1])) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(this, new String[]{permission[0], permission[1]}, requestPermissionCode);

            } else {

                ActivityCompat.requestPermissions(this, new String[]{permission[0], permission[1]}, requestPermissionCode);
            }
        } else {
            takeCurrentCityName();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestPermissionCode == requestCode) {

            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                takeCurrentCityName();
            } else {
                askForPermission();
            }
        }
    }

    private void takeCurrentCityName() {
        //TO get the location,manifest file is added with 2 permissions
        //Location Manager is used to figure out which location provider needs to be used
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //Best location provider is decided by the criteria
        Criteria criteria = new Criteria();
        //location manager will take the best location from the criteria
        bestProvider = mLocationManager.getBestProvider(criteria, false);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestPermissionCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        //once you know the name of the LocationProvider, you can call getLastKnownPosition() to find out where you were recently.
        Location location = mLocationManager.getLastKnownLocation(bestProvider);

        if (null != location) {
            onLocationChanged(location);
        } else {
            // TODO:
            // show no location
        }
    }

    private void startMainActivity() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void onLocationChanged(Location location) {
        //You had this as int. It is advised to have Lat/Loing as double.
        double lat = location.getLatitude();
        double lng = location.getLongitude();

        Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
        List<Address> address;

        try {
            address = geoCoder.getFromLocation(lat, lng, 1);
            String cityName = address.get(0).getLocality();

            callWeatherApi(cityName);
        } catch (IOException | NullPointerException e) {
            // Handle IOException
            e.printStackTrace();
        }
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

    private void callWeatherApi(String cityName) {
        MausamDataHandler.getInstance().setCurrentCityName(cityName);

        if (Mausam.getInstance().hasNetworkConnection()) {

            MyWebService.getInstance().callLocalWeatherApi(
                    new LocalWeatherRequest.Builder()
                            .setQ(cityName)
                            .setNum_of_days(9)
                            .setTp("1")
                            .setShowlocaltime("yes")
                            .build()
            ).enqueue(new MausamCallBack<LocalWeatherResponse>() {
                @Override
                public void onResponse(Call<LocalWeatherResponse> call, Response<LocalWeatherResponse> response) {

                    if (response.isSuccessful())
                        handleResponse(response.body());

                    else showMessage(response.message());
                }
            });
        } else showMessage(R.string.no_internet_connection_available);
    }

    private void handleResponse(LocalWeatherResponse response) {

        LocalWeatherDataHandler.getInstance().saveWeatherByCityName(MausamDataHandler.getInstance().getCurrentCityName(), response);
        MausamDataHandler.getInstance().setFirstTimeOver();

        startMainActivity();
    }
}
