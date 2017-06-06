package only.sinha.android.mausam.app.webservice;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.sinha.android.ExceptionTracker;
import com.sinha.android.converter.JacksonConverterFactory;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import only.sinha.android.mausam.app.module.model.response.localweather.LocalWeatherResponse;
import retrofit2.Call;
import retrofit2.Retrofit;

public class MyWebService {

    private static MyWebService sMyWebService = new MyWebService();

    public static MyWebService getInstance() {
        return sMyWebService;
    }

    private MausamService mSportsCafeService;

    private ObjectMapper mObjectMapper;

    public void init(String baseUrl) {
        // Initialize ObjectMapper
        initObjectMapper();

        // Initialize Mausam Api service using retrofit
        initMausamService(baseUrl);
    }

    private void initMausamService(String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create(mObjectMapper))
                .client(getCustomHttpClient())
                .build();
        mSportsCafeService = retrofit.create(MausamService.class);
    }

    private OkHttpClient getCustomHttpClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(30, TimeUnit.SECONDS);
        httpClient.readTimeout(30, TimeUnit.SECONDS);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        // add logging as last interceptor
        httpClient.addInterceptor(logging);

        return httpClient.build();
    }

    private void initObjectMapper() {
        mObjectMapper = new ObjectMapper();
        mObjectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public <T> String getJsonStringFromObject(T object) {
        try {
            return mObjectMapper.writeValueAsString(object);
        } catch (Exception e) {
            ExceptionTracker.track(e);
        }
        return null;
    }

    public <T> T getObjectFromJson(String json, Class<T> classType) {
        try {
            return mObjectMapper.readValue(json, classType);
        } catch (Exception e) {
            ExceptionTracker.track(e);
        }
        return null;
    }

    public <T> T getObjectFromJson(String json, CollectionType classType) {
        try {
            return mObjectMapper.readValue(json, classType);
        } catch (Exception e) {
            ExceptionTracker.track(e);
        }
        return null;
    }

    public <T> T getObjectFromJson(String json, TypeReference<T> typeReference) {
        try {
            return mObjectMapper.readValue(json, typeReference);
        } catch (Exception e) {
            ExceptionTracker.track(e);
        }
        return null;
    }

    public <T> T getObjectFromObject(Object object, Class<T> classType) {
        try {
            return (T) mObjectMapper.convertValue(object, Map.class);
        } catch (Exception e) {
            ExceptionTracker.track(e);
        }
        return null;
    }

    public Call<LocalWeatherResponse> callLocalWeatherApi(Map<String, String> map) {
        return mSportsCafeService.callLocalWeatherApi(map);
    }
}