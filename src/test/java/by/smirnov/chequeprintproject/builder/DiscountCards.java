package by.smirnov.chequeprintproject.builder;

import by.smirnov.chequeprintproject.domain.DiscountCard;

import static by.smirnov.chequeprintproject.testconstants.TestConstants.DEFAULT_TIMESTAMP;

public class DiscountCards {

    public static DiscountCard.DiscountCardBuilder aCard(){
        return DiscountCard.builder()
                .id(5L)
                .holderName("John")
                .holderEmail("john@john.com")
                .discountRate(5.0)
                .isActive(true)
                .creationDate(DEFAULT_TIMESTAMP);
    }
}
