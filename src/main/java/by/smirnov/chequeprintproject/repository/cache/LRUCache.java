package by.smirnov.chequeprintproject.repository.cache;

import by.smirnov.chequeprintproject.domain.DiscountCard;

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
