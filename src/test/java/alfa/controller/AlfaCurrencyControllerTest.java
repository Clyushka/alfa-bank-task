package alfa.controller;

import alfa.model.currency.CurrencyObject;
import alfa.model.currency.Rate;
import alfa.model.giphy.Data;
import alfa.model.giphy.GiphyObject;
import alfa.service.AlfaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.MalformedURLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
public class AlfaCurrencyControllerTest {
    @Autowired
    AlfaController alfaCUT;

    @MockBean
    AlfaService alfaServiceMock;

    @Test
    public void checkCurrencyGrowToUSDNoSuchCurrencyTest() throws MalformedURLException {
        Mockito.when(alfaServiceMock.isCurrencyAvailable(Mockito.anyString())).thenReturn(false);
        ResponseEntity<String> expectedResponse = new ResponseEntity<>("", HttpStatus.NO_CONTENT);

        ResponseEntity<?> actualResponse = alfaCUT.checkCurrencyGrowToUSD("AUD");

        assertThat(expectedResponse.getStatusCode().toString(), is(actualResponse.getStatusCode().toString()));
    }

    @Test
    public void checkCurrencyGrowToUSDRichTest() throws MalformedURLException {
        CurrencyObject audTodayObj = new CurrencyObject(
                "Usage subject to terms: https://openexchangerates.org/terms",
                "https://openexchangerates.org/license",
                1654473599L,
                "USD",
                new Rate("AUD", 1.388785)
        );

        CurrencyObject audYesterdayObj = new CurrencyObject(
                "Usage subject to terms: https://openexchangerates.org/terms",
                "https://openexchangerates.org/license",
                1651708799L,
                "USD",
                new Rate("AUD", 1)
        );

        String gifType = "gif";
        String gifId = "richID";
        GiphyObject gifObj = new GiphyObject(new Data(gifType, gifId, "https://someURL/"));

        Mockito.when(alfaServiceMock.isCurrencyAvailable(Mockito.anyString())).thenReturn(true);
        Mockito.when(alfaServiceMock.getCurrentCurrency(Mockito.anyString()))
                .thenReturn(audTodayObj);
        Mockito.when(alfaServiceMock.getYesterdayCurrency(Mockito.anyString(), Mockito.anyLong()))
                .thenReturn(audYesterdayObj);
        Mockito.when(alfaServiceMock.getRandomGiphy(Mockito.anyString()))
                .thenReturn(gifObj);

        ResponseEntity<String> expectedResponse = new ResponseEntity<>(
                String.format("<img src=\"%s\" alt=\"%s\"/>",
                        "https://i.giphy.com/" + gifId + "." + gifType,
                        "rich"),
                HttpStatus.OK);

        ResponseEntity<?> actualResponse = alfaCUT.checkCurrencyGrowToUSD("AUD");

        assertThat(
                expectedResponse.getStatusCode().toString(),
                is(actualResponse.getStatusCode().toString()));
        assertThat(
                expectedResponse.getBody(),
                is(actualResponse.getBody().toString()));
    }

    @Test
    public void checkCurrencyGrowToUSDBrokeTest() throws MalformedURLException {
        CurrencyObject audTodayObj = new CurrencyObject(
                "Usage subject to terms: https://openexchangerates.org/terms",
                "https://openexchangerates.org/license",
                1654473599L,
                "USD",
                new Rate("AUD", 1.388785)
        );

        CurrencyObject audYesterdayObj = new CurrencyObject(
                "Usage subject to terms: https://openexchangerates.org/terms",
                "https://openexchangerates.org/license",
                1651708799L,
                "USD",
                new Rate("AUD", 1.5)
        );

        String gifType = "gif";
        String gifId = "richID";
        GiphyObject gifObj = new GiphyObject(new Data(gifType, gifId, "https://someURL/"));

        Mockito.when(alfaServiceMock.isCurrencyAvailable(Mockito.anyString())).thenReturn(true);
        Mockito.when(alfaServiceMock.getCurrentCurrency(Mockito.anyString()))
                .thenReturn(audTodayObj);
        Mockito.when(alfaServiceMock.getYesterdayCurrency(Mockito.anyString(), Mockito.anyLong()))
                .thenReturn(audYesterdayObj);
        Mockito.when(alfaServiceMock.getRandomGiphy(Mockito.anyString()))
                .thenReturn(gifObj);

        ResponseEntity<String> expectedResponse = new ResponseEntity<>(
                String.format("<img src=\"%s\" alt=\"%s\"/>",
                        "https://i.giphy.com/" + gifId + "." + gifType,
                        "broke"),
                HttpStatus.OK);

        ResponseEntity<?> actualResponse = alfaCUT.checkCurrencyGrowToUSD("AUD");

        assertThat(
                expectedResponse.getStatusCode().toString(),
                is(actualResponse.getStatusCode().toString()));
        assertThat(
                expectedResponse.getBody(),
                is(actualResponse.getBody().toString()));
    }

    @Test
    public void checkCurrencyGrowToUSDEqualCurrenciesTest() throws MalformedURLException {
        CurrencyObject audObj = new CurrencyObject(
                "Usage subject to terms: https://openexchangerates.org/terms",
                "https://openexchangerates.org/license",
                1654473599L,
                "USD",
                new Rate("AUD", 1.388785)
        );

        String gifType = "gif";
        String gifId = "richID";
        GiphyObject gifObj = new GiphyObject(new Data(gifType, gifId, "https://someURL/"));

        Mockito.when(alfaServiceMock.isCurrencyAvailable(Mockito.anyString())).thenReturn(true);
        Mockito.when(alfaServiceMock.getCurrentCurrency(Mockito.anyString()))
                .thenReturn(audObj);
        Mockito.when(alfaServiceMock.getYesterdayCurrency(Mockito.anyString(), Mockito.anyLong()))
                .thenReturn(audObj);
        Mockito.when(alfaServiceMock.getRandomGiphy(Mockito.anyString()))
                .thenReturn(gifObj);

        ResponseEntity<String> expectedResponse = new ResponseEntity<>(
                String.format("<img src=\"%s\" alt=\"%s\"/>",
                        "https://i.giphy.com/" + gifId + "." + gifType,
                        "broke"),
                HttpStatus.OK);

        ResponseEntity<?> actualResponse = alfaCUT.checkCurrencyGrowToUSD("AUD");

        assertThat(
                expectedResponse.getStatusCode().toString(),
                is(actualResponse.getStatusCode().toString()));
        assertThat(
                expectedResponse.getBody(),
                is(actualResponse.getBody().toString()));
    }
}
