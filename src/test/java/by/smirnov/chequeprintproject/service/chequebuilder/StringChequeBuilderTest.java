package by.smirnov.chequeprintproject.service.chequebuilder;

import by.smirnov.chequeprintproject.domain.Cashier;
import by.smirnov.chequeprintproject.domain.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static by.smirnov.chequeprintproject.builder.Products.aProduct;
import static by.smirnov.chequeprintproject.domain.Store.SHOP;
import static by.smirnov.chequeprintproject.testconstants.TestConstants.CHEQUE_BEGINNING;
import static by.smirnov.chequeprintproject.testconstants.TestConstants.CHEQUE_END;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class StringChequeBuilderTest {

    private static final Map<Product, Integer> products = new HashMap<>();

    @Mock
    private ChequeCounter chequeCounter;

    @InjectMocks
    private StringChequeBuilder chequeBuilder;

    @BeforeAll
    static void init() {
        products.put(aProduct().build(), 4);
        products.put(aProduct().productName("Vic Firth drumsticks 2BN").build(), 5);
    }

    @Test
    @DisplayName("BuildCheque should contain cheque output parts")
    void checkBuildChequeShouldContainChequeOutputParts() {
        Cashier cashier = new Cashier(1001L, SHOP);

        doReturn(products).when(chequeCounter).getProducts();
        doReturn(126.0).when(chequeCounter).getGrossChequeAmount();
        doReturn(0.0).when(chequeCounter).getPromotionDiscountSum();
        doReturn(6.3).when(chequeCounter).getCardDiscountSum();
        doReturn(99.75).when(chequeCounter).getTaxableAmount();
        doReturn(19.95).when(chequeCounter).getVatAmount();
        doReturn(119.7).when(chequeCounter).getTotalAmount();

        assertThat(chequeBuilder.buildCheque(cashier).toString()).contains(CHEQUE_BEGINNING);
        assertThat(chequeBuilder.buildCheque(cashier).toString()).contains(CHEQUE_END);
    }
}
