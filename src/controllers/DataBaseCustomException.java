package controllers;

public class DataBaseCustomException extends Throwable {

    private final String MESSAGE;

    public DataBaseCustomException(String message) {
        this.MESSAGE = "ERROR : " + message;
    }

    public String errorMessage () {
        return this.MESSAGE;
    }
    
}
