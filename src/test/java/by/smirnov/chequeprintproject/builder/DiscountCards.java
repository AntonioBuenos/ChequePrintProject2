package by.smirnov.chequeprintproject.builder;

import by.smirnov.chequeprintproject.domain.DiscountCard;

import static by.smirnov.chequeprintproject.testconstants.TestConstants.DEFAULT_TIMESTAMP;

public class DiscountCards {

    public static DiscountCard.DiscountCardBuilder aCard(){
        return DiscountCard.builder()
                .id(1L)
                .holderName("John Smith")
                .holderEmail("1001@supermail.com")
                .discountRate(3.0)
                .isActive(true)
                .creationDate(DEFAULT_TIMESTAMP);
    }
}
