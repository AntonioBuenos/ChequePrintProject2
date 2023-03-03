package by.smirnov.chequeprintproject.multithreading.client;

import by.smirnov.chequeprintproject.multithreading.entity.Request;
import by.smirnov.chequeprintproject.multithreading.entity.Response;

import java.util.List;

public interface Client {

    List<Response> sendRequests(List<Request> requests);
}
