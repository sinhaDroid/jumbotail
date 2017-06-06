package only.sinha.android.mausam.app.module.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;

import butterknife.BindView;
import butterknife.ButterKnife;
import only.sinha.android.mausam.app.Constants;
import only.sinha.android.mausam.app.R;
import only.sinha.android.mausam.app.module.AppSnippet;
import only.sinha.android.mausam.app.module.common.Adapter;
import only.sinha.android.mausam.app.module.model.response.localweather.WeatherItem;

/**
 * Created by deepanshu on 1/6/17.
 */

public class DailyAdapter extends Adapter<WeatherItem, DailyAdapter.MyViewHolder> {

    public DailyAdapter(Context context) {
        super(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(getLayoutInflater().inflate(R.layout.inflater_daily_detail_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.daily_day_tv)
        TextView day;

        @BindView(R.id.daily_date_tv)
        TextView date;

        @BindView(R.id.daily_icon_iv)
        ImageView dailyIcon;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(WeatherItem weatherItem) {
            String date = weatherItem.getDate();
            try {
                this.day.setText(AppSnippet.getWeekOfYear(weatherItem.getDate(), Constants.DayTime.YYYYMMDD));
                this.date.setText(AppSnippet.getDayMonth(date, Constants.DayTime.YYYYMMDD));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            dailyIcon.setImageResource(AppSnippet.getWeatherBitMapResource(weatherItem.getHourly().get(0).getWeatherCode()));
        }
    }
}
