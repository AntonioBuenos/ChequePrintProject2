package by.smirnov.chequeprintproject.multithreading.server;

import by.smirnov.chequeprintproject.multithreading.entity.Request;
import by.smirnov.chequeprintproject.multithreading.entity.Response;

public interface Server {

    Response proceed(Request request);
}
