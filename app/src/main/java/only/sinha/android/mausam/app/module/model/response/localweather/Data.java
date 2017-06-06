package only.sinha.android.mausam.app.module.model.response.localweather;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class Data {

    @JsonProperty("request")
    private List<RequestItem> request;

    @JsonProperty("time_zone")
    private List<TimeZone> timeZone;

    public Data() {
    }

    @JsonProperty("current_condition")
    private List<CurrentConditionItem> currentCondition;

    @JsonProperty("weather")
    private List<WeatherItem> weather;

    @JsonProperty("ClimateAverages")
    private List<ClimateAveragesItem> climateAverages;

    public void setRequest(List<RequestItem> request) {
        this.request = request;
    }

    public List<RequestItem> getRequest() {
        return request;
    }

    public void setTimeZone(List<TimeZone> timeZone) {
        this.timeZone = timeZone;
    }

    public List<TimeZone> getTimeZone() {
        return timeZone;
    }

    public void setCurrentCondition(List<CurrentConditionItem> currentCondition) {
        this.currentCondition = currentCondition;
    }

    public List<CurrentConditionItem> getCurrentCondition() {
        return currentCondition;
    }

    public void setWeather(List<WeatherItem> weather) {
        this.weather = weather;
    }

    public List<WeatherItem> getWeather() {
        return weather;
    }

    public void setClimateAverages(List<ClimateAveragesItem> climateAverages) {
        this.climateAverages = climateAverages;
    }

    public List<ClimateAveragesItem> getClimateAverages() {
        return climateAverages;
    }

    @Override
    public String toString() {
        return
                "Data{" +
                        "request = '" + request + '\'' +
                        ",current_condition = '" + currentCondition + '\'' +
                        ",weather = '" + weather + '\'' +
                        ",climateAverages = '" + climateAverages + '\'' +
                        "}";
    }
}