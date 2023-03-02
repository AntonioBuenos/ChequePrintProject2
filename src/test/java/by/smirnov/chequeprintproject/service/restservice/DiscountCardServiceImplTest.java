package by.smirnov.chequeprintproject.service.restservice;

import by.smirnov.chequeprintproject.domain.DiscountCard;
import by.smirnov.chequeprintproject.repository.DiscountCardDBRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static by.smirnov.chequeprintproject.builder.DiscountCards.aCard;
import static by.smirnov.chequeprintproject.testconstants.TestConstants.TEST_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DiscountCardServiceImplTest {

    @Mock
    private DiscountCardDBRepository repository;

    @InjectMocks
    private DiscountCardServiceImpl service;

    @Test
    @DisplayName("FindById should return equal DiscountCard")
    void checkFindByIdShouldReturnEqualDiscountCard() {
        DiscountCard expected = aCard().build();
        when(repository.findById(TEST_ID)).thenReturn(Optional.of(expected));

        DiscountCard actual = service.findById(TEST_ID);

        assertThat(expected).isEqualTo(actual);
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("FindById should return null")
    void checkFindByIdShouldReturnNull(Long id) {
        when(repository.findById(id)).thenReturn(Optional.empty());

        final DiscountCard actual = service.findById(id);

        assertThat(actual).isNull();
    }
}
