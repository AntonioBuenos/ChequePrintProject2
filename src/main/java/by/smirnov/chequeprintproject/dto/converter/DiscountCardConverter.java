package by.smirnov.chequeprintproject.dto.converter;

import by.smirnov.chequeprintproject.domain.DiscountCard;
import by.smirnov.chequeprintproject.dto.DiscountCardRequest;
import by.smirnov.chequeprintproject.dto.DiscountCardResponse;
import by.smirnov.chequeprintproject.service.restservice.DiscountCardService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class DiscountCardConverter {

    private final ModelMapper modelMapper;
    private final DiscountCardService service;

    public DiscountCard convert(DiscountCardRequest request){
        DiscountCard created = modelMapper.map(request, DiscountCard.class);
        created.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
        created.setIsActive(true);
        return created;
    }

    public DiscountCard convert(DiscountCardRequest request, Long id){
        DiscountCard old = service.findById(id);
        old.setHolderName(request.getHolderName());
        old.setHolderEmail(request.getHolderEmail());
        old.setDiscountRate(request.getDiscountRate());
        return old;
    }

    public DiscountCardResponse convert(DiscountCard genre){
        return modelMapper.map(genre, DiscountCardResponse.class);
    }
}
