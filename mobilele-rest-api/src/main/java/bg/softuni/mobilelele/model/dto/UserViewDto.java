package bg.softuni.mobilelele.model.dto;

import bg.softuni.mobilelele.model.enums.UserRoleEnum;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public class UserViewDto {

    private String token;
    private String email;
    private String firstName;
    private String lastName;
    private List<String> authorities;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }
}
