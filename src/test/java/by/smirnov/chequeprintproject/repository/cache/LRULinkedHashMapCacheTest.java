package by.smirnov.chequeprintproject.repository.cache;

import by.smirnov.chequeprintproject.domain.DiscountCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static by.smirnov.chequeprintproject.builder.DiscountCards.aCard;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LRULinkedHashMapCacheTest {

    private LRULinkedHashMapCache cache = new LRULinkedHashMapCache();

    @BeforeEach
    void init(){
        LRULinkedHashMapCache.cacheLimit = 3;
        cache.cache.put(1L, aCard().id(1L).build());
        cache.cache.put(2L, aCard().id(2L).build());
        cache.cache.put(3L, aCard().id(3L).build());
    }

    @ParameterizedTest
    @DisplayName("findById should return card with right id")
    @ValueSource(longs = {1L, 2L, 3L})
    void checkFindByIdShouldReturnCardWithRightId(Long id) {
        DiscountCard actual = cache.findById(id);
        assertThat(actual.getId()).isEqualTo(id);
    }

    @ParameterizedTest
    @DisplayName("findById should return null")
    @ValueSource(longs = {-1L, 0L, 100500L})
    void checkFindByIdShouldReturnNull(Long id) {
        DiscountCard actual = cache.findById(id);
        assertThat(actual).isNull();
    }

    @Test
    @DisplayName("delete decrements cache size")
    void checkDeleteDecrementsCacheSize() {
        cache.delete(1L);
        assertThat(cache.cache).hasSize(2);
    }

    @Test
    @DisplayName("delete removes right element")
    void checkDeleteRemovesRightElement() {
        cache.delete(1L);
        DiscountCard actual = cache.findById(1L);
        assertThat(actual).isNull();
    }

    @Test
    @DisplayName("put removes weakest card")
    void checkPutRemovesWeakestCard() {
        DiscountCard newCard = aCard().id(5L).build();
        DiscountCard actual = cache.put(newCard);
        assertThat(actual).isEqualTo(newCard);
        assertThat(cache.cache).hasSize(3);
        assertThat(cache.cache.get(1L)).isNull();
    }
}
