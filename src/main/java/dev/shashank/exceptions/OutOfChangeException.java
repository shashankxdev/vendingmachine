package dev.shashank.exceptions;

public class OutOfChangeException extends RuntimeException {

    private String message;

    public OutOfChangeException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
