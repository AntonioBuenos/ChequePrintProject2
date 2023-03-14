package by.smirnov.chequeprintproject.repository.cache;

import by.smirnov.chequeprintproject.domain.DiscountCard;

/**
 * Class performing LRU cache algorithm for caching DiscountCard entities based on this package
 * Node.class.
 * It implements DiscountCard put(DiscountCard card) method while other cache methods are performed by the parent class
 * NodeCache.
 * @author Anton Smirnov
 * @version 1.1
 */
public class LRUCache extends NodeCache implements DiscountCardCache {

    /**
     * This method puts a new or already cached object. The Node's counter of an object already cached
     * is modified according to LRU cache algorithm (most recently used objects). And prior to caching the less recently
     * used element is removed from cache as the cache reaches its limit.
     * @param card - DiscountCard to be cached
     * @return returns a DiscountCard cached
     */
    @Override
    public DiscountCard put(DiscountCard card) {
        Long key = card.getId();
        if (map.containsKey(key)) {
            Node node = new Node(card, getTime());
            map.compute(key, (k, v) -> v = node);
        } else {
            normalizeCacheSize();
            map.put(key, new Node(card, getTime()));
        }
        return card;
    }
}
