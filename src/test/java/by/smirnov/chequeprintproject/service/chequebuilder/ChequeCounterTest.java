package by.smirnov.chequeprintproject.service.chequebuilder;

import by.smirnov.chequeprintproject.domain.DiscountCard;
import by.smirnov.chequeprintproject.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.Map;

import static by.smirnov.chequeprintproject.builder.DiscountCards.aCard;
import static by.smirnov.chequeprintproject.builder.Products.aProduct;
import static org.assertj.core.api.Assertions.assertThat;

class ChequeCounterTest {

    private ChequeCounter counter;

    @Test
    @DisplayName("GetProducts should return equal map")
    void checkGetProductsShouldReturnEqualMap() {
        Map<Product, Integer> expected = Map.of(
                aProduct().build(), 1,
                aProduct().id(2L).build(), 1
        );
        counter = getCheckCounter();

        Map<Product, Integer> actual = counter.getProducts();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("GetCard should return equal DiscountCard")
    void checkGetCardShouldReturnEqualDiscountCard() {
        DiscountCard expected = aCard().build();
        counter = getCheckCounter();

        DiscountCard actual = counter.getCard();

        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @DisplayName("GetTotalAmount should return result")
    @CsvFileSource(resources = "/test_cheque_total_amount.csv", numLinesToSkip = 1, delimiter = ',')
    void checkGetTotalAmountShouldReturnResult
            (double fstGoodPrice, boolean fstIsDiscounted, int fstGoodQty,
             double sndGoodPrice, boolean sndIsDiscounted, int sndGoodQty,
             double cardDiscountRate, double result) {
        counter = getCheckCounter(
                fstGoodPrice, fstIsDiscounted, fstGoodQty,
                sndGoodPrice, sndIsDiscounted, sndGoodQty,
                cardDiscountRate);

        double actual = counter.getTotalAmount();

        assertThat(actual).isEqualTo(result);
    }

    @ParameterizedTest
    @DisplayName("getVatAmount should return result")
    @CsvFileSource(resources = "/test_cheque_vat_amount.csv", numLinesToSkip = 1, delimiter = ',')
    void checkGetVatAmountShouldReturnResult(double fstGoodPrice, boolean fstIsDiscounted, int fstGoodQty,
                                             double sndGoodPrice, boolean sndIsDiscounted, int sndGoodQty,
                                             double cardDiscountRate, double result) {
        counter = getCheckCounter(
                fstGoodPrice, fstIsDiscounted, fstGoodQty,
                sndGoodPrice, sndIsDiscounted, sndGoodQty,
                cardDiscountRate);

        double actual = counter.getVatAmount();

        assertThat(actual).isEqualTo(result);
    }

    @ParameterizedTest
    @DisplayName("getPromotionDiscountSum should return result")
    @CsvFileSource(resources = "/test_cheque_promotion_discount.csv", numLinesToSkip = 1, delimiter = ',')
    void checkGetPromotionDiscountSumShouldReturnResult(double fstGoodPrice, boolean fstIsDiscounted, int fstGoodQty,
                                                        double sndGoodPrice, boolean sndIsDiscounted, int sndGoodQty,
                                                        double cardDiscountRate, double result) {
        counter = getCheckCounter(
                fstGoodPrice, fstIsDiscounted, fstGoodQty,
                sndGoodPrice, sndIsDiscounted, sndGoodQty,
                cardDiscountRate);

        double actual = counter.getPromotionDiscountSum();

        assertThat(actual).isEqualTo(result);
    }

    @ParameterizedTest
    @DisplayName("getTaxableAmount should return result")
    @CsvFileSource(resources = "/test_cheque_taxable_amount.csv", numLinesToSkip = 1, delimiter = ',')
    void getTaxableAmountShouldReturnResult(double fstGoodPrice, boolean fstIsDiscounted, int fstGoodQty,
                                            double sndGoodPrice, boolean sndIsDiscounted, int sndGoodQty,
                                            double cardDiscountRate, double result) {
        counter = getCheckCounter(
                fstGoodPrice, fstIsDiscounted, fstGoodQty,
                sndGoodPrice, sndIsDiscounted, sndGoodQty,
                cardDiscountRate);

        double actual = counter.getTaxableAmount();

        assertThat(actual).isEqualTo(result);
    }

    @ParameterizedTest
    @DisplayName("getGrossChequeAmount should return result")
    @CsvFileSource(resources = "/test_cheque_gross_amount.csv", numLinesToSkip = 1, delimiter = ',')
    void getGrossChequeAmountShouldReturnResult(double fstGoodPrice, boolean fstIsDiscounted, int fstGoodQty,
                                                double sndGoodPrice, boolean sndIsDiscounted, int sndGoodQty,
                                                double cardDiscountRate, double result) {
        counter = getCheckCounter(
                fstGoodPrice, fstIsDiscounted, fstGoodQty,
                sndGoodPrice, sndIsDiscounted, sndGoodQty,
                cardDiscountRate);

        double actual = counter.getGrossChequeAmount();

        assertThat(actual).isEqualTo(result);
    }

    @ParameterizedTest
    @DisplayName("getCardDiscountSum should return result")
    @CsvFileSource(resources = "/test_cheque_card_discount.csv", numLinesToSkip = 1, delimiter = ',')
    void getCardDiscountSumShouldReturnResult(double fstGoodPrice, boolean fstIsDiscounted, int fstGoodQty,
                                              double sndGoodPrice, boolean sndIsDiscounted, int sndGoodQty,
                                              double cardDiscountRate, double result) {
        counter = getCheckCounter(
                fstGoodPrice, fstIsDiscounted, fstGoodQty,
                sndGoodPrice, sndIsDiscounted, sndGoodQty,
                cardDiscountRate);

        double actual = counter.getCardDiscountSum();

        assertThat(actual).isEqualTo(result);
    }

    private ChequeCounter getCheckCounter(double fstGoodPrice, boolean fstIsDiscounted, int fstGoodQty,
                                          double sndGoodPrice, boolean sndIsDiscounted, int sndGoodQty,
                                          double cardDiscountRate) {
        Map<Product, Integer> products = Map.of(
                aProduct()
                        .price(fstGoodPrice)
                        .isPromoted(fstIsDiscounted).build(),
                fstGoodQty,
                aProduct()
                        .id(2L)
                        .price(sndGoodPrice)
                        .isPromoted(sndIsDiscounted).build(),
                sndGoodQty
        );
        DiscountCard card = aCard().discountRate(cardDiscountRate).build();
        return new ChequeCounter(products, card);
    }

    private ChequeCounter getCheckCounter() {
        Map<Product, Integer> products = Map.of(
        aProduct().build(), 1,
        aProduct().id(2L).build(), 1
        );
        DiscountCard card = aCard().build();
        return new ChequeCounter(products, card);
    }
}
