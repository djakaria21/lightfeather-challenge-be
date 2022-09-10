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

import javax.xml.bind.ValidationException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.hamcrest.Matchers.containsString;


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
    void postSupervisor(){

        mockMvc.perform(
                post("/api/supervisors")
                        .contentType(MediaType.APPLICATION_JSON).content("{firstName: \"John\", " +
                                "lastName: \"Smith\", " +
                                "email: \"something@gmail.com\", " +
                                "phoneNumber: \"1-234-5678\", " +
                                "supervisor: \"CA - Doe, Jane\"}")).andExpect(status().isOk());
               }

    @SneakyThrows
    @Test
    void postSupervisorShort(){

        mockMvc.perform(
                post("/api/supervisors")
                        .contentType(MediaType.APPLICATION_JSON).content("{firstName: \"John\", " +
                                "lastName: \"Smith\", " +
                                "supervisor: \"CA - Doe, Jane\"}")).andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    void postSupervisorWithPhoneValidationErrors(){

        Exception exception = assertThrows(ValidationException.class, () ->{
        mockMvc.perform(
                post("/api/supervisors")
                        .contentType(MediaType.APPLICATION_JSON).content("{firstName: \"John\", " +
                                "lastName: \"Smith\", " +
                                "email: \"something@gmail.com\", " +
                                "phoneNumber: \"1d234d5678\", " +
                                "supervisor: \"CA - Doe, Jane\"}")).andExpect(status().isOk());
        });

        String expectedMessage = "phoneNumber is not valid";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }


    @SneakyThrows
    @Test
    void postSupervisorWithEmailValidationErrors(){

        Exception exception = assertThrows(ValidationException.class, () ->{
            mockMvc.perform(
                    post("/api/supervisors")
                            .contentType(MediaType.APPLICATION_JSON).content("{firstName: \"John\", " +
                                    "lastName: \"Smith\", " +
                                    "email: \"somethingcom\", " +
                                    "phoneNumber: \"1-234-5678\", " +
                                    "supervisor: \"CA - Doe, Jane\"}")).andExpect(status().isOk());
        });

        String expectedMessage = "email is not valid";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @SneakyThrows
    @Test
    void postSupervisorWithFirstNameValidationErrors(){

        Exception exception = assertThrows(ValidationException.class, () ->{
            mockMvc.perform(
                    post("/api/supervisors")
                            .contentType(MediaType.APPLICATION_JSON).content("{firstName: \"Jo3hn\", " +
                                    "lastName: \"Smith\", " +
                                    "email: \"something@gmail.com\\" +
                                    "phoneNumber: \"1-234-5678\", " +
                                    "supervisor: \"CA - Doe, Jane\"}")).andExpect(status().isOk());
        });

        String expectedMessage = "firstName is not valid";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @SneakyThrows
    @Test
    void postSupervisorWithLasttNameValidationErrors(){

        Exception exception = assertThrows(ValidationException.class, () ->{
            mockMvc.perform(
                    post("/api/supervisors")
                            .contentType(MediaType.APPLICATION_JSON).content("{firstName: \"John\", " +
                                    "lastName: \"Smi3th\", " +
                                    "email: \"something@gmail.com\", " +
                                    "phoneNumber: \"1-234-5678\", " +
                                    "supervisor: \"CA - Doe, Jane\"}")).andExpect(status().isOk());
        });

        String expectedMessage = "lastName is not valid";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }


}
