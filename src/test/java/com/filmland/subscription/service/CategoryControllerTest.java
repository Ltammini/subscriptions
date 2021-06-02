package com.filmland.subscription.service;

import com.filmland.subscription.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:data.sql")
class CategoryControllerTest extends AbstractTest{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void userRepositoryWithoutTokenIsForbidden() throws Exception {
        mockMvc.perform(get("/categories")).andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Get all categories")
    void getCategories() throws Exception {
        final String token = extractToken(login("dev@sogeti.com", "Bhavscool").andReturn());
        mockMvc.perform(get("/categories")
                .param("username", "dev@sogeti.com")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.availableCategories").exists())
                .andExpect(jsonPath("$.availableCategories").value(hasSize(1)))
                .andExpect(jsonPath("$.availableCategories.[0].name").value("Dutch Series"))
                .andExpect(jsonPath("$.availableCategories.[0].availableContent").value(20))
                .andExpect(jsonPath("$.availableCategories.[0].price").value(6.0))
                .andExpect(jsonPath("$.subscribedCategories").exists())
                .andExpect(jsonPath("$.subscribedCategories").value(hasSize(2)))
                .andExpect(jsonPath("$.subscribedCategories.[0].name").value("International Films"))
                .andExpect(jsonPath("$.subscribedCategories.[0].remainingContent").value(10))
                .andExpect(jsonPath("$.subscribedCategories.[0].price").value(5.0))
                .andExpect(jsonPath("$.subscribedCategories.[0].startDate").value("2021-01-01T00:00:00"))
                .andExpect(jsonPath("$.subscribedCategories.[1].name").value("Dutch Films"))
                .andExpect(jsonPath("$.subscribedCategories.[1].remainingContent").value(10))
                .andExpect(jsonPath("$.subscribedCategories.[1].price").value(4.0))
                .andExpect(jsonPath("$.subscribedCategories.[1].startDate").value("2021-01-01T00:00:00"));

    }

}