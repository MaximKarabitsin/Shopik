package ru.sstu.shopik.forms;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class PasswordRecoveryForm {
    @NotBlank
    @Email
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
