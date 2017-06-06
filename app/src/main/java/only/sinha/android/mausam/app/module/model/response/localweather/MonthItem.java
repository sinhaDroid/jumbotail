package only.sinha.android.mausam.app.module.model.response.localweather;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class MonthItem {

    @JsonProperty("absMaxTemp")
    private String absMaxTemp;

    @JsonProperty("avgMinTemp_F")
    private String avgMinTempF;

    @JsonProperty("name")
    private String name;

    @JsonProperty("index")
    private String index;

    @JsonProperty("absMaxTemp_F")
    private String absMaxTempF;

    @JsonProperty("avgDailyRainfall")
    private String avgDailyRainfall;

    @JsonProperty("avgMinTemp")
    private String avgMinTemp;

    public MonthItem() {
    }

    public void setAbsMaxTemp(String absMaxTemp) {
        this.absMaxTemp = absMaxTemp;
    }

    public String getAbsMaxTemp() {
        return absMaxTemp;
    }

    public void setAvgMinTempF(String avgMinTempF) {
        this.avgMinTempF = avgMinTempF;
    }

    public String getAvgMinTempF() {
        return avgMinTempF;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getIndex() {
        return index;
    }

    public void setAbsMaxTempF(String absMaxTempF) {
        this.absMaxTempF = absMaxTempF;
    }

    public String getAbsMaxTempF() {
        return absMaxTempF;
    }

    public void setAvgDailyRainfall(String avgDailyRainfall) {
        this.avgDailyRainfall = avgDailyRainfall;
    }

    public String getAvgDailyRainfall() {
        return avgDailyRainfall;
    }

    public void setAvgMinTemp(String avgMinTemp) {
        this.avgMinTemp = avgMinTemp;
    }

    public String getAvgMinTemp() {
        return avgMinTemp;
    }

    @Override
    public String toString() {
        return
                "MonthItem{" +
                        "absMaxTemp = '" + absMaxTemp + '\'' +
                        ",avgMinTemp_F = '" + avgMinTempF + '\'' +
                        ",name = '" + name + '\'' +
                        ",index = '" + index + '\'' +
                        ",absMaxTemp_F = '" + absMaxTempF + '\'' +
                        ",avgDailyRainfall = '" + avgDailyRainfall + '\'' +
                        ",avgMinTemp = '" + avgMinTemp + '\'' +
                        "}";
    }
}