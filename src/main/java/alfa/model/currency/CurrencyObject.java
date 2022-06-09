package alfa.model.currency;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedHashMap;

public class CurrencyObject {
    private String disclaimer;
    private String license;
    private long timestamp;
    private String base;
    private Rate rates;

    public CurrencyObject() {
        //empty
    }

    @JsonCreator
    public CurrencyObject(
            @JsonProperty("disclaimer") String disclaimer,
            @JsonProperty("license") String license,
            @JsonProperty("timestamp") long timestamp,
            @JsonProperty("base") String base,
            @JsonProperty("rates") LinkedHashMap ratesMap
    ) {
        this.disclaimer = disclaimer;
        this.license = license;
        this.timestamp = timestamp;
        this.base = base;
        this.rates = new Rate(
                (String) ratesMap.keySet().toArray()[0],
                (ratesMap.values().toArray()[0].getClass().equals(Integer.class)
                        ? (int) ratesMap.values().toArray()[0] * 1.0
                        : (double) ratesMap.values().toArray()[0])
        );
    }

    public CurrencyObject(String disclaimer, String license, long timestamp,
                          String base, Rate rates) {
        this.disclaimer = disclaimer;
        this.license = license;
        this.timestamp = timestamp;
        this.base = base;
        this.rates = rates;
    }

    public String getDisclaimer() {
        return disclaimer;
    }

    public String getLicense() {
        return license;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getBase() {
        return base;
    }

    @JsonGetter("rates")
    public Rate getRates() {
        return rates;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public void setRates(Rate rates) {
        this.rates = rates;
    }

    @Override
    public boolean equals(Object object) {
        //musthave checks
        if (this == object) {
            return true;
        }
        if (object == null || !object.getClass().equals(CurrencyObject.class)) {
            return false;
        }

        //field check
        CurrencyObject currencyObject = (CurrencyObject) object;
        if (this.disclaimer.equals(currencyObject.disclaimer)
                && this.license.equals(currencyObject.license)
                && this.timestamp == currencyObject.timestamp
                && this.base.equals(currencyObject.base)
                && this.rates.equals(currencyObject.rates)) {
            return true;
        }
        return false;
    }
}
