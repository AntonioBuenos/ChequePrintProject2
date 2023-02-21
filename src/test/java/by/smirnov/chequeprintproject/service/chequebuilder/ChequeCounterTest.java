package by.smirnov.chequeprintproject.service.chequebuilder;

import by.smirnov.chequeprintproject.domain.DiscountCard;
import by.smirnov.chequeprintproject.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.HashMap;
import java.util.Map;

import static by.smirnov.chequeprintproject.builder.DiscountCards.aCard;
import static by.smirnov.chequeprintproject.builder.Products.aProduct;
import static org.assertj.core.api.Assertions.assertThat;

class ChequeCounterTest {

    private ChequeCounter counter;

    private static Map<Product, Integer> products;

    private static DiscountCard card;

    @Test
    void getProducts() {
    }

    @Test
    void getCard() {
    }

    @ParameterizedTest
    @DisplayName("GetTotalAmount should return result")
    @CsvFileSource(resources = "/test_cheque_total_amount.csv", numLinesToSkip = 1, delimiter = ',')
    void checkGetTotalAmountShouldReturnResult
            (double price1, boolean promo1, int qnty1,
             double price2, boolean promo2, int qnty2,
             double cardRate, double result) {
        products = new HashMap<>();
        products.put(aProduct().price(price1).isPromoted(promo1).build(), qnty1);
        products.put(aProduct().id(2L).price(price2).isPromoted(promo2).build(), qnty2);
        card = aCard().discountRate(cardRate).build();
        counter = new ChequeCounter(products, card);

        double actual = counter.getTotalAmount();

        assertThat(actual).isEqualTo(result);
    }

    @ParameterizedTest
    @DisplayName("GetTotalAmount should return result")
    @CsvFileSource(resources = "/test_cheque_vat_amount.csv", numLinesToSkip = 1, delimiter = ',')
    void getVatAmount(double price1, boolean promo1, int qnty1,
                      double price2, boolean promo2, int qnty2,
                      double cardRate, double result) {
        products = new HashMap<>();
        products.put(aProduct().price(price1).isPromoted(promo1).build(), qnty1);
        products.put(aProduct().id(2L).price(price2).isPromoted(promo2).build(), qnty2);
        card = aCard().discountRate(cardRate).build();
        counter = new ChequeCounter(products, card);

        double actual = counter.getVatAmount();

        assertThat(actual).isEqualTo(result);
    }

    @Test
    void getPromotionDiscountSum() {
    }

    @Test
    void getTaxableAmount() {
    }

    @Test
    void getGrossChequeAmount() {
    }

    @Test
    void getCardDiscountSum() {
    }
}
