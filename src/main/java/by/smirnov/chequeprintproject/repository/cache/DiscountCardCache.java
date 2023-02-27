package by.smirnov.chequeprintproject.repository.cache;

import by.smirnov.chequeprintproject.domain.DiscountCard;

/**
 * Interface to be extended by LFU of LRU cache implementations for caching DiscountCard entities.
 * @author Anton Smirnov
 * @version 1.1
 */
public interface DiscountCardCache {

    /**
     * This method shall find the DiscountCard by its ID and shall modify Node's counter according to LFU or LRU realization
     * @param id - DiscountCard ID
     * @return returns a DiscountCard found
     */
    DiscountCard findById(Long id);

    /**
     * This method shall put an object to cache and modify its state according to LFU or LRU realization
     * @param object - DiscountCard to be cached
     * @return returns a DiscountCard cached
     */
    DiscountCard put(DiscountCard object);

    /**
     * This method shall remove the DiscountCard by its ID from the cache
     * @param id - DiscountCard ID
     */
    void delete(Long id);


}
