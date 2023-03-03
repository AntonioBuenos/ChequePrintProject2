package by.smirnov.chequeprintproject.multithreading.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Request {

    private int id;
    private int amount;
}
