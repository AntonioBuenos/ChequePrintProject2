package by.smirnov.chequeprintproject.multithreading.utils;

import lombok.experimental.UtilityClass;

import java.util.concurrent.ThreadLocalRandom;

import static by.smirnov.chequeprintproject.multithreading.constants.MultithreadingConstants.SLEEP_TIME_MAX_MILLIS;
import static by.smirnov.chequeprintproject.multithreading.constants.MultithreadingConstants.SLEEP_TIME_MIN_MILLIS;

@UtilityClass
public class Randomizer {

    public static int get() {
        return ThreadLocalRandom.current().nextInt(SLEEP_TIME_MIN_MILLIS, SLEEP_TIME_MAX_MILLIS + 1);
    }
}
