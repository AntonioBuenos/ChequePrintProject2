package by.smirnov.chequeprintproject.exceptionhandler;

public class BadRequestException extends RuntimeException {

    public BadRequestException () {
        super("Your request doesn't mach requirements");
    }

    public BadRequestException (String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "BadRequestException{}" + super.toString();
    }
}
