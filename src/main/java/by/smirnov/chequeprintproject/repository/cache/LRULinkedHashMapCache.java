package by.smirnov.chequeprintproject.repository.cache;

import by.smirnov.chequeprintproject.domain.DiscountCard;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRULinkedHashMapCache implements DiscountCardCache{

    private static final int MAX_ENTRIES = 10;

    private static final Map<Long, DiscountCard> cache = new LinkedHashMap<>(16, 0.75f, true){
        @Override
        protected boolean removeEldestEntry(Map.Entry<Long, DiscountCard> eldest) {
            return size() > MAX_ENTRIES;
        }
    };

    @Override
    public DiscountCard findById(Long id) {
        return cache.get(id);
    }

    @Override
    public DiscountCard put(DiscountCard object) {
        cache.put(object.getId(), object);
        return object;
    }

    @Override
    public void delete(Long id) {
        cache.remove(id);
    }
}
