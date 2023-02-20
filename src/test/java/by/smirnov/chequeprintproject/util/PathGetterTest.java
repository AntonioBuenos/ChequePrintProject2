package by.smirnov.chequeprintproject.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class PathGetterTest {

    @ParameterizedTest
    @CsvSource(value = {
            "Cheques.txt=D:\\Java\\Anything\\ChequePrintProject2\\src\\main\\resources\\Cheques.txt",
            "chequefiles\\Cheques.txt=D:\\Java\\Anything\\ChequePrintProject2\\src\\main\\resources\\chequefiles\\Cheques.txt"
    },
            delimiter = '=')
    @DisplayName("getPath should return equal path")
    void checkGetPathShouldReturnEqualPath(String input, String expected) {
        String actual = PathGetter.getPath(input);

        assertEquals(expected, actual);
    }
}
