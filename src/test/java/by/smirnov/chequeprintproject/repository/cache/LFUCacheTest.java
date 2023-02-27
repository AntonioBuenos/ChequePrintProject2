package by.smirnov.chequeprintproject.repository.cache;

import by.smirnov.chequeprintproject.domain.DiscountCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static by.smirnov.chequeprintproject.builder.DiscountCards.aCard;
import static org.assertj.core.api.Assertions.assertThat;

class LFUCacheTest {

    private LFUCache cache = new LFUCache();

    @BeforeEach
    void init(){
        cache.map.put(1L, new Node(aCard().id(1L).build(), 1L));
        cache.map.put(2L, new Node(aCard().id(2L).build(), 2L));
        cache.map.put(3L, new Node(aCard().id(3L).build(), 3L));
    }

    @ParameterizedTest
    @DisplayName("findById should return card with right id")
    @ValueSource(longs = {1L, 2L, 3L})
    void checkFindByIdShouldReturnCardWithRightId(Long id) {
        System.out.println();
        DiscountCard actual = cache.findById(id);
        assertThat(actual.getId()).isEqualTo(id);
    }

    @ParameterizedTest
    @DisplayName("findById should increment node count")
    @CsvSource(value = {"1,2", "2,3", "3,4"},
    delimiter = ',')
    void checkFindByIdShouldIncrementNodeCount(Long id, Long count) {
        System.out.println();
        cache.findById(id);
        Node actual = cache.map.get(id);
        assertThat(actual.getCount()).isEqualTo(count);
    }

    @ParameterizedTest
    @DisplayName("findById should return null")
    @ValueSource(longs = {-1L, 0L, 100500L})
    void checkFindByIdShouldReturnNull(Long id) {
        System.out.println();
        DiscountCard actual = cache.findById(id);
        assertThat(actual).isNull();
    }

    @Test
    void delete() {
    }

    @Test
    void put() {
    }
}
