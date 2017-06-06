package only.sinha.android.mausam.app.module.model.response.localweather;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class WeatherItem {

    @JsonProperty("date")
    private String date;

    @JsonProperty("sunHour")
    private String sunHour;

    @JsonProperty("mintempF")
    private String mintempF;

    @JsonProperty("mintempC")
    private String mintempC;

    @JsonProperty("totalSnow_cm")
    private String totalSnowCm;

    @JsonProperty("maxtempF")
    private String maxtempF;

    @JsonProperty("hourly")
    private List<HourlyItem> hourly;

    @JsonProperty("astronomy")
    private List<AstronomyItem> astronomy;

    @JsonProperty("uvIndex")
    private String uvIndex;

    @JsonProperty("maxtempC")
    private String maxtempC;

    public WeatherItem() {
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setSunHour(String sunHour) {
        this.sunHour = sunHour;
    }

    public String getSunHour() {
        return sunHour;
    }

    public void setMintempF(String mintempF) {
        this.mintempF = mintempF;
    }

    public String getMintempF() {
        return mintempF;
    }

    public void setMintempC(String mintempC) {
        this.mintempC = mintempC;
    }

    public String getMintempC() {
        return mintempC;
    }

    public void setTotalSnowCm(String totalSnowCm) {
        this.totalSnowCm = totalSnowCm;
    }

    public String getTotalSnowCm() {
        return totalSnowCm;
    }

    public void setMaxtempF(String maxtempF) {
        this.maxtempF = maxtempF;
    }

    public String getMaxtempF() {
        return maxtempF;
    }

    public void setHourly(List<HourlyItem> hourly) {
        this.hourly = hourly;
    }

    public List<HourlyItem> getHourly() {
        return hourly;
    }

    public void setAstronomy(List<AstronomyItem> astronomy) {
        this.astronomy = astronomy;
    }

    public List<AstronomyItem> getAstronomy() {
        return astronomy;
    }

    public void setUvIndex(String uvIndex) {
        this.uvIndex = uvIndex;
    }

    public String getUvIndex() {
        return uvIndex;
    }

    public void setMaxtempC(String maxtempC) {
        this.maxtempC = maxtempC;
    }

    public String getMaxtempC() {
        return maxtempC;
    }

    @Override
    public String toString() {
        return
                "WeatherItem{" +
                        "date = '" + date + '\'' +
                        ",sunHour = '" + sunHour + '\'' +
                        ",mintempF = '" + mintempF + '\'' +
                        ",mintempC = '" + mintempC + '\'' +
                        ",totalSnow_cm = '" + totalSnowCm + '\'' +
                        ",maxtempF = '" + maxtempF + '\'' +
                        ",hourly = '" + hourly + '\'' +
                        ",astronomy = '" + astronomy + '\'' +
                        ",uvIndex = '" + uvIndex + '\'' +
                        ",maxtempC = '" + maxtempC + '\'' +
                        "}";
    }
}