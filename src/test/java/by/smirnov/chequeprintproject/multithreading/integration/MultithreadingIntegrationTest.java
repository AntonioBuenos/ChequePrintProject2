package by.smirnov.chequeprintproject.multithreading.integration;

import by.smirnov.chequeprintproject.multithreading.client.Client;
import by.smirnov.chequeprintproject.multithreading.entity.Request;
import by.smirnov.chequeprintproject.multithreading.entity.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static by.smirnov.chequeprintproject.multithreading.constants.Constants.REQUESTS_QTY;
import static by.smirnov.chequeprintproject.multithreading.constants.Constants.TEST_REQUEST_AMOUNT;
import static by.smirnov.chequeprintproject.multithreading.constants.MultithreadingConstants.INCREMENT_QTY;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MultithreadingIntegrationTest {

    @Autowired
    private Client client;

    private static AtomicInteger count = new AtomicInteger();

    private static List<Request> requests;

    @BeforeAll
    static void init(){
        requests = Stream
                .generate(() -> new Request(count.incrementAndGet(), TEST_REQUEST_AMOUNT))
                .limit(REQUESTS_QTY)
                .toList();
    }

    @Test
    @DisplayName("sendRequests should have entry and exit lists of equal size")
    void checkSendRequestsShouldHaveEntryAndExitListsOfEqualSize() {
        List<Response> responses = client.sendRequests(requests);

        assertThat(responses).hasSameSizeAs(requests);
    }

    @Test
    @DisplayName("sendRequests should return list with right increased total amount")
    void checkSendRequestsShouldReturnListWithRightIncreasedTotalAmount() {
        List<Response> responses = client.sendRequests(requests);
        int resultSum = responses.stream().mapToInt(Response::getAmount).sum();
        int expectedSum = REQUESTS_QTY * (TEST_REQUEST_AMOUNT + INCREMENT_QTY);

        assertThat(resultSum).isEqualTo(expectedSum);
    }
}
