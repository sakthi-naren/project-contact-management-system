package controllers;

public class Warning extends Exception {

    public Warning(String invalidInput) {
        super(invalidInput);
    }

}
