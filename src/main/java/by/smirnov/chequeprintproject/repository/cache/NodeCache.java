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

@Slf4j
public abstract class NodeCache {

    Map<Long, Node> map = new HashMap<>();
    private static final int CACHE_LIMIT = CacheConfiguration.getCacheLimit();

    public DiscountCard findById(Long id) {
        Node node = map.get(id);
        if(node==null) return null;
        put(node.getValue());
        System.out.println(map.toString());
        return node.getValue();
    }

    public abstract DiscountCard put(DiscountCard card);

    public void delete(Long id) {
        map.remove(id);
    }

    void normalizeCacheSize() {
        if (map.size() >= CACHE_LIMIT) {
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
