package bg.softuni.mobilelele.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class UserLoginDto {

    @NotEmpty(message = "User email should be provided.")
    @Email(message = "User email should be valid.")
    private String email;

    @NotEmpty
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
