package alfa.service;

import alfa.feign.currency.CurrencyClientService;
import alfa.feign.gify.GiphyClientService;
import alfa.model.currency.CurrencyObject;
import alfa.model.giphy.GiphyObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;

@Service
public class AlfaService implements AlfaCurrencyInterface, AlfaGiphyInterface {
    //openexchangerates
    @Autowired
    CurrencyClientService ccs;
    @Value("${currency-api.app_id}")
    private String currencyApiAppId;

    //giphy
    @Autowired
    GiphyClientService gcs;
    @Value("${giphy-api.api_key}")
    private String giphyApiAppId;
    @Value("${giphy-api.rating}")
    private String rating;

    //inner variables
    private LinkedHashMap<String, String> AVAILABLE_CURRENCIES;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public AlfaService() {
        //empty
    }

    @PostConstruct
    public void initAC() {
        AVAILABLE_CURRENCIES = ccs.getAvailableCurrencies();
    }

    public LinkedHashMap<String, String> getAvailableCurrencies() {
        return new LinkedHashMap<>(AVAILABLE_CURRENCIES);
    }

    @Override
    public boolean isCurrencyAvailable(String symbol) {
        if (AVAILABLE_CURRENCIES.containsKey(symbol)) {
            return true;
        }
        return false;
    }

    @Override
    public CurrencyObject getCurrentCurrency(String symbol) {
          return ccs.getLatestCurrency(currencyApiAppId, symbol);
    }

    @Override
    public CurrencyObject getYesterdayCurrency(String symbol, long timestamp) {
        return ccs.getHistoricalCurrency(
                currencyApiAppId,
                symbol,
                new Timestamp(timestamp * 1000).toLocalDateTime().minusDays(1).format(DATE_FORMATTER));
    }

    @Override
    public GiphyObject getRandomGiphy(String tag) {
        return gcs.getRandomGiphy(giphyApiAppId, tag, rating);
    }
}