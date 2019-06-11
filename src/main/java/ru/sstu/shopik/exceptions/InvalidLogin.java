package ru.sstu.shopik.exceptions;

public class InvalidLogin extends  Exception {
    public InvalidLogin() {
    }

    public InvalidLogin(String message) {
        super(message);
    }

    public InvalidLogin(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidLogin(Throwable cause) {
        super(cause);
    }
}
