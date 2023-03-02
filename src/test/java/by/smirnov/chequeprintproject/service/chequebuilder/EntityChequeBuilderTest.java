package by.smirnov.chequeprintproject.service.chequebuilder;

import by.smirnov.chequeprintproject.domain.Cashier;
import by.smirnov.chequeprintproject.domain.ChequeResponse;
import by.smirnov.chequeprintproject.domain.Position;
import by.smirnov.chequeprintproject.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static by.smirnov.chequeprintproject.builder.Products.aProduct;
import static by.smirnov.chequeprintproject.domain.Store.SHOP;
import static by.smirnov.chequeprintproject.service.chequebuilder.ChequeConstants.AD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EntityChequeBuilderTest {

    @Mock
    private ChequeCounter chequeCounter;

    @InjectMocks
    private EntityChequeBuilder entityChequeBuilder;

    private final Cashier cashier = new Cashier(1001L, SHOP);

    private static final Map<Product, Integer> products = new HashMap<>();

    @Test
    @DisplayName("BuildCheque should invoke CheckCounter")
    void checkBuildChequeShouldInvokeCheckCounter() {
        entityChequeBuilder.buildCheque(cashier);

        verify(chequeCounter, times(1)).getProducts();
        verify(chequeCounter, times(1)).getGrossChequeAmount();
        verify(chequeCounter, times(1)).getPromotionDiscountSum();
        verify(chequeCounter, times(1)).getCardDiscountSum();
        verify(chequeCounter, times(1)).getTaxableAmount();
        verify(chequeCounter, times(1)).getVatAmount();
        verify(chequeCounter, times(1)).getTotalAmount();
    }

    @Test
    @DisplayName("BuildCheque should return equal ChequeResponse")
    void checkBuildChequeShouldReturnEqualResponse() {
        products.put(aProduct().build(), 4);
        products.put(aProduct().productName("Vic Firth drumsticks 2BN").build(), 5);

        List<Position> productList = new ArrayList<>();
        productList.add(new Position(4, "Vic Firth drumsticks 2B", 14.0, 4 * 14.0));
        productList.add(new Position(5, "Vic Firth drumsticks 2BN", 14.0, 5 * 14.0));

        LocalDateTime dateTime = LocalDateTime.now();
        ChequeResponse expected = ChequeResponse.builder()
                .title("CASH RECEIPT")
                .storeName("DrumsticksStore#1")
                .address("Minsk, Herearound Str., 111-222")
                .phoneNumber("tel. +375(11)222-33-44")
                .cashierNumber(1001L)
                .date(dateTime.toLocalDate())
                .time(dateTime.toLocalTime())
                .positions(productList)
                .sum(126.0)
                .promoDiscount(0.0)
                .cardDiscount(6.3)
                .taxable(99.75)
                .vat(19.95)
                .total(119.7)
                .ad(AD)
                .build();

        doReturn(products).when(chequeCounter).getProducts();
        doReturn(126.0).when(chequeCounter).getGrossChequeAmount();
        doReturn(0.0).when(chequeCounter).getPromotionDiscountSum();
        doReturn(6.3).when(chequeCounter).getCardDiscountSum();
        doReturn(99.75).when(chequeCounter).getTaxableAmount();
        doReturn(19.95).when(chequeCounter).getVatAmount();
        doReturn(119.7).when(chequeCounter).getTotalAmount();

        ChequeResponse actual = entityChequeBuilder.buildCheque(cashier);

        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("BuildCheque throws NullPointerException")
    void checkBuildChequeThrowsNullPointerException(Cashier cashier) {
        assertThrows(NullPointerException.class, () -> entityChequeBuilder.buildCheque(cashier));
    }
}
