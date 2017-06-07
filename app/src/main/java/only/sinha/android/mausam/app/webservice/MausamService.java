package only.sinha.android.mausam.app.webservice;


import java.util.Map;

import only.sinha.android.mausam.app.module.model.response.geocode.GeoCodeResponse;
import only.sinha.android.mausam.app.module.model.response.localweather.LocalWeatherResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

interface MausamService {

    @GET("premium/v1/weather.ashx")
    Call<LocalWeatherResponse> callLocalWeatherApi(@QueryMap Map<String, String> map);

    @GET("maps/api/geocode/json")
    Call<GeoCodeResponse> callGeoApi(@Query("latlng") String latLng, @Query("sensor") boolean lng);
}