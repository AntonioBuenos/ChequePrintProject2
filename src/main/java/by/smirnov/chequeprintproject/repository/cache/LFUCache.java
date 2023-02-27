package by.smirnov.chequeprintproject.repository.cache;

import by.smirnov.chequeprintproject.domain.DiscountCard;

/**
 * Class performing LFU cache algorithm for caching DiscountCard entities based on this package
 * Node.class.
 * It implements DiscountCard put(DiscountCard card) method while other cache methods are performed by the parent class
 * NodeCache.
 * @author Anton Smirnov
 * @version 1.1
 */
public class LFUCache extends NodeCache implements DiscountCardCache {

    /**
     * This method puts a new or already cached object. The Node's counter of an object already cached
     * is modified according to LFU cache algorithm (by its frequency of use). And prior to caching the less frequently
     * used element is removed from cache as the cache reaches its limit.
     * @param card - DiscountCard to be cached
     * @return returns a DiscountCard cached
     */
    @Override
    public DiscountCard put(DiscountCard card) {
        Long key = card.getId();
        if (map.containsKey(key)) {
            Node node = new Node(card, map.get(key).getCount() + 1);
            map.compute(key, (k, v) -> v = node);
        } else {
            normalizeCacheSize();
            map.put(key, new Node(card, 1L));
        }
        return card;
    }
}
