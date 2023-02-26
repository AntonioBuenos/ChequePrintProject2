package by.smirnov.chequeprintproject.repository.cache;

import by.smirnov.chequeprintproject.domain.DiscountCard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import static by.smirnov.chequeprintproject.exceptionhandler.ExceptionConstants.LOG_MESSAGE_CACHE_NOT_NORMALIZED;

@Component
@Primary
@Slf4j
public class LRUCache extends NodeCache implements DiscountCardCache {

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
