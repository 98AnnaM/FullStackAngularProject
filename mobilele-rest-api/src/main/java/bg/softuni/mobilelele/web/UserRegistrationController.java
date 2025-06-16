package bg.softuni.mobilelele.web;

import bg.softuni.mobilelele.model.dto.UserRegisterDto;
import bg.softuni.mobilelele.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserRegistrationController {

    private final UserService userService;
    private final LocaleResolver localeResolver;

    public UserRegistrationController(UserService userService, LocaleResolver localeResolver) {
        this.userService = userService;
        this.localeResolver = localeResolver;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterDto userModel,
                                      HttpServletRequest request) {
        userService.registerUser(userModel, localeResolver.resolveLocale(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Registration successful"));
    }
}

