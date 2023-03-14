package by.smirnov.chequeprintproject.repository.cache;

import by.smirnov.chequeprintproject.config.CacheConfiguration;
import by.smirnov.chequeprintproject.domain.DiscountCard;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class performing LRU cache algorithm for caching DiscountCard entities by using the functionality of LinkedHashMap.
 * @author Anton Smirnov
 * @version 1.1
 */
public class LRULinkedHashMapCache implements DiscountCardCache{

    /** Field cacheLimit is a max limit of a cache to be set in application.yml */
    static int cacheLimit = CacheConfiguration.getCacheLimit();

    /** Field map is a LinkedHashMap cache container, using LinkedHashMap constructor with the specified initial
     * capacity, load factor and ordering mode and setting a cache limit by overriding a removeEldestEntry() method */
    final Map<Long, DiscountCard> cache = new LinkedHashMap<>(16, 0.75f, true){
        @Override
        protected boolean removeEldestEntry(Map.Entry<Long, DiscountCard> eldest) {
            return size() > cacheLimit;
        }
    };

    /**
     * This method finds the DiscountCard by its ID
     * @param id - DiscountCard ID
     * @return returns a DiscountCard found
     */
    @Override
    public DiscountCard findById(Long id) {
        return cache.get(id);
    }

    /**
     * This method puts a new or already cached object. The less recently
     * used element is removed from cache as the cache reaches its limit according to the LRU algorithm as realized by
     * LinkedHashMap.
     * @param object - DiscountCard to be cached
     * @return returns a DiscountCard cached
     */
    @Override
    public DiscountCard put(DiscountCard object) {
        cache.put(object.getId(), object);
        return object;
    }

    /**
     * This method removes the DiscountCard by its ID from the cache
     * @param id - DiscountCard ID
     */
    @Override
    public void delete(Long id) {
        cache.remove(id);
    }
}
