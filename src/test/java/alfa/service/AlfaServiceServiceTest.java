package alfa.service;

import alfa.feign.currency.CurrencyClientService;
import alfa.feign.gify.GiphyClientService;
import alfa.model.currency.CurrencyObject;
import alfa.model.currency.Rate;
import alfa.model.giphy.Data;
import alfa.model.giphy.GiphyObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
public class AlfaServiceServiceTest {
    @Autowired
    private AlfaService alfaSUT;

    @MockBean
    private CurrencyClientService currencyServiceMock;
    @MockBean
    private GiphyClientService giphyServiceMock;

    //for currencyServiceMock
    private static LinkedHashMap<String, String> currenciesForMock = new LinkedHashMap<>(Map.of(
            "AED", "United Arab Emirates Dirham",
            "AFN", "Afghan Afghani",
            "ALL", "Albanian Lek",
            "AMD", "Armenian Dram",
            "ANG", "Netherlands Antillean Guilder",
            "AOA", "Angolan Kwanza",
            "ARS", "Argentine Peso",
            "AUD", "Australian Dollar",
            "AWG", "Aruban Florin",
            "AZN", "Azerbaijani Manat"
    ));

    @BeforeEach
    public void initAC() {
        Mockito.when(currencyServiceMock.getAvailableCurrencies()).thenReturn(currenciesForMock);
        alfaSUT.initAC();
    }

    @Test
    public void methodsCallingTest() {
        assertThat(
                alfaSUT.getAvailableCurrencies().size(),
                is(currenciesForMock.size())
        );
    }

    @ParameterizedTest
    @MethodSource("availableCurrencySymbolFactory")
    public void isCurrencyAvailableTest(String symbol, boolean expected) {
        assertThat(
                alfaSUT.isCurrencyAvailable(symbol),
                is(expected)
        );
    }

    @Test
    public void getCurrentCurrencyTest(){
        CurrencyObject audObj = new CurrencyObject(
                "Usage subject to terms: https://openexchangerates.org/terms",
                "https://openexchangerates.org/license",
                1654473599L,
                "USD",
                new Rate("AUD", 1.388785)
        );

        Mockito.when(currencyServiceMock.getLatestCurrency(Mockito.anyString(), eq("AUD")))
                .thenReturn(audObj);

        assertThat(alfaSUT.getCurrentCurrency("AUD"), is(audObj));
    }

    @Test
    public void getYesterdayCurrencyTest() {
        long timestamp = 1654473599L;
        CurrencyObject audObj = new CurrencyObject(
                "Usage subject to terms: https://openexchangerates.org/terms",
                "https://openexchangerates.org/license",
                timestamp,
                "USD",
                new Rate("AUD", 1.388785)
        );

        Mockito.when(currencyServiceMock.getHistoricalCurrency(
                        Mockito.anyString(),
                        eq("AUD"),
                        Mockito.anyString()))
                .thenReturn(audObj);

        assertThat(alfaSUT.getYesterdayCurrency("AUD", timestamp), is(audObj));
    }

    @ParameterizedTest
    @MethodSource("randomGiphyByTagFactory")
    public void getRandomGiphyTest(String tag, GiphyObject expectedGiphy) {
        Mockito.when(giphyServiceMock.getRandomGiphy(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(expectedGiphy);
        assertThat(alfaSUT.getRandomGiphy(tag), is(expectedGiphy));
    }

    //for isCurrencyAvailableTest
    public static Stream<Arguments> availableCurrencySymbolFactory() {
        return Stream.of(
                Arguments.of("AED", true),
                Arguments.of("AFN", true),
                Arguments.of("ALL", true),
                Arguments.of("AMD", true),
                Arguments.of("ANG", true),
                Arguments.of("AOA", true),
                Arguments.of("ARS", true),
                Arguments.of("AUD", true),
                Arguments.of("AWG", true),
                Arguments.of("AZN", true),
                Arguments.of("BAM", false),
                Arguments.of("BBD", false),
                Arguments.of("BDT", false),
                Arguments.of("BGN", false),
                Arguments.of("BHD", false),
                Arguments.of("BIF", false),
                Arguments.of("BMD", false),
                Arguments.of("BND", false),
                Arguments.of("BOB", false),
                Arguments.of("BRL", false),
                Arguments.of("BSD", false),
                Arguments.of("BTC", false)
        );
    }

    //for getRandomGiphyTest
    public static Stream<Arguments> randomGiphyByTagFactory() {
        return Stream.of(
                Arguments.of(
                        "rich",
                        new GiphyObject(new Data("gif", "richID", "https://someURL/"))),
                Arguments.of(
                        "broke",
                        new GiphyObject(new Data("gif", "brokeID", "https://someURL/")))
        );
    }
}
