package only.sinha.android.mausam.app.module;

import android.text.format.Time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import only.sinha.android.mausam.app.Constants;
import only.sinha.android.mausam.app.R;

/**
 * Created by deepanshu on 3/6/17.
 */

public class AppSnippet {

    public static int getDrawable(int num) {
        int hourly_temperature;
        switch (num) {
            case 0:
                hourly_temperature = R.mipmap.hourly_temperature_0;
                break;
            case 1:
                hourly_temperature = R.mipmap.hourly_temperature_1;
                break;
            case 2:
                hourly_temperature = R.mipmap.hourly_temperature_2;
                break;
            case 3:
                hourly_temperature = R.mipmap.hourly_temperature_3;
                break;
            case 4:
                hourly_temperature = R.mipmap.hourly_temperature_4;
                break;
            case 5:
                hourly_temperature = R.mipmap.hourly_temperature_5;
                break;
            case 6:
                hourly_temperature = R.mipmap.hourly_temperature_6;
                break;
            case 7:
                hourly_temperature = R.mipmap.hourly_temperature_7;
                break;
            case 8:
                hourly_temperature = R.mipmap.hourly_temperature_8;
                break;
            case 9:
                hourly_temperature = R.mipmap.hourly_temperature_9;
                break;
            default:
                hourly_temperature = -1;
                break;
        }

        return hourly_temperature;
    }

    public static int getCurrentWeatherBg(int weatherImageId) {
        int imgBgResID;
        switch (weatherImageId) {
            case 113:
                if (isDayTime()) {
                    imgBgResID = R.drawable.bg_sunny_day;
                } else {
                    imgBgResID = R.drawable.bg_sunny_night;
                }
                return imgBgResID;
            case 116:
            case 119:
            case 122:
                if (isDayTime()) {
                    imgBgResID = R.drawable.bg_cloudy_day;
                } else {
                    imgBgResID = R.drawable.bg_cloudy_night;
                }
                return imgBgResID;
            case 143:
            case 248:
                return R.drawable.bg_foggy_day;
            case 176:
            case 182:
            case 185:
            case 200:
            case 263:
            case 266:
            case 281:
            case 284:
            case 293:
            case 296:
            case 299:
            case 302:
            case 305:
            case 308:
            case 311:
            case 314:
            case 353:
            case 356:
            case 359:
            case 362:
            case 365:
            case 386:
            case 389:
                if (isDayTime()) {
                    imgBgResID = R.drawable.bg_rain_day;
                } else {
                    imgBgResID = R.drawable.bg_rain_night;
                }
                return imgBgResID;
            case 179:
            case 227:
            case 230:
            case 260:
            case 317:
            case 320:
            case 323:
            case 325:
            case 326:
            case 329:
            case 332:
            case 338:
            case 350:
            case 368:
            case 371:
            case 374:
            case 377:
            case 392:
            case 395:
                return R.drawable.bg_snow_day;
            default:
                if (isDayTime()) {
                    return R.drawable.bg_sunny_day;
                }
                return R.drawable.bg_sunny_night;
        }
    }



    public static int getWeatherBitMapResource(String weather_img_code) {
        int imgResID = R.drawable.icon_weather_day_00;
        if (weather_img_code == null) {
            return R.drawable.icon_weather_day_00;
        }
        switch (Integer.parseInt(weather_img_code)) {
            case 113:
                if (isDayTime()) {
                    imgResID = R.drawable.icon_weather_day_00;
                } else {
                    imgResID = R.drawable.icon_weather_night_00;
                }
                break;
            case 116:
            case 119:
                if (isDayTime()) {
                    imgResID = R.drawable.icon_weather_day_01;
                } else {
                    imgResID = R.drawable.icon_weather_night_01;
                }
                break;
            case 122:
                imgResID = R.drawable.icon_weather_day_02;
                break;
            case 143:
            case 248:
            case 260:
                if (isDayTime()) {
                    imgResID = R.drawable.icon_weather_day_18;
                } else {
                    imgResID = R.drawable.icon_weather_night_18;
                }
                break;
            case 176:
            case 266:
            case 296:
                imgResID = R.drawable.icon_weather_day_07;
                break;
            case 179:
            case 227:
            case 323:
            case 326:
                imgResID = R.drawable.icon_weather_day_14;
                break;
            case 182:
            case 317:
            case 320:
            case 362:
            case 365:
            case 374:
            case 377:
                imgResID = R.drawable.icon_weather_day_06;
                break;
            case 185:
            case 281:
            case 284:
            case 311:
            case 314:
                imgResID = R.drawable.icon_weather_day_19;
                break;
            case 200:
            case 386:
            case 389:
                imgResID = R.drawable.icon_weather_day_04;
                break;
            case 230:
            case 350:
                imgResID = R.drawable.icon_weather_day_17;
                break;
            case 263:
            case 293:
            case 299:
            case 305:
            case 353:
                if (isDayTime()) {
                    imgResID = R.drawable.icon_weather_day_03;
                } else {
                    imgResID = R.drawable.icon_weather_night_03;
                }
                break;
            case 302:
            case 356:
                imgResID = R.drawable.icon_weather_day_09;
                break;
            case 308:
            case 359:
                imgResID = R.drawable.icon_weather_day_11;
                break;
            case 329:
            case 332:
                imgResID = R.drawable.icon_weather_day_15;
                break;
            case 335:
            case 338:
                imgResID = R.drawable.icon_weather_day_16;
                break;
            case 368:
            case 371:
                if (isDayTime()) {
                    imgResID = R.drawable.icon_weather_day_13;
                } else {
                    imgResID = R.drawable.icon_weather_night_13;
                }
                break;
            case 392:
            case 395:
                imgResID = R.drawable.icon_weather_day_05;
                break;
        }
        return imgResID;
    }

    private static boolean isDayTime() {
        Time time = new Time();
        time.setToNow();
        return time.hour >= Constants.DayTime.DAYTIME_BEGIN_HOUR && time.hour < Constants.DayTime.DAYTIME_END_HOUR;
    }

    private static String getWeekStringByIndex(int index) {
        switch (index) {
            case 1:
                return "SUN";
            case 2:
                return "MON";
            case 3:
                return "TUE";
            case 4:
                return "WED";
            case 5:
                return "THU";
            case 6:
                return "FRI";
            case 7:
                return "SAT";
            default:
                return "SUN";
        }
    }

    public static String getWeekOfYear(String time, String timeFormat) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parseDate(time, timeFormat));
        return getWeekStringByIndex(calendar.get(Calendar.DAY_OF_WEEK));
    }

    public static String getDayMonth(String date, String timeFormat) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parseDate(date, timeFormat));
        return calendar.get(Calendar.MONTH) + 1 + "/" + calendar.get(Calendar.DATE);
    }

    public static Date parseDate(String time, String timeFormat) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(timeFormat, Locale.getDefault());
        return dateFormat.parse(time);
    }

    public static float getViewTox(float horizontalScrollOffset) {
        if (horizontalScrollOffset >= 10 && horizontalScrollOffset < 100) {
            return 0;
        } else if (horizontalScrollOffset >= 100 && horizontalScrollOffset < 225) {
            return 1;
        } else if (horizontalScrollOffset >= 225 && horizontalScrollOffset < 360) {
            return 2;
        } else if (horizontalScrollOffset >= 360) {
            return 3;
        } else {
            return -0.3f;
        }
    }
}
