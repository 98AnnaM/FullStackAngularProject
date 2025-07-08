package bg.softuni.mobilelele.web;

import bg.softuni.mobilelele.model.dto.OfferAddOrEditDto;
import bg.softuni.mobilelele.model.dto.OfferDetailDTO;
import bg.softuni.mobilelele.model.entity.ModelEntity;
import bg.softuni.mobilelele.model.entity.OfferEntity;
import bg.softuni.mobilelele.model.entity.UserEntity;
import bg.softuni.mobilelele.model.enums.EngineEnum;
import bg.softuni.mobilelele.model.enums.TransmissionEnum;
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

import java.math.BigDecimal;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OfferControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestDataUtils testDataUtils;

    @Autowired
    private ObjectMapper objectMapper;

    private UserEntity testUser, testAdmin;

    private OfferEntity testOffer, testAdminOffer;

    private ModelEntity testModel;

    @BeforeEach
    void setUp() {
        testUser = testDataUtils.createTestUser("testuser@example.com");
        testAdmin = testDataUtils.createTestAdmin("testadmin@example.com");
        testModel = testDataUtils.createTestModel(testDataUtils.createTestBrand());

        testOffer = testDataUtils.createTestOffer(testUser, testModel);
        testAdminOffer = testDataUtils.createTestOffer(testAdmin, testModel);
    }

    @AfterEach
    void tearDown() {
        testDataUtils.cleanUpDatabase();
    }

    @Test
    void testDeleteByAnonymousUser_Unauthorized() throws Exception {
        mockMvc.perform(delete("/offers/{id}", testOffer.getId()).with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "testadmin@example.com", roles = {"ADMIN", "USER"})
    void testDeleteByAdmin() throws Exception {
        mockMvc.perform(delete("/offers/{id}", testOffer.getId()).with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "testuser@example.com", roles = "USER")
    void testDeleteByOwner() throws Exception {
        mockMvc.perform(delete("/offers/{id}", testOffer.getId()).with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "testuser@example.com", roles = "USER")
    void testDeleteNotOwned_Forbidden() throws Exception {
        mockMvc.perform(delete("/offers/{id}", testAdminOffer.getId()).with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = "testuser@example.com", userDetailsServiceBeanName = "testUserDataService")
    void testAddOffer() throws Exception {
        long beforeCount = testDataUtils.getOfferRepository().count();

        OfferAddOrEditDto dto = new OfferAddOrEditDto();
        dto.setModelId(testModel.getId());
        dto.setPrice(BigDecimal.valueOf(11200));
        dto.setEngine(EngineEnum.GASOLINE.name());
        dto.setYear(1979);
        dto.setMileage(1000);
        dto.setDescription("test");
        dto.setTransmission(TransmissionEnum.MANUAL.name());
        dto.setImageUrl("image://test.png");

        String json = objectMapper.writeValueAsString(dto);

        MvcResult result = mockMvc.perform(post("/offers/add")
                        .contentType("application/json")
                        .content(json)
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andReturn();

        long afterCount = testDataUtils.getOfferRepository().count();
        Assertions.assertEquals(beforeCount + 1, afterCount);

        String responseBody = result.getResponse().getContentAsString();
        OfferDetailDTO createdOffer = objectMapper.readValue(responseBody, OfferDetailDTO.class);

        Assertions.assertTrue(testDataUtils.getOfferRepository().existsById(createdOffer.getId()));
    }
}
