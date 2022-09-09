package io.lightfeather.controller;

import io.lightfeather.entity.Supervisor;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
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


}
