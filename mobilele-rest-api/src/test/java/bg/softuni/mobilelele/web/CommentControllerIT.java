package bg.softuni.mobilelele.web;

import bg.softuni.mobilelele.model.dto.CommentAddDto;
import bg.softuni.mobilelele.model.dto.CommentViewDto;
import bg.softuni.mobilelele.model.entity.CommentEntity;
import bg.softuni.mobilelele.model.entity.ModelEntity;
import bg.softuni.mobilelele.model.entity.OfferEntity;
import bg.softuni.mobilelele.model.entity.UserEntity;
import bg.softuni.mobilelele.util.TestDataUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestDataUtils testDataUtils;

    @Autowired
    private ObjectMapper objectMapper;

    private UserEntity testUser, testAdmin;

    private OfferEntity testOffer;

    private ModelEntity testModel;

    private CommentEntity userComment, adminComment;

    @BeforeEach
    void setUp() {
        testUser = testDataUtils.createTestUser("testuser@example.com");
        testAdmin = testDataUtils.createTestAdmin("testadmin@example.com");
        testModel = testDataUtils.createTestModel(testDataUtils.createTestBrand());

        testOffer = testDataUtils.createTestOffer(testUser, testModel);
        userComment = testDataUtils.createTestComment(testUser, testOffer, "Test user's comment");
        adminComment = testDataUtils.createTestComment(testAdmin, testOffer, "Test admin's comment");
    }

    @AfterEach
    void tearDown() {
        testDataUtils.cleanUpDatabase();
    }

    @Test
    void testDeleteByAnonymousUser_Unauthorized() throws Exception {
        mockMvc.perform(delete("/offers/{offerId}/comments/{commentId}", testOffer.getId(), userComment.getId())
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "testadmin@example.com", roles = {"ADMIN", "USER"})
    void testDeleteByAdmin() throws Exception {
        mockMvc.perform(delete("/offers/{offerId}/comments/{commentId}", testOffer.getId(), userComment.getId())
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testuser@example.com", roles = "USER")
    void testDeleteByOwner() throws Exception {
        mockMvc.perform(delete("/offers/{offerId}/comments/{commentId}", testOffer.getId(), userComment.getId())
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testuser@example.com", roles = "USER")
    void testDeleteNotOwned_Forbidden() throws Exception {
        mockMvc.perform(delete("/offers/{offerId}/comments/{commentId}", testOffer.getId(), adminComment.getId())
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = "testuser@example.com", userDetailsServiceBeanName = "testUserDataService")
    void testAddCommentSuccess() throws Exception {
        // Arrange
        long initialCount = testDataUtils.getCommentRepository().count();
        CommentAddDto commentAddDto = new CommentAddDto("The test user tries to add a comment");
        String commentJson = objectMapper.writeValueAsString(commentAddDto);

        // Act
        MvcResult result = mockMvc.perform(post("/offers/{offerId}/comments", testOffer.getId())
                        .contentType("application/json")
                        .content(commentJson)
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andReturn();

        // Assert
        long newCount = testDataUtils.getCommentRepository().count();
        Assertions.assertEquals(initialCount + 1, newCount, "Expected comment count to increase by 1");

        String responseBody = result.getResponse().getContentAsString();
        CommentViewDto createdComment = objectMapper.readValue(responseBody, CommentViewDto.class);

        Assertions.assertNotNull(createdComment.getCommentId());
        Assertions.assertTrue(testDataUtils.getCommentRepository().existsById(createdComment.getCommentId()));
    }

    @Test
    void testAddComment_UnauthenticatedUser_Forbidden() throws Exception {
        // Arrange
        CommentAddDto commentAddDto = new CommentAddDto("Anonymous tries to comment");
        String commentJson = objectMapper.writeValueAsString(commentAddDto);

        // Act & Assert
        mockMvc.perform(post("/offers/{offerId}/comments", testOffer.getId())
                        .contentType("application/json")
                        .content(commentJson)
                        .with(csrf())) // CSRF is present, but user is not authenticated
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = "testuser@example.com", userDetailsServiceBeanName = "testUserDataService")
    void testAddComment_BlankMessage_BadRequest() throws Exception {
        CommentAddDto invalidComment = new CommentAddDto("   "); // Blank or whitespace-only message
        String json = objectMapper.writeValueAsString(invalidComment);

        long beforeCount = testDataUtils.getCommentRepository().count();

        mockMvc.perform(post("/offers/{offerId}/comments", testOffer.getId())
                        .contentType("application/json")
                        .content(json)
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.message").value("must not be blank"));

        long afterCount = testDataUtils.getCommentRepository().count();
        Assertions.assertEquals(beforeCount, afterCount);
    }

    @Test
    void testGetComments_AsAnonymousUser() throws Exception {


        MvcResult result = mockMvc.perform(get("/offers/{offerId}/comments", testOffer.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].message").value("Test user's comment"))
                .andExpect(jsonPath("$[1].message").value("Test admin's comment"))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        List<CommentViewDto> comments = Arrays.asList(objectMapper.readValue(jsonResponse, CommentViewDto[].class));

        Assertions.assertFalse(comments.isEmpty());
        Assertions.assertTrue(comments.stream().anyMatch(c -> c.getMessage().equals(userComment.getTextContent())));
        Assertions.assertTrue(comments.stream().anyMatch(c -> c.getMessage().equals(adminComment.getTextContent())));
    }
}
