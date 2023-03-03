package by.smirnov.chequeprintproject.multithreading.server;

import by.smirnov.chequeprintproject.multithreading.entity.Request;
import by.smirnov.chequeprintproject.multithreading.entity.Response;
import by.smirnov.chequeprintproject.multithreading.utils.Sleeper;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static by.smirnov.chequeprintproject.multithreading.constants.MultithreadingConstants.INCREMENT_QTY;

@Component
public class ServerImpl implements Server {

    private static final Lock lock = new ReentrantLock();
    private static int bufferAmount = 0;

    @Override
    public Response proceed(Request request) {
        int newAmount = doWork(request.getAmount());
        return new Response(request.getId(), newAmount);
    }

    private static int doWork(int amount) {
        lock.lock();
        try{
            for (int i = 0; i < INCREMENT_QTY; i++) {
                Sleeper.sleep();
                ++bufferAmount;
            }
            amount += bufferAmount;
            bufferAmount = 0;
        } finally {
            lock.unlock();
        }
        return amount;
    }
}
