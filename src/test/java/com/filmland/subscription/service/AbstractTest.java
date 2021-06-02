package com.filmland.subscription.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.filmland.subscription.dto.UserDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:data.sql")
@Disabled
public class AbstractTest {
    @Autowired
    protected MockMvc mockMvc;

    protected ResultActions login(String username, String password) throws Exception {
        final UserDto auth = new UserDto();
        auth.setUserId(username);
        auth.setPassword(password);
        return mockMvc.perform(
                post("/login")
                        .content(json(auth))
                        .contentType(MediaType.APPLICATION_JSON));
    }

    protected String json(Object o) throws IOException {
        return new ObjectMapper().writeValueAsString(o);
    }

    protected String extractToken(MvcResult result) throws UnsupportedEncodingException {
        return result.getResponse().getContentAsString();
    }
}
