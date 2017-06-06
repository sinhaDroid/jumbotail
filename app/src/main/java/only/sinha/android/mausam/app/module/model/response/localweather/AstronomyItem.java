package only.sinha.android.mausam.app.module.model.response.localweather;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class AstronomyItem {

    @JsonProperty("moonset")
    private String moonset;

    @JsonProperty("sunrise")
    private String sunrise;

    @JsonProperty("sunset")
    private String sunset;

    @JsonProperty("moonrise")
    private String moonrise;

    public AstronomyItem() {
    }

    public void setMoonset(String moonset) {
        this.moonset = moonset;
    }

    public String getMoonset() {
        return moonset;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getSunset() {
        return sunset;
    }

    public void setMoonrise(String moonrise) {
        this.moonrise = moonrise;
    }

    public String getMoonrise() {
        return moonrise;
    }

    @Override
    public String toString() {
        return
                "AstronomyItem{" +
                        "moonset = '" + moonset + '\'' +
                        ",sunrise = '" + sunrise + '\'' +
                        ",sunset = '" + sunset + '\'' +
                        ",moonrise = '" + moonrise + '\'' +
                        "}";
    }
}