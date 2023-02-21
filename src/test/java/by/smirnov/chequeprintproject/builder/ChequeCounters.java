package by.smirnov.chequeprintproject.builder;

import by.smirnov.chequeprintproject.service.chequebuilder.ChequeCounter;

import java.util.Map;

public class ChequeCounters {

    public static ChequeCounter.ChequeCounterBuilder aChequeCounter(){
        return ChequeCounter.builder()
                .products(Map.of(
                        Products.aProduct().build(), 4,
                        Products.aProduct().id(2L).build(), 5
                ))
                .card(DiscountCards.aCard().build());
    }
}
