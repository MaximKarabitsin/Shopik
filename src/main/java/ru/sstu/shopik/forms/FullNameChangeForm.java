package ru.sstu.shopik.forms;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class FullNameChangeForm {
    @Pattern(regexp = "[a-zA-Zа-яА-Я]{0,20}")
    private String firstName;

    @Pattern(regexp = "[a-zA-Zа-яА-Я]{0,20}")
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
