package by.smirnov.chequeprintproject.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscountCardRequest {

    @NotNull
    @Pattern(regexp = "[a-zA-Zа-яА-ЯёЁ]+")
    private String holderName;

    @NotNull
    @Email
    private String holderEmail;

    @NotNull
    @PositiveOrZero
    private Double discountRate;

}
