package only.sinha.android.mausam.app.module.common.widget;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import only.sinha.android.mausam.app.module.offline.LocalWeatherDataHandler;
import only.sinha.android.mausam.app.module.weather.view.WeatherFragment;

/**
 * Created by deepanshu on 4/6/17.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    private List<String> keyStringList;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
        keyStringList = new ArrayList<>(LocalWeatherDataHandler.getInstance().getWeatherByCityName().keySet());
    }

    @Override
    public Fragment getItem(int position) {
        return WeatherFragment.newInstance(keyStringList.get(position));
    }

    @Override
    public int getCount() {
        return keyStringList.size();
    }
}
