package io.lightfeather.controller;

import io.lightfeather.service.SupervisorService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
public class SupervisorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    SupervisorController supervisorController;

    @Mock
    SupervisorService supervisorService;

    ArrayList<String> processedSupervisorList;

    @BeforeEach
    public void before() {
        mockMvc = MockMvcBuilders.standaloneSetup(supervisorController).build();

        processedSupervisorList = new ArrayList<String>();
        processedSupervisorList.add("CA - Smith, John");
        processedSupervisorList.add("CA - Doe, Jane");

    }

    @SneakyThrows
    @Test
    void getSupervisors() {

        when(supervisorService.getSupervisors()).thenReturn(processedSupervisorList);
        mockMvc.perform(
                get("/api/supervisors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content().string(containsString("CA - Doe, Jane")));

    }

    @SneakyThrows
    @Test
    void postNotification(){

        mockMvc.perform(
                post("/api/submit")
                        .contentType(MediaType.APPLICATION_JSON).content("{\"firstName\": \"John\", " +
                                "\"lastName\": \"Smith\", " +
                                "\"email\": \"something@gmail.com\", " +
                                "\"phoneNumber\": \"124-234-5678\", " +
                                "\"supervisor\": \"CA - Doe, Jane\"}")).andExpect(status().isOk());
               }

    @SneakyThrows
    @Test
    void postNotificationShort(){

        mockMvc.perform(
                post("/api/submit")
                        .contentType(MediaType.APPLICATION_JSON).content("{\"firstName\": \"John\", " +
                                "\"lastName\": \"Smith\", " +
                                "\"supervisor\": \"CA - Doe, Jane\"}")).andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    void postNotificationWithPhoneValidationErrors(){

        mockMvc.perform(
                post("/api/submit")
                        .contentType(MediaType.APPLICATION_JSON).content("{\"firstName\": \"John\", " +
                                "\"lastName\": \"Smith\", " +
                                "\"email\": \"something@gmail.com\", " +
                                "\"phoneNumber\": \"1d234d5678\", " +
                                "\"supervisor\": \"CA - Doe, Jane\"}")).andExpect(status().is4xxClientError());
        ;



    }


    @SneakyThrows
    @Test
    void postNotificationWithEmailValidationErrors(){

            mockMvc.perform(
                    post("/api/submit")
                            .contentType(MediaType.APPLICATION_JSON).content("{\"firstName\": \"John\", " +
                                    "\"lastName\": \"Smith\", " +
                                    "\"email\": \"somethingcom\", " +
                                    "\"phoneNumber\": \"112-234-5678\", " +
                                    "\"supervisor\": \"CA - Doe, Jane\"}")).andExpect(status().is4xxClientError());
    }

    @SneakyThrows
    @Test
    void postNotificationWithFirstNameValidationErrors(){

            mockMvc.perform(
                    post("/api/submit")
                            .contentType(MediaType.APPLICATION_JSON).content("{\"firstName\": \"Jo3hn\", " +
                                    "\"lastName\": \"Smith\", " +
                                    "\"email\": \"something@gmail.com\\" +
                                    "\"phoneNumber\": \"112-234-5678\", " +
                                    "\"supervisor\": \"CA - Doe, Jane\"}")).andExpect(status().is4xxClientError());

    }

    @SneakyThrows
    @Test
    void postNotificationWithLastNameValidationErrors(){

            mockMvc.perform(
                    post("/api/submit")
                            .contentType(MediaType.APPLICATION_JSON).content("{\"firstName\": \"John\", " +
                                    "\"lastName\": \"Smi3th\", " +
                                    "\"email\": \"something@gmail.com\", " +
                                    "\"phoneNumber\": \"112-234-5678\", " +
                                    "\"supervisor\": \"CA - Doe, Jane\"}")).andExpect(status().is4xxClientError());

    }


}
