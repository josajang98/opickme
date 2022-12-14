package com.ssafy.api.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.api.service.QuestionService;
import com.ssafy.api.service.ScriptService;
import com.ssafy.api.service.UserService;
import com.ssafy.common.auth.SsafyUserDetailService;
import com.ssafy.converter.Converter;
import com.ssafy.db.entity.Question;
import com.ssafy.db.entity.Script;
import com.ssafy.db.entity.User;
import com.ssafy.stt.STT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ScriptController.class)
class ScriptControllerTest {
    @MockBean
    UserService userService;
    @MockBean
    PasswordEncoder passwordEncoder;
    @MockBean
    SsafyUserDetailService ssafyUserDetailService;
    @MockBean
    QuestionService questionService;
    @MockBean
    ScriptService scriptService;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    Converter converter;

    @MockBean
    STT stt;
    @Test
    void 스크립트_등록_성공() throws Exception {
        Map<String, String> input = new HashMap<>();

        input.put("userId", "1");
        input.put("questionId", "1");
        input.put("audioURL", "C:/Users/multicampus/Desktop/ssafy/java-speech/samples/snapshot/resources/audio.raw");
        input.put("keyName", "0296f912-7c83-4be8-8b52-7948e3927667");

        given(userService.getUserByUserId(1L)).willReturn(Optional.ofNullable(new User()));
        given(questionService.getQuestionByQuestionId(1L)).willReturn(Optional.ofNullable(new Question()));

        mockMvc.perform(post("/api/v1/script")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(input))
                )
                .andExpect(status().is2xxSuccessful());
    }
    @Test
    void 스크립트_조회_성공() throws Exception {
        given(userService.getUserByUsername("test")).willReturn(Optional.ofNullable(new User()));
        given(scriptService.getScriptList(1L)).willReturn(new ArrayList<>());

        mockMvc.perform(get("/api/v1/script/test")).andExpect(status().isOk());
    }

    @Test
    void 스크립트_디테일_조회_성공() throws Exception {
        Long scriptId =1L;
        User user = new User();
        user.setId(1L);
        given(userService.getUserByUsername("test")).willReturn(Optional.ofNullable(user));
        given(scriptService.getDetail(scriptId,1L)).willReturn(Optional.ofNullable(new Script()));
        mockMvc.perform(get("/api/v1/script/test/"+scriptId))
                .andExpect(status().isOk());
    }

    @Test
    void 스크립트_삭제_성공() throws Exception {

        Long scriptId =1L;

        mockMvc.perform(delete("/api/v1/script/"+scriptId))
                .andExpect(status().isOk());
    }


    @Test
    void 스크립트_유효성검증_실패() throws Exception {
        Map<String, String> input = new HashMap<>();

        input.put("userId", "");
        input.put("questionId", "1");
        input.put("audioURL", "C:/Users/multicampus/Desktop/ssafy/java-speech/samples/snapshot/resources/audio.raw");
        input.put("keyName", "0296f912-7c83-4be8-8b52-7948e3927667");

        given(userService.getUserByUserId(1L)).willReturn(Optional.ofNullable(new User()));
        given(questionService.getQuestionByQuestionId(1L)).willReturn(Optional.ofNullable(new Question()));

        mockMvc.perform(post("/api/v1/script")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(input))
                )
                .andExpect(status().is4xxClientError());
    }
}