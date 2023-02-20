package by.smirnov.chequeprintproject.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PathGetterTest {

    @ParameterizedTest
    @CsvSource(value = {
            "Cheques.txt=D:\\Java\\Anything\\ChequePrintProject2\\src\\main\\resources\\Cheques.txt",
            "chequefiles\\Cheques.txt=D:\\Java\\Anything\\ChequePrintProject2\\src\\main\\resources\\chequefiles\\Cheques.txt",
            "Cheques=D:\\Java\\Anything\\ChequePrintProject2\\src\\main\\resources\\Cheques.txt"
    },
            delimiter = '=')
    @DisplayName("getPath should return equal path")
    void checkGetPathShouldReturnEqualPath(String input, String expected) {
        String actual = PathGetter.getPath(input);

        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("getPath should return default path")
    void checkGetPathShouldReturnDefaultPath(String input) {
        String expected = "D:\\Java\\Anything\\ChequePrintProject2\\src\\main\\resources\\cheque.txt";

        String actual = PathGetter.getPath(input);

        assertThat(actual).isEqualTo(expected);
    }
}
