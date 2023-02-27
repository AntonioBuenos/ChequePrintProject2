package by.smirnov.chequeprintproject.parser;

import by.smirnov.chequeprintproject.domain.DiscountCard;
import by.smirnov.chequeprintproject.dto.DiscountCardResponse;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class JsonParserTest {

    private JsonParser parser = new JsonParser();

    private String testJson = """
                    {
                    "id": 5,
                    "holderName": "Mescalero El Camino",
                    "holderEmail": "1005@supermail.com",
                    "discountRate": 3.0,
                    "isActive": true,
                    "creationDate": null
                    }
            """;

    private String testJson2 = "{\"id\":5,\"holderName\":\"Mescalero El Camino\",\"holderEmail\":\"1005@supermail.com\"," +
            "\"discountRate\":3.0,\"isActive\":true,\"creationDate\":null}";

    private DiscountCard testCard = DiscountCard.builder()
            .id(5L)
            .holderName("Mescalero El Camino")
            .holderEmail("1005@supermail.com")
            .discountRate(3.0)
            .isActive(true)
            .build();

    @Test
    void deserialize() throws NoSuchFieldException, InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {
        DiscountCard actual = parser.deserialize(testJson, DiscountCard.class);
        assertThat(actual).isEqualTo(testCard);
    }

    @Test
    void serialize() throws IllegalAccessException {
        String actual = parser.serialize(testCard);
        assertThat(actual).isEqualTo(testJson2);
    }
}
