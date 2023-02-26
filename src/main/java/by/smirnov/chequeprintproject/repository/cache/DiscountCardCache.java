package by.smirnov.chequeprintproject.repository.cache;

import by.smirnov.chequeprintproject.domain.DiscountCard;

public interface DiscountCardCache {

    DiscountCard findById(Long id);

    DiscountCard put(DiscountCard object);

    void delete(Long id);


}
