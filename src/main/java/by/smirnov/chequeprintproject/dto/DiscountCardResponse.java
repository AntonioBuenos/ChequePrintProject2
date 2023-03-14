package by.smirnov.chequeprintproject.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class DiscountCardResponse {

    private Long id;

    private String holderName;

    private String holderEmail;

    private Double discountRate;

    private Boolean isActive;

    private Timestamp creationDate;

}
