package by.smirnov.chequeprintproject.repository.cache;

import by.smirnov.chequeprintproject.domain.DiscountCard;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Comparator;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class Node {

    private DiscountCard value;
    private Long count;

    public Long getCount() {
        return count;
    }
}
