package alfa.model.giphy;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Data {
    private String type;
    private String id;
    private String url;

    @JsonCreator
    public Data(
            @JsonProperty("type") String type,
            @JsonProperty("id") String id,
            @JsonProperty("url") String url
    ) {
        this.type = type;
        this.id = id;
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
