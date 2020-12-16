package ru.sstu.shopik.forms;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class PasswordChangeForm {


    @NotBlank
    @Pattern(regexp = "[a-zA-Z0-9]{9,40}")
    private String currentPassword;

    @NotBlank
    @Pattern(regexp = "[a-zA-Z0-9]{9,40}")
    private String newPassword;

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
