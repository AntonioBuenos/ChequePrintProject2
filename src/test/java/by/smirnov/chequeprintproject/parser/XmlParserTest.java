package by.smirnov.chequeprintproject.parser;

import by.smirnov.chequeprintproject.builder.DiscountCards;
import by.smirnov.chequeprintproject.domain.DiscountCard;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static by.smirnov.chequeprintproject.builder.DiscountCards.aCard;
import static org.assertj.core.api.Assertions.assertThat;

class XmlParserTest {

    private XmlParser parser = new XmlParser();

    private String testXml = """
            <DiscountCardResponse>
            <id>5</id>
            <holderName>John</holderName>
            <holderEmail>john@john.com</holderEmail>
            <discountRate>5.0</discountRate>
            <isActive>true</isActive>
            <creationDate>null</creationDate>
            </DiscountCardResponse>
            """;

    private final DiscountCard testCard = aCard().creationDate(null).build();

    @Test
    void deserialize() throws NoSuchFieldException, InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {
        DiscountCard actual = parser.deserialize(testXml, DiscountCard.class);
        assertThat(actual).isEqualTo(testCard);
    }
}
