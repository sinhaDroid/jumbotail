package only.sinha.android.mausam.app.module.model.response.localweather;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class ClimateAveragesItem {

    @JsonProperty("month")
    private List<MonthItem> month;

    public ClimateAveragesItem() {
    }

    public void setMonth(List<MonthItem> month) {
        this.month = month;
    }

    public List<MonthItem> getMonth() {
        return month;
    }

    @Override
    public String toString() {
        return
                "ClimateAveragesItem{" +
                        "month = '" + month + '\'' +
                        "}";
    }
}