package bg.softuni.mobilelele.web;

import bg.softuni.mobilelele.model.dto.OfferDetailDTO;
import bg.softuni.mobilelele.model.dto.UserLoginDto;
import bg.softuni.mobilelele.model.dto.UserViewDto;
import bg.softuni.mobilelele.model.entity.UserEntity;
import bg.softuni.mobilelele.model.entity.UserRoleEntity;
import bg.softuni.mobilelele.model.enums.UserRoleEnum;
import bg.softuni.mobilelele.util.TestDataUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        testAdmin = testDataUtils.createTestAdmin("testadmin@example.com");
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

        MvcResult result = mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        UserViewDto loggedUser = objectMapper.readValue(responseBody, UserViewDto.class);

        Assertions.assertNotNull(loggedUser);
        Assertions.assertNotNull(loggedUser.getToken());
        Assertions.assertEquals(testUser.getEmail(), loggedUser.getEmail());
        Assertions.assertEquals(testUser.getFirstName(), loggedUser.getFirstName());
        Assertions.assertEquals(testUser.getLastName(), loggedUser.getLastName());
        Assertions.assertEquals(List.of("USER"), loggedUser.getAuthorities());
    }

    @Test
    void testLoginSuccessAdmin() throws Exception {
        UserLoginDto loginDto = new UserLoginDto();
        loginDto.setEmail(testAdmin.getEmail());
        loginDto.setPassword("correctPassword");

        MvcResult result = mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        UserViewDto loggedUser = objectMapper.readValue(responseBody, UserViewDto.class);

        Assertions.assertNotNull(loggedUser);
        Assertions.assertNotNull(loggedUser.getToken());
        Assertions.assertEquals(testAdmin.getEmail(), loggedUser.getEmail());
        Assertions.assertEquals(testAdmin.getFirstName(), loggedUser.getFirstName());
        Assertions.assertEquals(testAdmin.getLastName(), loggedUser.getLastName());
        Assertions.assertEquals(List.of("ADMIN", "USER"), loggedUser.getAuthorities());
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
