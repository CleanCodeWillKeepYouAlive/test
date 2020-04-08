package com.userdemo.UserResult;

import com.userdemo.UserResult.rest.UserController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
@TestMethodOrder(OrderAnnotation.class)
public class UserResultCacheTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserController userController;

    private final String firstUser = "{\"user_id\":1,\"level_id\":3,\"result\":55}";
    private final String secondUser = "{\"user_id\":1,\"level_id\":3,\"result\":60}";

    @Test
    @org.junit.jupiter.api.Order(1)
    public void setFirstUser() throws Exception {
        this.mockMvc.perform(put("/users/setinfo")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(firstUser))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(
                        "data processing"));
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    public void userExistHandle() throws Exception {
        this.mockMvc.perform(put("/users/setinfo")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(firstUser))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(
                        "user with such result already reported"));
    }


    @Test
    @org.junit.jupiter.api.Order(3)
    public void getUsersFirstQuery() throws Exception {
        this.mockMvc.perform(get("/users/userinfo/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(
                        "[" + firstUser + "]"));
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    public void getUsersLevelFirstQuery() throws Exception {
        this.mockMvc.perform(get("/users/levelinfo/3")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(
                        "[" + firstUser + "]"));
    }

    // SECOND PART

    @Test
    @org.junit.jupiter.api.Order(5)
    public void setSecondUser() throws Exception {
        this.mockMvc.perform(put("/users/setinfo")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(secondUser))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(
                        "data processing"));
    }

    @Test
    @org.junit.jupiter.api.Order(6)
    public void getUserSecondQuery() throws Exception {
        this.mockMvc.perform(get("/users/userinfo/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString(
                        "\"user_id\":1")))
                .andExpect(content().string(containsString(
                        "\"result\":55")))
                .andExpect(content().string(containsString(
                        "\"result\":60")));
    }

    @Test
    @org.junit.jupiter.api.Order(7)
    public void getUserLevelSecondQuery() throws Exception {
        this.mockMvc.perform(get("/users/levelinfo/3")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString(
                        "\"level_id\":3")))
                .andExpect(content().string(containsString(
                        "\"result\":55")))
                .andExpect(content().string(containsString(
                        "\"result\":60")));
    }
}
