package by.smirnov.chequeprintproject.service.handler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;

class InputHandlerTest {

    private final InputHandler inputHandler = new InputHandler();

    @Test
    @DisplayName("ProcessCheque should fit timeout")
    void checkProcessChequeShouldFitTimeout() {
        String[] args = {"java", "CheckRunner", "1-7", "2-5", "3-1", "4-1", "5-1", "6-1", "7-1", "card-1", "cashier-1001"};

        assertTimeout(Duration.ofMillis(200L), () -> inputHandler.processCheque(args));
    }

    @Test
    @DisplayName("ProcessCheque throws NumberFormatException")
    void checkProcessChequeThrowsNumberFormatException() {
        String[] args = {"java", "CheckRunner", "fgh-7", "2-5", "3-1", "4-1", "5-1", "6-1", "7-1", "card-1", "cashier-1001"};

        assertThrows(NumberFormatException.class, () -> inputHandler.processCheque(args));
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("ProcessCheque throws NullPointerException")
    void checkProcessChequeThrowsNullPointerException(String...args) {
        assertThrows(NullPointerException.class, () -> inputHandler.processCheque(args));
    }

    @ParameterizedTest
    @EmptySource
    @DisplayName("ProcessCheque throws ArrayIndexOutOfBoundsException")
    void checkProcessChequeThrowsArrayIndexOutOfBoundsException(String...args) {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> inputHandler.processCheque(args));
    }
}
