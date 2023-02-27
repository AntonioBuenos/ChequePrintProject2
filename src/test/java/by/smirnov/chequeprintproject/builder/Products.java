package by.smirnov.chequeprintproject.builder;

import by.smirnov.chequeprintproject.domain.Product;

import static by.smirnov.chequeprintproject.testconstants.TestConstants.DEFAULT_TIMESTAMP;

public class Products {
    public static Product.ProductBuilder aProduct(){
        return Product.builder()
                .id(1L)
                .productName("Vic Firth drumsticks 2B")
                .price(14.0)
                .isPromoted(false)
                .creationDate(DEFAULT_TIMESTAMP)
                .isDeleted(false);
    }
}
