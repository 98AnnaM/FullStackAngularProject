package bg.softuni.mobilelele.web;

import bg.softuni.mobilelele.model.dto.UserRegisterDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import javax.mail.internet.MimeMessage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RegistrationControllerGreenMailIT {

    @Value("${mail.host}")
    private String mailHost;

    @Value("${mail.port}")
    private Integer mailPort;

    @Value("${mail.username}")
    private String mailUsername;

    @Value("${mail.password}")
    private String mailPassword;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private GreenMail greenMail;

    @BeforeEach
    void setup() {
        greenMail = new GreenMail(new ServerSetup(mailPort, mailHost, "smtp"));
        greenMail.start();
        greenMail.setUser(mailUsername, mailPassword);
    }

    @AfterEach
    void tearDown() {
        greenMail.stop();
    }

    @Test
    void testRegistrationWithSuccess() throws Exception {
        UserRegisterDto dto = new UserRegisterDto();
        dto.setEmail("angela@example.com");
        dto.setFirstName("Angela");
        dto.setLastName("Angelova");
        dto.setPassword("password");
        dto.setConfirmPassword("password");

        String json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/users/register")
                        .contentType("application/json")
                        .content(json)
                        .with(csrf()))
                .andExpect(status().isCreated());

        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        assertEquals(1, receivedMessages.length);
        MimeMessage welcomeMessage = receivedMessages[0];
        assertTrue(welcomeMessage.getContent().toString().contains("Angela Angelova"));
    }
}
