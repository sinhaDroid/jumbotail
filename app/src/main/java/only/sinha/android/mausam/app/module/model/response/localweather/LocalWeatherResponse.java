package only.sinha.android.mausam.app.module.model.response.localweather;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class LocalWeatherResponse {

    @JsonProperty("data")
    private Data data;

    public LocalWeatherResponse() {
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    @Override
    public String toString() {
        return
                "LocalWeatherResponse{" +
                        "data = '" + data + '\'' +
                        "}";
    }
}