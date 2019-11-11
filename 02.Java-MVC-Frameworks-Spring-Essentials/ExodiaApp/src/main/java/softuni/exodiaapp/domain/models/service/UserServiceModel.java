package softuni.exodiaapp.domain.models.service;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


public class UserServiceModel {

    private String id;
    private String username;
    private String password;
    private String email;
    public UserServiceModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NotNull(message = "can not be empty!")
    @NotEmpty
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotNull(message = "can not be empty!")
    @NotEmpty
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotNull(message = "can not be empty!")
    @NotEmpty
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
