package alfa.service;

import alfa.model.giphy.GiphyObject;

public interface AlfaGiphyInterface {
    GiphyObject getRandomGiphy(String tag);
}
