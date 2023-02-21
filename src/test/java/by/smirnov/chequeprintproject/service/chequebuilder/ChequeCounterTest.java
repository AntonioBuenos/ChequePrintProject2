package by.smirnov.chequeprintproject.service.chequebuilder;

import by.smirnov.chequeprintproject.builder.DiscountCards;
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

    @Test
    @DisplayName("GetProducts should return equal map")
    void checkGetProductsShouldReturnEqualMap() {
        Map<Product, Integer> expected = Map.of(aProduct().build(), 1, aProduct().id(2L).build(), 1);
        counter = getCheckCounter();

        Map<Product, Integer> actual = counter.getProducts();

        assertThat(actual).isEqualTo(expected);
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
        counter = getCheckCounter(price1, promo1, qnty1, price2, promo2, qnty2, cardRate);

        double actual = counter.getTotalAmount();

        assertThat(actual).isEqualTo(result);
    }

    @ParameterizedTest
    @DisplayName("getVatAmount should return result")
    @CsvFileSource(resources = "/test_cheque_vat_amount.csv", numLinesToSkip = 1, delimiter = ',')
    void checkGetVatAmountShouldReturnResult(double price1, boolean promo1, int qnty1,
                                             double price2, boolean promo2, int qnty2,
                                             double cardRate, double result) {
        counter = getCheckCounter(price1, promo1, qnty1, price2, promo2, qnty2, cardRate);

        double actual = counter.getVatAmount();

        assertThat(actual).isEqualTo(result);
    }

    @ParameterizedTest
    @DisplayName("getPromotionDiscountSum should return result")
    @CsvFileSource(resources = "/test_cheque_promotion_discount.csv", numLinesToSkip = 1, delimiter = ',')
    void checkGetPromotionDiscountSumShouldReturnResult(double price1, boolean promo1, int qnty1,
                                                        double price2, boolean promo2, int qnty2,
                                                        double cardRate, double result) {
        counter = getCheckCounter(price1, promo1, qnty1, price2, promo2, qnty2, cardRate);

        double actual = counter.getPromotionDiscountSum();

        assertThat(actual).isEqualTo(result);
    }

    @ParameterizedTest
    @DisplayName("getTaxableAmount should return result")
    @CsvFileSource(resources = "/test_cheque_taxable_amount.csv", numLinesToSkip = 1, delimiter = ',')
    void getTaxableAmountShouldReturnResult(double price1, boolean promo1, int qnty1,
                                            double price2, boolean promo2, int qnty2,
                                            double cardRate, double result) {
        counter = getCheckCounter(price1, promo1, qnty1, price2, promo2, qnty2, cardRate);

        double actual = counter.getTaxableAmount();

        assertThat(actual).isEqualTo(result);
    }

    @ParameterizedTest
    @DisplayName("getGrossChequeAmount should return result")
    @CsvFileSource(resources = "/test_cheque_gross_amount.csv", numLinesToSkip = 1, delimiter = ',')
    void getGrossChequeAmountShouldReturnResult(double price1, boolean promo1, int qnty1,
                                                double price2, boolean promo2, int qnty2,
                                                double cardRate, double result) {
        counter = getCheckCounter(price1, promo1, qnty1, price2, promo2, qnty2, cardRate);

        double actual = counter.getGrossChequeAmount();

        assertThat(actual).isEqualTo(result);
    }

    @ParameterizedTest
    @DisplayName("getCardDiscountSum should return result")
    @CsvFileSource(resources = "/test_cheque_card_discount.csv", numLinesToSkip = 1, delimiter = ',')
    void getCardDiscountSumShouldReturnResult(double price1, boolean promo1, int qnty1,
                                              double price2, boolean promo2, int qnty2,
                                              double cardRate, double result) {
        counter = getCheckCounter(price1, promo1, qnty1, price2, promo2, qnty2, cardRate);

        double actual = counter.getCardDiscountSum();

        assertThat(actual).isEqualTo(result);
    }

    private ChequeCounter getCheckCounter(double price1, boolean promo1, int qnty1,
                                          double price2, boolean promo2, int qnty2,
                                          double cardRate) {
        Map<Product, Integer> products = new HashMap<>();
        products.put(aProduct().price(price1).isPromoted(promo1).build(), qnty1);
        products.put(aProduct().id(2L).price(price2).isPromoted(promo2).build(), qnty2);
        DiscountCard card = aCard().discountRate(cardRate).build();
        return new ChequeCounter(products, card);
    }

    private ChequeCounter getCheckCounter() {
        Map<Product, Integer> products = new HashMap<>();
        products.put(aProduct().build(), 1);
        products.put(aProduct().id(2L).build(), 1);
        DiscountCard card = aCard().build();
        return new ChequeCounter(products, card);
    }
}
