package by.smirnov.chequeprintproject.domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ChequeRequest {

    @NotNull
    @NotEmpty
    private Map<Long, Integer> products;

    @Positive
    @NotNull
    private Long cardId;

    @Positive
    @NotNull
    private Long cashierId;
}
