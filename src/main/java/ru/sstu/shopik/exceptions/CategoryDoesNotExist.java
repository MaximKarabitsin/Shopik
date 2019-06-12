package ru.sstu.shopik.exceptions;

public class CategoryDoesNotExist extends Exception {
    public CategoryDoesNotExist() {
    }

    public CategoryDoesNotExist(String message) {
        super(message);
    }

    public CategoryDoesNotExist(String message, Throwable cause) {
        super(message, cause);
    }

    public CategoryDoesNotExist(Throwable cause) {
        super(cause);
    }
}
