package alfa.service;

import alfa.model.currency.CurrencyObject;

public interface AlfaCurrencyInterface {
    boolean isCurrencyAvailable(String symbol);
    CurrencyObject getCurrentCurrency(String symbol);
    CurrencyObject getYesterdayCurrency(String symbol, long timestamp);
}
