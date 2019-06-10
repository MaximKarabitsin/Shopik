package ru.sstu.shopik.forms;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class UserRegistrationForm {

    @NotBlank
    @Pattern(regexp = "[a-zA-Z0-9]{1,15}")
    private String login;

    @NotBlank
    @Email
    private String email;

    @Pattern(regexp = "[a-zA-Zа-яА-Я]{0,20}")
    private String firstName;

    @Pattern(regexp = "[a-zA-Zа-яА-Я]{0,20}")
    private String lastName;

    @NotBlank
    @Pattern(regexp = "[a-zA-Z0-9]{9,40}")
    private String password;

    @NotBlank
    private String role;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
