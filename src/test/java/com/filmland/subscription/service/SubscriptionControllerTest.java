package com.filmland.subscription.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.filmland.subscription.dto.SubscribeDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:data.sql")
public class SubscriptionControllerTest extends AbstractTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    public void userRepositoryWithoutTokenIsForbidden() throws Exception {
        mockMvc.perform(get("/subscribe")).andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Subscribe a category")
    void getCategories() throws Exception {
        SubscribeDto subscribeDto = SubscribeDto.builder().email("java@sogeti.com").name("Dutch Series").build();
        final String token = extractToken(login("java@sogeti.com", "Javaiscool").andReturn());

        mockMvc.perform(
                post("/subscribe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(subscribeDto))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Your subscription to Dutch Series is successful"));

    }

}
