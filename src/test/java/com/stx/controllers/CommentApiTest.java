package com.stx.controllers;

import com.stx.services.ArtifactService;
import com.stx.services.CommentService;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("testU")
class CommentApiTest {
    private final CommentService commentService;
    private final ArtifactService artifactService;
    private final MockMvc mockMvc;

    @Autowired
    CommentApiTest(CommentService commentService, ArtifactService artifactService, MockMvc mockMvc) {
        this.commentService = commentService;
        this.artifactService = artifactService;
        this.mockMvc = mockMvc;
    }

    @Test
    void create() throws Exception {
        JSONObject jsonArtifactRequest = new JSONObject();
        jsonArtifactRequest.put("category", "test");
        jsonArtifactRequest.put("description", "test");

        MvcResult mvcCreateArtifactResult = mockMvc.perform(post("/api/artifact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonArtifactRequest.toString()))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject jsonArtifactResponse = new JSONObject(mvcCreateArtifactResult.getResponse().getContentAsString());
        Assertions.assertNotNull(jsonArtifactResponse);

        JSONObject jsonCommentRequest = new JSONObject();
        jsonCommentRequest.put("content", "test");
        jsonCommentRequest.put("artifactId", jsonArtifactResponse.getString("id"));

        MvcResult mvcCommentResult = mockMvc.perform(post("/api/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCommentRequest.toString()))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject jsonCommentResponse = new JSONObject(mvcCommentResult.getResponse().getContentAsString());
        Assertions.assertNotNull(jsonCommentResponse.getString("id"));

        commentService.delete(jsonCommentResponse.getString("id"));
        artifactService.delete(jsonArtifactResponse.getString("id"));
    }
}