package ru.sstu.shopik.exceptions;

public class ProductDoesNotExist extends Exception {
    public ProductDoesNotExist() {
    }

    public ProductDoesNotExist(String message) {
        super(message);
    }

    public ProductDoesNotExist(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductDoesNotExist(Throwable cause) {
        super(cause);
    }
}
