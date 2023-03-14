package by.smirnov.chequeprintproject.repository.cache;

import by.smirnov.chequeprintproject.domain.DiscountCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static by.smirnov.chequeprintproject.builder.DiscountCards.aCard;
import static org.assertj.core.api.Assertions.assertThat;

class LRUCacheTest {

    private LRUCache cache = new LRUCache();

    @BeforeEach
    void init(){
        LRUCache.cacheLimit = 3;
        cache.map.put(1L, new Node(aCard().id(1L).build(), 1L));
        cache.map.put(2L, new Node(aCard().id(2L).build(), 2L));
        cache.map.put(3L, new Node(aCard().id(3L).build(), 3L));
    }

    @ParameterizedTest
    @DisplayName("findById should return card with right id")
    @ValueSource(longs = {1L, 2L, 3L})
    void checkFindByIdShouldReturnCardWithRightId(Long id) {
        DiscountCard actual = cache.findById(id);
        assertThat(actual.getId()).isEqualTo(id);
    }

    @ParameterizedTest
    @DisplayName("findById should increase node count")
    @ValueSource(longs = {1L, 2L, 3L})
    void checkFindByIdShouldIncreaseNodeCount(Long id) {
        Long actual = cache.map.get(id).getCount();
        cache.findById(id);
        Long next = cache.map.get(id).getCount();
        assertThat(actual).isLessThan(next);
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
        assertThat(cache.map).hasSize(2);
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
        assertThat(cache.map).hasSize(3);
        assertThat(cache.map.get(1L)).isNull();
    }

    @Test
    @DisplayName("put should increase counter")
    void checkPutShouldIncreaseCounter() {
        DiscountCard oldCard = aCard().id(1L).build();
        DiscountCard actual = cache.put(oldCard);
        assertThat(actual).isEqualTo(oldCard);
        assertThat(cache.map).hasSize(3);
        Long actualCounter = cache.map.get(1L).getCount();
        cache.put(oldCard);
        Long nextCounter = cache.map.get(1L).getCount();
        assertThat(actualCounter).isLessThan(nextCounter);
    }
}
