package by.smirnov.chequeprintproject.exceptionhandler;

public interface ExceptionConstants {

    String NOT_FOUND_MESSAGE = "Wrong ID, entity not found";
    String PRODUCT_NOT_FOUND_MESSAGE = "Product not found, wrong ID: ";
    String CARD_NOT_FOUND_MESSAGE = "Discount card not found, wrong ID: ";
    String CASHIER_NOT_FOUND_MESSAGE = "Cashier not found, wrong ID: ";
    String ERROR_KEY = "Error Message";
    String ERROR = "Error";
    String LOG_MESSAGE_CACHE_NOT_NORMALIZED = "Smth went wrong: cache is full but has not been normalized, " +
            "although minimal usage object not found. Error message: {}";
}
