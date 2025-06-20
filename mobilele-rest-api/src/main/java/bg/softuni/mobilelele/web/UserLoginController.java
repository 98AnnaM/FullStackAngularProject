package bg.softuni.mobilelele.web;

import bg.softuni.mobilelele.model.dto.UserLoginDto;
import bg.softuni.mobilelele.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserLoginController {

    private final UserService userService;

    public UserLoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDto request) {
        return ResponseEntity.ok(userService.authenticate(request));
    }
}