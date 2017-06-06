package only.sinha.android.mausam.app.module.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import only.sinha.android.mausam.app.R;
import only.sinha.android.mausam.app.module.AppSnippet;
import only.sinha.android.mausam.app.module.common.Adapter;
import only.sinha.android.mausam.app.module.model.response.localweather.HourlyItem;

/**
 * Created by deepanshu on 2/6/17.
 */

public class HourlyAdapter extends Adapter<HourlyItem, HourlyAdapter.MyViewHolder> {

    private Context context;

    public HourlyAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(getLayoutInflater().inflate(R.layout.inflater_hourly_detail_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.hourly_time_tv)
        TextView time;

        @BindView(R.id.hourly_iv)
        ImageView weatherIcon;

        @BindView(R.id.hourly_degree_tv)
        TextView degree;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(HourlyItem hourlyItem) {
            String timeValue = hourlyItem.getTime();
            int length = timeValue.length();
            String t;
            if (length == 1) {
                t = "00:00";
            } else if (length == 3) {
                t = "0" + timeValue.substring(0, 1) + ":" + timeValue.substring(1, length);
            } else {
                t = timeValue.substring(0, 2) + ":" + timeValue.substring(2, length);
            }
            time.setText(t);

            weatherIcon.setImageResource(AppSnippet.getWeatherBitMapResource(hourlyItem.getWeatherCode()));

            degree.setText(String.format("%s\u00B0C", hourlyItem.getFeelsLikeC()));
        }
    }
}
