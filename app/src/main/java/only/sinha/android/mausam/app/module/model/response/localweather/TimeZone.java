package only.sinha.android.mausam.app.module.model.response.localweather;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class TimeZone {

    @JsonProperty("localtime")
    private String localtime;

    @JsonProperty("utcOffset")
    private String utcOffset;

    public TimeZone() {
    }

    public void setLocaltime(String localtime) {
        this.localtime = localtime;
    }

    public String getLocaltime() {
        return localtime;
    }

    public void setUtcOffset(String utcOffset) {
        this.utcOffset = utcOffset;
    }

    public String getUtcOffset() {
        return utcOffset;
    }

    @Override
    public String toString() {
        return
                "TimeZone{" +
                        "localtime = '" + localtime + '\'' +
                        ",utcOffset = '" + utcOffset + '\'' +
                        "}";
    }
}