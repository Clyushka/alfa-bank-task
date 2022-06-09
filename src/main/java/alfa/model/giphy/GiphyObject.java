package alfa.model.giphy;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GiphyObject {
    private Data dataObject;

    @JsonCreator
    public GiphyObject(@JsonProperty("data") Data dataObject) {
        this.dataObject = dataObject;
    }

    public Data getData() {
        return dataObject;
    }

    public void setData(Data dataObject) {
        this.dataObject = dataObject;
    }

    //Modern versions (2.9.6) of Jackson libraries will ignore missing creator properties
    // by default. However, if the ObjectMapper configuration is set to:
    //
    //ObjectMapper mapper = new ObjectMapper();
    //mapper.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, true);
}
