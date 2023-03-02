package by.smirnov.chequeprintproject.service.handler;

import by.smirnov.chequeprintproject.printer.ChequePrinter;
import by.smirnov.chequeprintproject.printer.ConsoleChequePrinter;
import by.smirnov.chequeprintproject.printer.FileChequePrinter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ChequePrinterFactoryTest {

    ChequePrinterFactory chequePrinterFactory = new ChequePrinterFactory();

    @ParameterizedTest
    @EnumSource(ChequePrinterFactory.PrinterType.class)
    @DisplayName("CreatePrinter Should Return Proper CheckPrinter")
    void checkCreatePrinterShouldReturnProperCheckPrinter(ChequePrinterFactory.PrinterType type) {
        ChequePrinter chequePrinter = chequePrinterFactory.createPrinter(type);

        if(type == ChequePrinterFactory.PrinterType.FILE)
            assertThat(chequePrinter).isExactlyInstanceOf(FileChequePrinter.class);
        else if (type == ChequePrinterFactory.PrinterType.CONSOLE)
            assertThat(chequePrinter).isExactlyInstanceOf(ConsoleChequePrinter.class);
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("CreatePrinter Throws NullPointerException")
    void checkCreatePrinterThrowsNullPointerException(ChequePrinterFactory.PrinterType type) {
        assertThrows(NullPointerException.class, () -> chequePrinterFactory.createPrinter(type));
    }
}
