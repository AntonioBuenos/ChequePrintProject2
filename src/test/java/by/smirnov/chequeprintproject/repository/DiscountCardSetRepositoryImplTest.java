package by.smirnov.chequeprintproject.repository;

import by.smirnov.chequeprintproject.domain.DiscountCard;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static by.smirnov.chequeprintproject.builder.DiscountCards.aCard;
import static org.assertj.core.api.Assertions.assertThat;

class DiscountCardSetRepositoryImplTest {

    private final DiscountCardRepository repository = new DiscountCardSetRepositoryImpl();

    @Test
    @DisplayName("findById should return equal DiscountCard")
    void checkFindByIdShouldReturnEqualDiscountCard() {
        DiscountCard expected = aCard()
                .id(1L)
                .holderEmail("1001@supermail.com")
                .holderName("John Smith")
                .discountRate(3.0)
                .creationDate(Timestamp.valueOf("2022-12-17 23:49:38"))
                .build();

        DiscountCard actual = repository.findById(1L);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("findById should return null")
    void checkFindByIdShouldReturnNull() {
        assertThat(repository.findById(1000L)).isNull();
    }
}
