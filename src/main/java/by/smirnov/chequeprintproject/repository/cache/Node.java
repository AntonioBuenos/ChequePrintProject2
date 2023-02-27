package by.smirnov.chequeprintproject.repository.cache;

import by.smirnov.chequeprintproject.domain.DiscountCard;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Entity class of nodes to be used in LFU or LRU cache implementations such as NodeCache.class successors.
 * @author Anton Smirnov
 * @version 1.1
 */
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class Node {

    /** This field is an object cached or to be cached */
    private DiscountCard value;

    /** This field is a marker that implies priority of a cached object according to LFU (usage frequency) or LRU
     * (time of recent usage) cache algorithm realization */
    private Long count;

    /**
     * This method returns a Node's usage count
     * @return returns a Node's usage count
     */
    public Long getCount() {
        return count;
    }
}
