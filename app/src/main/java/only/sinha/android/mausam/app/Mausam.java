package only.sinha.android.mausam.app;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.SystemClock;
import android.support.multidex.MultiDex;

import com.sinha.android.Log;
import com.sinha.android.volley.Volley;
import com.sinha.android.widgets.customfont.CustomFont;

import only.sinha.android.mausam.app.module.offline.LocalWeatherDataHandler;
import only.sinha.android.mausam.app.module.offline.MausamDataHandler;
import only.sinha.android.mausam.app.webservice.MyWebService;

/**
 * Created by deepanshu on 1/6/17.
 */

public class Mausam extends Application {

    private static final boolean mDebuggable = BuildConfig.DEBUG;

    private static Mausam sJumboTail;

    public static Mausam getInstance() {
        return sJumboTail;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Assigning the Mausam instance
        sJumboTail = Mausam.this;

        // Initializing all the data handlers
        initDataHandlers();

        // Initializing MyWebService
        MyWebService.getInstance().init(Config.API_BASE_URL);

        // Instantiating the volley
        Volley.getInstance().initVolley(getApplicationContext());

        // Initializing custom fonts
        initCustomFonts();

        if (MausamDataHandler.getInstance().isFirstTime()) {
            // start the service
            registerAlarm(this);
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        if (mDebuggable) {
            MultiDex.install(this);
        }
    }

    private void initDataHandlers() {
        Context context = getApplicationContext();

        MausamDataHandler.getInstance().init(context);
        LocalWeatherDataHandler.getInstance().init(context);
    }

    private void initCustomFonts() {
        CustomFont.getInstance().init(
                "fonts/Roboto-Medium.ttf",
                "fonts/Roboto-Regular.ttf",
                "fonts/RobotoCondensed-Bold.ttf",
                "fonts/HelveticaNeue-Thin.ttf"
        );
    }

    public static void registerAlarm(Context context) {
        Intent intent = new Intent(context, BroadcastService.class);
        intent.putExtra(Constants.BundleKeys.CITY_KEY, MausamDataHandler.getInstance().getCurrentCityName());

        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, Constants.CODE.REQUEST_CODE, intent, 0);

        // Schedule the alarm!
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);

        // Wake up the device to fire the alarm in 1 hour, and every 1 hour after that:
        am.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_FIFTEEN_MINUTES,
                AlarmManager.INTERVAL_FIFTEEN_MINUTES, alarmIntent);
        Log.d("Mausam", "setup");

    }

    /**
     * Checking the internet connectivity
     *
     * @return true if the connection is available otherwise false
     */
    public boolean hasNetworkConnection() {
        NetworkInfo activeNetwork = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
