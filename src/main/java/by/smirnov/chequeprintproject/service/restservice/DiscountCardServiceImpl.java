package by.smirnov.chequeprintproject.service.restservice;

import by.smirnov.chequeprintproject.domain.DiscountCard;
import by.smirnov.chequeprintproject.repository.DiscountCardDBRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountCardServiceImpl implements DiscountCardService{

    private final DiscountCardDBRepository repository;

    @Override
    public DiscountCard findById(Long id) {
        return repository
                .findById(id)
                .orElse(null);
    }

    @Override
    public List<DiscountCard> findAll() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public DiscountCard create(DiscountCard object) {
        return repository.save(object);
    }

    @Transactional
    @Override
    public DiscountCard update(DiscountCard toBeUpdated) {
        return repository.save(toBeUpdated);
    }

    @Transactional
    @Override
    public void delete(Long id){
        repository.deleteById(id);
    }
}
