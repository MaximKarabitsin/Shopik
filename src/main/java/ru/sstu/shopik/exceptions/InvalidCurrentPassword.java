package ru.sstu.shopik.exceptions;

public class InvalidCurrentPassword extends  Exception {

    public InvalidCurrentPassword() {
    }

    public InvalidCurrentPassword(String message) {
        super(message);
    }

    public InvalidCurrentPassword(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCurrentPassword(Throwable cause) {
        super(cause);
    }
}
