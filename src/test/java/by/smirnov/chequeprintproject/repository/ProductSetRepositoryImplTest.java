package by.smirnov.chequeprintproject.repository;

import by.smirnov.chequeprintproject.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static by.smirnov.chequeprintproject.builder.Products.aProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductSetRepositoryImplTest {

    private final ProductRepository repository = new ProductSetRepositoryImpl();

    @Test
    @DisplayName("findById should return equal Product")
    void checkFindByIdShouldReturnEqualProduct() {
        Product expected = aProduct().build();

        Product actual = repository.findById(1L);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("findById should return null")
    void checkFindByIdShouldReturnNull() {
        assertThat(repository.findById(1000L)).isNull();
    }
}
