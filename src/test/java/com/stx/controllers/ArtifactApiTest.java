package com.stx.controllers;

import com.stx.controllers.data.UserTestService;
import com.stx.services.ArtifactService;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("testU")
class ArtifactApiTest {
    private final MockMvc mockMvc;
    private final ArtifactService artifactService;
    private final UserTestService userTestService;

    @Autowired
    ArtifactApiTest(MockMvc mockMvc, ArtifactService artifactService, UserTestService userTestService) {
        this.mockMvc = mockMvc;
        this.artifactService = artifactService;
        this.userTestService = userTestService;
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void create() throws Exception {
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("category", "test");
        jsonRequest.put("description", "test");

        MvcResult mvcResult = mockMvc.perform(post("/api/artifact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest.toString()))
                .andExpect(status().isOk())
                .andReturn();
        JSONObject jsonResponse = new JSONObject(mvcResult.getResponse().getContentAsString());
        assertNotNull(jsonResponse.getString("id"));

        artifactService.delete(jsonResponse.getString("id"));
    }

    @Test
    void update() throws Exception {
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("category", "test123");
        jsonRequest.put("description", "test123");

        MvcResult mvcResult = mockMvc.perform(post("/api/artifact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest.toString()))
                .andExpect(status().isOk())
                .andReturn();
        JSONObject jsonResponse = new JSONObject(mvcResult.getResponse().getContentAsString());
        assertNotNull(jsonResponse.getString("id"));

        JSONObject jsonRequestUpdate = new JSONObject();
        jsonRequestUpdate.put("id", jsonResponse.getString("id"));
        jsonRequestUpdate.put("category", "test");
        jsonRequestUpdate.put("description", "test");

        MvcResult mvcResultUpdate = mockMvc.perform(post("/api/artifact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestUpdate.toString()))
                .andExpect(status().isOk())
                .andReturn();
        JSONObject jsonResponseUpdate = new JSONObject(mvcResultUpdate.getResponse().getContentAsString());
        assertEquals(jsonResponse.getString("id"), jsonResponseUpdate.getString("id"));

        artifactService.delete(jsonResponse.getString("id"));
    }

    @Test
    void delete() throws Exception {
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("category", "test123");
        jsonRequest.put("description", "test123");

        MvcResult mvcResult = mockMvc.perform(post("/api/artifact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest.toString()))
                .andExpect(status().isOk())
                .andReturn();
        JSONObject jsonResponse = new JSONObject(mvcResult.getResponse().getContentAsString());
        assertNotNull(jsonResponse.getString("id"));

        MvcResult mvcResultDelete =  mockMvc.perform(MockMvcRequestBuilders.delete("/api/artifact")
                        .param("uuid", jsonResponse.getString("id")))
                .andExpect(status().isOk())
                .andReturn();

        Assertions.assertEquals(jsonResponse.getString("id"), mvcResultDelete.getResponse().getContentAsString());
    }

    @Test
    void get() throws Exception {
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("category", "test123");
        jsonRequest.put("description", "test123");

        MvcResult mvcResult = mockMvc.perform(post("/api/artifact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest.toString()))
                .andExpect(status().isOk())
                .andReturn();
        JSONObject jsonResponse = new JSONObject(mvcResult.getResponse().getContentAsString());
        assertNotNull(jsonResponse.getString("id"));


        MvcResult mvcResultGet = mockMvc.perform(MockMvcRequestBuilders.get("/api/artifact")
                        .param("uuid", jsonResponse.getString("id")))
                .andExpect(status().isOk())
                .andReturn();

        assertNotNull(mvcResultGet.getResponse().getContentAsString());
    }
}