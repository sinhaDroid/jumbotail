package only.sinha.android.mausam.app.module.model.response.localweather;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class WeatherIconUrlItem {

    @JsonProperty("value")
    private String value;

    public WeatherIconUrlItem() {
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return
                "WeatherIconUrlItem{" +
                        "value = '" + value + '\'' +
                        "}";
    }
}