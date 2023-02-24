package by.smirnov.chequeprintproject.repository;

import by.smirnov.chequeprintproject.domain.DiscountCard;

public interface DiscountCardCache {

    DiscountCard findById(Long id);

    DiscountCard create(DiscountCard object);

    DiscountCard update(DiscountCard toBeUpdated);

    void delete(Long id);


}
