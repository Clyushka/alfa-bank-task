package alfa.feign.gify;

import alfa.model.giphy.GiphyObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="giphy-api",url="${giphy-api.address}")
public interface GiphyClientService {
    //https://api.giphy.com/v1/gifs/random?api_key=cw0YSahSwjDPeQtMUFWyvHcRHNuGDXGw&tag=rich&rating=g
    @GetMapping(value = "/random",
            produces = "application/json", params = { "api_key", "tag", "rating" })
    GiphyObject getRandomGiphy(
            @RequestParam("api_key") String api_key,
            @RequestParam("tag") String tag,
            @RequestParam("rating") String rating
    );
}
