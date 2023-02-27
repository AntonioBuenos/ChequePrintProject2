package by.smirnov.chequeprintproject.repository.cache;

import by.smirnov.chequeprintproject.config.CacheConfiguration;
import by.smirnov.chequeprintproject.domain.DiscountCard;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import static by.smirnov.chequeprintproject.exceptionhandler.ExceptionConstants.LOG_MESSAGE_CACHE_NOT_NORMALIZED;

/**
 * Abstract class to be extended by LFU of LRU cache classes for caching DiscountCard entities based on this package
 * Node.class.
 * The only method to be implemented is DiscountCard put(DiscountCard card).
 * @author Anton Smirnov
 * @version 1.1
 */
@Slf4j
public abstract class NodeCache {

    /** Field map is a cache container */
    Map<Long, Node> map = new HashMap<>();

    /** Field cacheLimit is a max limit of a cache to be set in application.yml */
    static int cacheLimit = CacheConfiguration.getCacheLimit();

    /**
     * This method finds the DiscountCard by its ID and shall modify Node's counter according to LFU or LRU realization
     * @param id - DiscountCard ID
     * @return returns a DiscountCard found
     */
    public DiscountCard findById(Long id) {
        Node node = map.get(id);
        if(node==null) return null;
        put(node.getValue());
        return node.getValue();
    }

    /**
     * This method shall be implemented by LFU or LRU class successors. The Node's counter of an object already cached
     * shall be modified according to LFU or LRU realization
     * @param card - DiscountCard to be cached
     * @return returns a DiscountCard cached
     */
    public abstract DiscountCard put(DiscountCard card);

    /**
     * This method removes the DiscountCard by its ID from the cache
     * @param id - DiscountCard ID
     */
    public void delete(Long id) {
        map.remove(id);
    }

    void normalizeCacheSize() {
        if (map.size() >= cacheLimit) {
            try{
                Long rarelyUsedId = map.values().stream().
                        min(Comparator.comparing(Node::getCount))
                        .orElseThrow(NullPointerException::new)
                        .getValue()
                        .getId();
                map.remove(rarelyUsedId);
            }catch (NullPointerException e){
                log.error(LOG_MESSAGE_CACHE_NOT_NORMALIZED, e.getMessage());
            }
        }
    }

    long getTime(){
        return Timestamp.valueOf(LocalDateTime.now()).getTime();
    }
}
