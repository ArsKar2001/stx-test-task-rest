package com.stx.controllers;

import com.stx.controllers.data.UserTestService;
import com.stx.domains.dtos.UserDto;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthApiTest {

    private final MockMvc mockMvc;
    private final UserTestService userTestService;

    private final String username = "testU";
    private final String password = "testP";

    @Autowired
    public AuthApiTest(MockMvc mockMvc, UserTestService userTestService) {
        this.mockMvc = mockMvc;
        this.userTestService = userTestService;
    }

    @Test
    void testLoginSuccess() throws Exception {
        UserDto user = userTestService.createUser(username, password);

        MvcResult result = mockMvc.perform(post("/api/public/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"%s\", \"password\": \"%s\"}".formatted(username, password)))
                .andExpect(status().isOk())
                .andExpect(header().exists(HttpHeaders.AUTHORIZATION))
                .andReturn();

        String sJson = result.getResponse().getContentAsString();
        JSONObject object = new JSONObject(sJson);
        assertEquals(user.getId().toString(), object.getString("id"), "User ids must match");
    }

    @Test
    void testLoginFail() throws Exception {
        mockMvc.perform(post("/api/public/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"%s\", \"password\": \"%s\"}".formatted(username, "123")))
                .andExpect(status().isUnauthorized())
                .andExpect(header().exists(HttpHeaders.AUTHORIZATION))
                .andReturn();
    }

    @Test
    void testSignInSuccess() throws Exception {
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("username", "testSignIn");
        jsonRequest.put("password", "testSignIn");
        jsonRequest.put("rePassword", "testSignIn");

        MvcResult result = mockMvc.perform(post("/api/public/signIn")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest.toString()))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject jsonResponse = new JSONObject(result.getResponse().getContentAsString());
        assertEquals(jsonRequest.getString("username"), jsonResponse.getString("username"));

        userTestService.deleteUser(jsonResponse.getString("id"));
    }

    @Test
    void testSignInFail() throws Exception {
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("username", "testSignIn");

        MvcResult result = mockMvc.perform(post("/api/public/signIn")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest.toString()))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}