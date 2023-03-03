package by.smirnov.chequeprintproject.multithreading.client;

import by.smirnov.chequeprintproject.multithreading.entity.Request;
import by.smirnov.chequeprintproject.multithreading.entity.Response;
import by.smirnov.chequeprintproject.multithreading.server.Server;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static by.smirnov.chequeprintproject.multithreading.constants.MultithreadingConstants.THREAD_POOL_SIZE;

@Component
@RequiredArgsConstructor
@Slf4j
public class ClientImpl implements Client {

    private final Server server;

    @Override
    public List<Response> sendRequests(List<Request> requests) {
        List<Response> responses = new CopyOnWriteArrayList<>();

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        requests.stream()
                .parallel()
                .forEach(request -> {
                    Future<Response> future = executorService.submit(() -> server.proceed(request));
                    responses.add(getFutureValue(future));
                });

        executorService.shutdown();

        return responses;
    }

    private Response getFutureValue(Future<Response> future) {
        Response response = null;
        try {
            response = future.get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error of getting value from Future: {}", e.getMessage());
            Thread.currentThread().interrupt();
        }
        return response;
    }
}
