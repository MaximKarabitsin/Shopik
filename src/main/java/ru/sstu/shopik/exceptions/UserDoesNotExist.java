package ru.sstu.shopik.exceptions;

public class UserDoesNotExist extends Exception {
    public UserDoesNotExist() {
    }

    public UserDoesNotExist(String message) {
        super(message);
    }

    public UserDoesNotExist(String message, Throwable cause) {
        super(message, cause);
    }

    public UserDoesNotExist(Throwable cause) {
        super(cause);
    }
}
