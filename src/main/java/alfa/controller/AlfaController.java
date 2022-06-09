package alfa.controller;

import alfa.model.currency.CurrencyObject;
import alfa.service.AlfaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.net.MalformedURLException;

@Controller
public class AlfaController {
    private final AlfaService alfaService;

    @Value("${giphy-api.happy_word}")
    private String happyWord;
    @Value("${giphy-api.sad_word}")
    private String sadWord;
    @Value("${giphy-api.gif_type}")
    private String gifType;
    @Value("${giphy-api.gif_source_address}")
    private String gifSourceAddress;
    private final String GIF_SOURCE_ADDRESS_FORMAT = "%s%s.%s";
    private final String GIF_HTML_RESULT_FORMAT = "<img src=\"%s\" alt=\"%s\"/>";

    @Autowired
    public AlfaController(AlfaService alfaService) {
        this.alfaService = alfaService;
    }

    @GetMapping(value = "/{symbols}")
    public ResponseEntity<?> checkCurrencyGrowToUSD(
            @PathVariable(name = "symbols") String symbols) throws MalformedURLException {

        /*** Работа с сервисом openexchangerates ***/

        //проверяем, возможно ли сравнить предложенную валюту с USD
        if (!alfaService.isCurrencyAvailable(symbols)) {
            return new ResponseEntity<>(
                    "",
                    HttpStatus.NO_CONTENT);
        }

        //Получаем текущий курс по валюте
        CurrencyObject todayCurrency = alfaService.getCurrentCurrency(symbols);

        //Заправшиваем вчерашний курс по валюте
        CurrencyObject yesterdayCurrency = alfaService.getYesterdayCurrency(
                symbols,
                todayCurrency.getTimestamp()
        );

        /*** Работа с сервисом giphy ***/
        String gifId;
        boolean isGrew = todayCurrency.getRates().getValue() > yesterdayCurrency.getRates().getValue();
        if (isGrew) {
            gifId = alfaService.getRandomGiphy(happyWord).getData().getId();
        } else {
            gifId = alfaService.getRandomGiphy(sadWord).getData().getId();
        }
        String gifURL = String.format(GIF_SOURCE_ADDRESS_FORMAT,
                gifSourceAddress, gifId, gifType);
        String gifHtml = String.format(GIF_HTML_RESULT_FORMAT, gifURL, (isGrew ? happyWord : sadWord));

        //на клиент отправляем HTML-тег img со ссылккой на источник с GIF из нужно подборки
        return new ResponseEntity<>(gifHtml, HttpStatus.OK);
    }
}
