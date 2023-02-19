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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DiscountCardServiceImplTest {

    @Mock
    private DiscountCardDBRepository repository;

    @InjectMocks
    private DiscountCardServiceImpl service;

    private static final long ID = 1L;

    @Test
    @DisplayName("FindById should return equal DiscountCard")
    void checkFindByIdShouldReturnEqualDiscountCard() {
        final DiscountCard expected = mock(DiscountCard.class);
        when(repository.findById(ID)).thenReturn(Optional.of(expected));

        final DiscountCard actual = service.findById(ID);

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
