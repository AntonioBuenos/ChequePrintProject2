package by.smirnov.chequeprintproject.multithreading.client;

import by.smirnov.chequeprintproject.multithreading.entity.Request;
import by.smirnov.chequeprintproject.multithreading.entity.Response;
import by.smirnov.chequeprintproject.multithreading.server.ServerImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static by.smirnov.chequeprintproject.multithreading.constants.Constants.TEST_REQUEST_AMOUNT;
import static by.smirnov.chequeprintproject.multithreading.constants.MultithreadingConstants.INCREMENT_QTY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ClientImplTest {

    @Mock
    private ServerImpl server;

    @InjectMocks
    private ClientImpl client;

    @Test
    void sendRequests() {
        int entriesQty = 10000;
        List<Request> requests = Stream.generate(() -> new Request(1, TEST_REQUEST_AMOUNT))
                .limit(entriesQty)
                .toList();
        doReturn(new Response(1, TEST_REQUEST_AMOUNT + INCREMENT_QTY));

        List<Response> responses = client.sendRequests(requests);

        verify(server, times(entriesQty)).proceed(any(Request.class));
        assertThat(responses).hasSize(entriesQty);
    }
}
