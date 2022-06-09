package alfa.feign.currency;

import alfa.model.currency.CurrencyObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedHashMap;

@FeignClient(name="currency-api",url="${currency-api.address}")
public interface CurrencyClientService {
    @GetMapping(value = "/currencies.json", produces = "application/json")
    LinkedHashMap<String, String> getAvailableCurrencies();

    @GetMapping(value = "/latest.json",
            produces = "application/json", params = { "app_id", "symbols" })
    CurrencyObject getLatestCurrency(@RequestParam("app_id") String apiId, @RequestParam("symbols") String symbols);

    @GetMapping(value = "/historical/{date}.json", //yyyy-MM-dd
            produces = "application/json", params = { "app_id", "symbols" })
    CurrencyObject getHistoricalCurrency(@RequestParam("app_id") String apiId,
                                        @RequestParam("symbols") String symbols,
                                        @PathVariable("date") String date);
}
