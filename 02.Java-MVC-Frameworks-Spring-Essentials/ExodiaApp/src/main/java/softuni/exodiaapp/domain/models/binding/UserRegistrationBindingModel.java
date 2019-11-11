package softuni.exodiaapp.domain.models.binding;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRegistrationBindingModel {
    private String username;
    private String password;
    private String confirmPassword;
    private String email;

    public UserRegistrationBindingModel() {
    }

    @NotNull(message = "can not be empty!")
    @Size(min = 1, message = "can not be empty")

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotNull(message = "can not be empty!")
    @Size(min = 1, message = "can not be empty")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotNull(message = "can not be empty!")
    @Size(min = 1, message = "can not be empty")
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @NotNull(message = "can not be empty!")
    @Size(min = 1, message = "can not be empty")

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
