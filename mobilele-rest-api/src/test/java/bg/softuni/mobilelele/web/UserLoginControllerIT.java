package bg.softuni.mobilelele.web;

import bg.softuni.mobilelele.model.dto.UserLoginDto;
import bg.softuni.mobilelele.model.entity.UserEntity;
import bg.softuni.mobilelele.util.TestDataUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserLoginControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TestDataUtils testDataUtils;

    private UserEntity testUser, testAdmin;

    @BeforeEach
    void setUp() {
        testUser = testDataUtils.createTestUser("testuser@example.com");
    }

    @AfterEach
    void tearDown() {
        testDataUtils.cleanUpDatabase();
    }

    @Test
    void testLoginSuccess() throws Exception {
        UserLoginDto loginDto = new UserLoginDto();
        loginDto.setEmail(testUser.getEmail());
        loginDto.setPassword("correctPassword");

        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isString());
    }

    @Test
    void testLoginFailWrongPassword() throws Exception {
        UserLoginDto loginDto = new UserLoginDto();
        loginDto.setEmail(testUser.getEmail());
        loginDto.setPassword("wrongPassword");

        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isForbidden());
    }

    @Test
    void testLoginFailEmptyEmail() throws Exception {
        UserLoginDto loginDto = new UserLoginDto();
        loginDto.setEmail("");
        loginDto.setPassword("somePassword");

        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.email", hasItem("User email should be provided.")));
    }

    @Test
    void testLoginFailInvalidEmail() throws Exception {
        UserLoginDto loginDto = new UserLoginDto();
        loginDto.setEmail("invalid-email");
        loginDto.setPassword("somePassword");

        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.email", hasItem("User email should be valid.")));
    }

    @Test
    void testLoginFailEmptyPassword() throws Exception {
        UserLoginDto loginDto = new UserLoginDto();
        loginDto.setEmail("user@example.com");
        loginDto.setPassword("");

        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.password", hasItem("must not be empty")));
    }
}
