package by.smirnov.chequeprintproject.service.restservice;

import by.smirnov.chequeprintproject.domain.ChequeRequest;
import by.smirnov.chequeprintproject.domain.Product;
import by.smirnov.chequeprintproject.exceptionhandler.NoSuchEntityException;
import by.smirnov.chequeprintproject.repository.ProductDBRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static by.smirnov.chequeprintproject.builder.Products.aProduct;
import static by.smirnov.chequeprintproject.exceptionhandler.ExceptionConstants.CARD_NOT_FOUND_MESSAGE;
import static by.smirnov.chequeprintproject.exceptionhandler.ExceptionConstants.PRODUCT_NOT_FOUND_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductDBRepository repository;

    @Mock
    private DiscountCardService discountCardService;

    @InjectMocks
    private ProductServiceImpl service;

    private static Map<Long, Integer> products;

    private static final long ID = 1L;

    @BeforeAll
    static void init(){
        products = new HashMap<>();
        products.put(1L, 4);
        products.put(2L, 5);
    }

    @Nested
    @DisplayName("findById tests")
    class FindByIdTest{
        @Test
        @DisplayName("FindById should return equal Product")
        void checkFindByIdShouldReturnEqualProduct() {
            Product expected = mock(Product.class);
            when(repository.findById(ID)).thenReturn(Optional.of(expected));

            Product actual = service.findById(ID);

            assertThat(expected).isEqualTo(actual);
        }

        @ParameterizedTest
        @NullSource
        @DisplayName("FindById should return null")
        void checkFindByIdShouldReturnNull(Long id) {
            when(repository.findById(id)).thenReturn(Optional.empty());

            Product actual = service.findById(id);

            assertThat(actual).isNull();
        }
    }

    @Test
    @DisplayName("getCheque Throws NoSuchEntityException")
    void checkGetChequeThrowsNoSuchEntityException() {
        var request = new ChequeRequest(products, 1L, 1001L);
        doReturn(null).when(discountCardService).findById(1L);

        var exception = assertThrows(NoSuchEntityException.class, () -> service.getCheque(request));
        assertThat(exception.getMessage()).isEqualTo(CARD_NOT_FOUND_MESSAGE + 1L);
    }

    @Nested
    @DisplayName("convertCart tests")
    class ConvertCartTest{
        @Test
        @DisplayName("convertCart Should Return Equal Map")
        void checkConvertCartShouldReturnEqualMap() {
            Product product1 = aProduct().build();
            Product product2 = aProduct().id(2L).build();
            Map<Product, Integer> expected = Map.of(product1, 4, product2, 5);
            doReturn(Optional.of(product1)).when(repository).findById(1L);
            doReturn(Optional.of(product2)).when(repository).findById(2L);

            Map<Product, Integer> actual = service.convertCart(products);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("convertCart Throws NoSuchEntityException")
        void checkConvertCartThrowsNoSuchEntityException() {
            Product product1 = aProduct().build();
            doReturn(Optional.of(product1)).when(repository).findById(1L);
            doReturn(Optional.empty()).when(repository).findById(2L);

            var exception = assertThrows(NoSuchEntityException.class, () -> service.convertCart(products));
            assertThat(exception.getMessage()).isEqualTo(PRODUCT_NOT_FOUND_MESSAGE + 2L);
        }
    }

}
