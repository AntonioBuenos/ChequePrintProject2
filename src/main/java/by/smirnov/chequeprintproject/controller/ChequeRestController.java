package by.smirnov.chequeprintproject.controller;

import by.smirnov.chequeprintproject.domain.ChequeRequest;
import by.smirnov.chequeprintproject.domain.ChequeResponse;
import by.smirnov.chequeprintproject.exceptionhandler.BadRequestException;
import by.smirnov.chequeprintproject.exceptionhandler.ValidationErrorConverter;
import by.smirnov.chequeprintproject.service.restservice.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cheques")
public class ChequeRestController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ChequeResponse> create(@RequestBody @Valid ChequeRequest request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new BadRequestException(ValidationErrorConverter.getErrors(bindingResult).toString());
        }

        ChequeResponse response = productService.getCheque(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
