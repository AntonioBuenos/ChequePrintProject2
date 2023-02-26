package by.smirnov.chequeprintproject.repository.cache;

import by.smirnov.chequeprintproject.domain.DiscountCard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import static by.smirnov.chequeprintproject.exceptionhandler.ExceptionConstants.LOG_MESSAGE_CACHE_NOT_NORMALIZED;

@Component
@Primary
@Slf4j
public class LFUCache implements DiscountCardCache {

    Map<Long, Node> map = new HashMap<>();
    private static final int MAX_ENTRIES = 3;

    @Override
    public DiscountCard findById(Long id) {
        Node node = map.get(id);
        if(node==null) return null;
        put(node.getValue());
        return node.getValue();
    }

    @Override
    public DiscountCard put(DiscountCard card) {
        Long key = card.getId();
        if (map.containsKey(key)) {
            Node node = new Node(card, map.get(key).getCount() + 1);
            delete(key);
            map.put(key, node);
        } else {
            normalizeCacheSize();
            map.put(key, new Node(card, 1L));
        }
        return card;
    }

    @Override
    public void delete(Long id) {
        map.remove(id);
    }

    private void normalizeCacheSize() {
        if (map.size() >= MAX_ENTRIES) {
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
}
