package by.smirnov.chequeprintproject.multithreading.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import static by.smirnov.chequeprintproject.multithreading.constants.MultithreadingConstants.INCREMENT_QTY;

@UtilityClass
@Slf4j
public class Sleeper {

    public static void sleep() {
        try {
            Thread.sleep(Randomizer.get()/INCREMENT_QTY);
        } catch (InterruptedException e) {
            log.error("Interruption error occurred while thread time-sleeping: {}", e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
