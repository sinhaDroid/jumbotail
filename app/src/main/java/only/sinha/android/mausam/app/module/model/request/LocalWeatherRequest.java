package only.sinha.android.mausam.app.module.model.request;

import java.util.HashMap;
import java.util.Map;

import only.sinha.android.mausam.app.BuildConfig;

/**
 * Created by deepanshu on 2/6/17.
 */

public class LocalWeatherRequest {

    public static class Builder {

        String q;

        String extra;

        int num_of_days;

        String date;

        String fx;

        String cc;

        String mca;

        String fx24;

        String includelocation;

        String format;

        String show_comments;

        String tp;

        String showlocaltime;

        String callback;

        String lang;

        public Builder setQ(String q) {
            this.q = q;
            return this;
        }

        public Builder setExtra(String extra) {
            this.extra = extra;
            return this;
        }

        public Builder setNum_of_days(int num_of_days) {
            this.num_of_days = num_of_days;
            return this;
        }

        public Builder setDate(String date) {
            this.date = date;
            return this;
        }

        public Builder setFx(String fx) {
            this.fx = fx;
            return this;
        }

        public Builder setCc(String cc) {
            this.cc = cc;
            return this;
        }

        public Builder setMca(String mca) {
            this.mca = mca;
            return this;
        }

        public Builder setFx24(String fx24) {
            this.fx24 = fx24;
            return this;
        }

        public Builder setIncludelocation(String includelocation) {
            this.includelocation = includelocation;
            return this;
        }

        public Builder setFormat(String format) {
            this.format = format;
            return this;
        }

        public Builder setShow_comments(String show_comments) {
            this.show_comments = show_comments;
            return this;
        }

        public Builder setTp(String tp) {
            this.tp = tp;
            return this;
        }

        public Builder setShowlocaltime(String showlocaltime) {
            this.showlocaltime = showlocaltime;
            return this;
        }

        public Builder setCallback(String callback) {
            this.callback = callback;
            return this;
        }

        public Builder setLang(String lang) {
            this.lang = lang;
            return this;
        }

        public Map<String, String> build() {
            Map<String, String> queries = new HashMap<>();

            queries.put("q", q);

            queries.put("key", BuildConfig.API_KEY);

            queries.put("format", "json");

            if (num_of_days != 0)
                queries.put("num_of_days", String.valueOf(num_of_days));

            if (null != extra)
                queries.put("extra", extra);

            if (null != date)
                queries.put("date", date);

            if (null != fx)
                queries.put("fx", fx);

            if (null != cc)
                queries.put("cc",cc);

            if (null != mca)
                queries.put("mca", mca);

            if (null != fx24)
                queries.put("fx24", fx24);

            if (null != includelocation)
                queries.put("includelocation", includelocation);

            if (null != show_comments)
                queries.put("show_comments", show_comments);

            if (null != tp)
                queries.put("tp", tp);

            if (null != showlocaltime)
                queries.put("showlocaltime", showlocaltime);

            if (null != callback)
                queries.put("callback", callback);

            if (null != lang)
                queries.put("lang", lang);

            return queries;
        }
    }
}
