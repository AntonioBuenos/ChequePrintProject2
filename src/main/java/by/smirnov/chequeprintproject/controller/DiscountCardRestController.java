package by.smirnov.chequeprintproject.controller;

import by.smirnov.chequeprintproject.domain.DiscountCard;
import by.smirnov.chequeprintproject.dto.DiscountCardRequest;
import by.smirnov.chequeprintproject.dto.DiscountCardResponse;
import by.smirnov.chequeprintproject.dto.converter.DiscountCardConverter;
import by.smirnov.chequeprintproject.exceptionhandler.BadRequestException;
import by.smirnov.chequeprintproject.exceptionhandler.NoSuchEntityException;
import by.smirnov.chequeprintproject.exceptionhandler.ValidationErrorConverter;
import by.smirnov.chequeprintproject.service.restservice.DiscountCardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static by.smirnov.chequeprintproject.controller.ControllerConstants.ID;
import static by.smirnov.chequeprintproject.controller.ControllerConstants.MAPPING_ID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/card")
public class DiscountCardRestController {

    private final DiscountCardService service;
    private final DiscountCardConverter converter;

    @GetMapping
    public ResponseEntity<Map<String, List<DiscountCardResponse>>> index() {
        List<DiscountCardResponse> cards = service.findAll().stream()
                .map(converter::convert)
                .toList();
        return new ResponseEntity<>(Collections.singletonMap("discountCards", cards), HttpStatus.OK);
    }

    @GetMapping(MAPPING_ID)
    public ResponseEntity<DiscountCardResponse> show(@PathVariable(ID) long id) {

        DiscountCard card = service.findById(id);
        DiscountCardResponse response = converter.convert(card);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DiscountCardResponse> create(@RequestBody @Valid DiscountCardRequest request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new BadRequestException(ValidationErrorConverter.getErrors(bindingResult).toString());
        }

        DiscountCard created = service.create(converter.convert(request));
        DiscountCardResponse response = converter.convert(created);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping(MAPPING_ID)
    public ResponseEntity<DiscountCardResponse> update(@PathVariable(name = ID) Long id,
                                                       @RequestBody @Valid DiscountCardRequest request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new BadRequestException(ValidationErrorConverter.getErrors(bindingResult).toString());
        }

        DiscountCard changed = service.update(converter.convert(request, id));
        DiscountCardResponse response = converter.convert(changed);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(MAPPING_ID)
    public ResponseEntity<Map<String, Long>> delete(@PathVariable(ID) long id) {
        if (Objects.isNull(service.findById(id))) throw new NoSuchEntityException();
        service.delete(id);
        return new ResponseEntity<>(Map.of("Object by this ID successfully deleted", id), HttpStatus.OK);
    }
}
