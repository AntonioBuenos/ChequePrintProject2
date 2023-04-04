package by.smirnov.chequeprintproject.multithreading.server;

import by.smirnov.chequeprintproject.multithreading.entity.Request;
import by.smirnov.chequeprintproject.multithreading.entity.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static by.smirnov.chequeprintproject.multithreading.constants.Constants.REQUESTS_QTY;
import static by.smirnov.chequeprintproject.multithreading.constants.Constants.TEST_REQUEST_AMOUNT;
import static by.smirnov.chequeprintproject.multithreading.constants.MultithreadingConstants.INCREMENT_QTY;
import static org.assertj.core.api.Assertions.assertThat;

class ServerImplTest {

    Server server = new ServerImpl();

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 100, -100, 100500})
    @DisplayName("proceed shall return increased amount")
    void checkProceedShallReturnIncreasedAmount(int amount) {
        Request request = new Request(1, amount);
        int expectedAmount = amount + INCREMENT_QTY;
        Response expected = new Response(1, expectedAmount);

        Response actual = server.proceed(request);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("proceed shall return increased total sum")
    void checkProceedShallReturnIncreasedTotalSum() {
        int expectedSum = REQUESTS_QTY*(TEST_REQUEST_AMOUNT + INCREMENT_QTY);

        int actualSum = Stream
                .generate(() -> new Request(1, TEST_REQUEST_AMOUNT))
                .limit(REQUESTS_QTY)
                .parallel()
                .map(server::proceed)
                .mapToInt(Response::getAmount)
                .sum();

        assertThat(actualSum).isEqualTo(expectedSum);
    }
}
