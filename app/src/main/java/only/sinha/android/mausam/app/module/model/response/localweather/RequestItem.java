package only.sinha.android.mausam.app.module.model.response.localweather;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class RequestItem {

    @JsonProperty("query")
    private String query;

    @JsonProperty("type")
    private String type;

    public RequestItem() {
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return
                "RequestItem{" +
                        "query = '" + query + '\'' +
                        ",type = '" + type + '\'' +
                        "}";
    }
}