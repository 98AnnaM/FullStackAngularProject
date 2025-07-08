package bg.softuni.mobilelele.web;

import bg.softuni.mobilelele.model.dto.UserRegisterDto;
import bg.softuni.mobilelele.model.validation.ApiError;
import bg.softuni.mobilelele.service.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.Cookie;
import java.util.Locale;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RegistrationControllerMockBeanIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmailService mockEmailService;


    @Test
    void testRegistrationWithSuccess() throws Exception {
        UserRegisterDto requestBody = new UserRegisterDto();
        requestBody.setEmail("angel@example.com");
        requestBody.setFirstName("Angel");
        requestBody.setLastName("Angelov");
        requestBody.setPassword("password");
        requestBody.setConfirmPassword("password");

        String json = objectMapper.writeValueAsString(requestBody);

        mockMvc.perform(post("/users/register")
                        .contentType("application/json")
                        .content(json)
                        .cookie(new Cookie("lang", Locale.ENGLISH.getLanguage()))
                        .with(csrf())
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Registration successful"));

        verify(mockEmailService)
                .sendRegistrationEmail("angel@example.com", "Angel Angelov", Locale.ENGLISH);
    }

    @Test
    void testRegistrationFail() throws Exception {
        UserRegisterDto requestBody = new UserRegisterDto();
        requestBody.setEmail("angel@example.com");
        requestBody.setFirstName("");
        requestBody.setLastName("Angelov");
        requestBody.setPassword("password");
        requestBody.setConfirmPassword("password");

        ApiError expectedError = new ApiError();
        expectedError.addError("firstName", "size must be between 2 and 20");
        expectedError.addError("firstName", "must not be empty");
        String expectedJson = objectMapper.writeValueAsString(expectedError);

        String json = objectMapper.writeValueAsString(requestBody);
        mockMvc.perform(post("/users/register")
                        .contentType("application/json")
                        .content(json)
                        .cookie(new Cookie("lang", Locale.ENGLISH.getLanguage()))
                        .with(csrf())
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().json(expectedJson));

        verify(mockEmailService, never()).sendRegistrationEmail("angel@example.com", " Angelov", Locale.GERMAN);
    }
}
