package only.sinha.android.mausam.app.module.model.response.geocode;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class GeoCodeResponse{

	@JsonProperty("results")
	private List<ResultsItem> results;

	@JsonProperty("status")
	private String status;

	public void setResults(List<ResultsItem> results){
		this.results = results;
	}

	public List<ResultsItem> getResults(){
		return results;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"GeoCodeResponse{" + 
			"results = '" + results + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}