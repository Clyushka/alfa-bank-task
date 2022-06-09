package alfa.model;

import alfa.model.currency.CurrencyObject;
import alfa.model.currency.Rate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CurrencyObjectTest {
    private CurrencyObject currencyObject = new CurrencyObject();

    @BeforeEach
    public void init() {
        currencyObject = new CurrencyObject();
    }

    @ParameterizedTest
    @MethodSource("jacksonSettingsCheckFactory")
    public void jacksonSettingsCheck(String jsonString, CurrencyObject expectedCurrencyObject)
            throws JsonProcessingException, JSONException {
        //act
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        currencyObject = mapper.readValue(
                jsonString,
                CurrencyObject.class);
        //assert
        assertTrue(currencyObject.equals(expectedCurrencyObject));
    }

    public static Stream<Arguments> jacksonSettingsCheckFactory() {
        return Stream.of(
                Arguments.of("{\n" +
                                "   \"disclaimer\": \"Usage subject to terms: https://openexchangerates.org/terms\",\n" +
                                "   \"license\": \"https://openexchangerates.org/license\",\n" +
                                "   \"timestamp\": 1654261200,\n" +
                                "   \"base\": \"USD\",\n" +
                                "   \"rates\": {\n" +
                                "     \"AUD\": 1.383143\n" +
                                "\t}\n" +
                                "}"
                        , new CurrencyObject(
                                "Usage subject to terms: https://openexchangerates.org/terms",
                                "https://openexchangerates.org/license",
                                1654261200,
                                "USD",
                                new Rate("AUD", 1.383143)
                        )),
                Arguments.of("{\n" +
                                "  \"disclaimer\": \"Usage subject to terms: https://openexchangerates.org/terms\",\n" +
                                "  \"license\": \"https://openexchangerates.org/license\",\n" +
                                "  \"timestamp\": 1654603189,\n" +
                                "  \"base\": \"USD\",\n" +
                                "  \"rates\": {\n" +
                                "    \"USD\": 1\n" +
                                "  }\n" +
                                "}"
                        , new CurrencyObject(
                                "Usage subject to terms: https://openexchangerates.org/terms",
                                "https://openexchangerates.org/license",
                                1654603189,
                                "USD",
                                new Rate("USD", 1)
                        ))
        );
    }
}
