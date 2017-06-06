package only.sinha.android.mausam.app.module.model.response.localweather;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class CurrentConditionItem {

    @JsonProperty("precipMM")
    private String precipMM;

    @JsonProperty("observation_time")
    private String observationTime;

    @JsonProperty("weatherDesc")
    private List<WeatherDescItem> weatherDesc;

    @JsonProperty("visibility")
    private String visibility;

    @JsonProperty("weatherCode")
    private String weatherCode;

    @JsonProperty("FeelsLikeF")
    private String feelsLikeF;

    @JsonProperty("pressure")
    private String pressure;

    @JsonProperty("temp_C")
    private String tempC;

    @JsonProperty("temp_F")
    private String tempF;

    @JsonProperty("cloudcover")
    private String cloudcover;

    @JsonProperty("windspeedMiles")
    private String windspeedMiles;

    @JsonProperty("winddirDegree")
    private String winddirDegree;

    @JsonProperty("FeelsLikeC")
    private String feelsLikeC;

    @JsonProperty("windspeedKmph")
    private String windspeedKmph;

    @JsonProperty("humidity")
    private String humidity;

    @JsonProperty("winddir16Point")
    private String winddir16Point;

    @JsonProperty("weatherIconUrl")
    private List<WeatherIconUrlItem> weatherIconUrl;

    public CurrentConditionItem() {
    }

    public void setPrecipMM(String precipMM) {
        this.precipMM = precipMM;
    }

    public String getPrecipMM() {
        return precipMM;
    }

    public void setObservationTime(String observationTime) {
        this.observationTime = observationTime;
    }

    public String getObservationTime() {
        return observationTime;
    }

    public void setWeatherDesc(List<WeatherDescItem> weatherDesc) {
        this.weatherDesc = weatherDesc;
    }

    public List<WeatherDescItem> getWeatherDesc() {
        return weatherDesc;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setWeatherCode(String weatherCode) {
        this.weatherCode = weatherCode;
    }

    public String getWeatherCode() {
        return weatherCode;
    }

    public void setFeelsLikeF(String feelsLikeF) {
        this.feelsLikeF = feelsLikeF;
    }

    public String getFeelsLikeF() {
        return feelsLikeF;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getPressure() {
        return pressure;
    }

    public void setTempC(String tempC) {
        this.tempC = tempC;
    }

    public String getTempC() {
        return tempC;
    }

    public void setTempF(String tempF) {
        this.tempF = tempF;
    }

    public String getTempF() {
        return tempF;
    }

    public void setCloudcover(String cloudcover) {
        this.cloudcover = cloudcover;
    }

    public String getCloudcover() {
        return cloudcover;
    }

    public void setWindspeedMiles(String windspeedMiles) {
        this.windspeedMiles = windspeedMiles;
    }

    public String getWindspeedMiles() {
        return windspeedMiles;
    }

    public void setWinddirDegree(String winddirDegree) {
        this.winddirDegree = winddirDegree;
    }

    public String getWinddirDegree() {
        return winddirDegree;
    }

    public void setFeelsLikeC(String feelsLikeC) {
        this.feelsLikeC = feelsLikeC;
    }

    public String getFeelsLikeC() {
        return feelsLikeC;
    }

    public void setWindspeedKmph(String windspeedKmph) {
        this.windspeedKmph = windspeedKmph;
    }

    public String getWindspeedKmph() {
        return windspeedKmph;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setWinddir16Point(String winddir16Point) {
        this.winddir16Point = winddir16Point;
    }

    public String getWinddir16Point() {
        return winddir16Point;
    }

    public void setWeatherIconUrl(List<WeatherIconUrlItem> weatherIconUrl) {
        this.weatherIconUrl = weatherIconUrl;
    }

    public List<WeatherIconUrlItem> getWeatherIconUrl() {
        return weatherIconUrl;
    }

    @Override
    public String toString() {
        return
                "CurrentConditionItem{" +
                        "precipMM = '" + precipMM + '\'' +
                        ",observation_time = '" + observationTime + '\'' +
                        ",weatherDesc = '" + weatherDesc + '\'' +
                        ",visibility = '" + visibility + '\'' +
                        ",weatherCode = '" + weatherCode + '\'' +
                        ",feelsLikeF = '" + feelsLikeF + '\'' +
                        ",pressure = '" + pressure + '\'' +
                        ",temp_C = '" + tempC + '\'' +
                        ",temp_F = '" + tempF + '\'' +
                        ",cloudcover = '" + cloudcover + '\'' +
                        ",windspeedMiles = '" + windspeedMiles + '\'' +
                        ",winddirDegree = '" + winddirDegree + '\'' +
                        ",feelsLikeC = '" + feelsLikeC + '\'' +
                        ",windspeedKmph = '" + windspeedKmph + '\'' +
                        ",humidity = '" + humidity + '\'' +
                        ",winddir16Point = '" + winddir16Point + '\'' +
                        ",weatherIconUrl = '" + weatherIconUrl + '\'' +
                        "}";
    }
}