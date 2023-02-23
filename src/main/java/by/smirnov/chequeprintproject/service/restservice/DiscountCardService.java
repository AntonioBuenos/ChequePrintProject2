package by.smirnov.chequeprintproject.service.restservice;

import by.smirnov.chequeprintproject.domain.DiscountCard;

import java.util.List;

public interface DiscountCardService {

    DiscountCard findById(Long id);

    List<DiscountCard> findAll();

    DiscountCard create(DiscountCard object);

    DiscountCard update(DiscountCard toBeUpdated);

    void hardDelete(Long id);

}
