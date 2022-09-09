package io.lightfeather.service;

import io.lightfeather.entity.Supervisor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SupervisorServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    SupervisorService supervisorService;

    @Value("${supervisor.url}")
    String supervisorUrl;
    ArrayList<Supervisor> supervisorList;

    private Supervisor createSupervisor(Integer id, String phone, String jurisdiction, String identificationNumber, String firstName, String lastName) {
        Supervisor supervisor = new Supervisor();
        supervisor.setId(id);
        supervisor.setPhone(phone);
        supervisor.setJurisdiction(jurisdiction);
        supervisor.setIdentificationNumber(identificationNumber);
        supervisor.setFirstName(firstName);
        supervisor.setLastName(lastName);

        return supervisor;
    }
    @BeforeEach
    public void before() {
        supervisorList = new ArrayList<Supervisor>();

        supervisorList.add(createSupervisor(1, "1234567890", "CA", "123456789", "John", "Smith"));
        supervisorList.add(createSupervisor(2, "1234567890", "CA", "123456789", "Jane", "Doe"));

    }

    @SneakyThrows
    @Test
    void processSupervisors() {

        when(restTemplate.getForObject(supervisorUrl, Supervisor[].class)).thenReturn(supervisorList.toArray(new Supervisor[0]));

        ArrayList<String> processedSupervisorList = supervisorService.getSupervisors();
        assert(processedSupervisorList.size() == 2);
        assert(processedSupervisorList.get(0).equals("CA - Smith, John"));
        assert(processedSupervisorList.get(1).equals("CA - Doe, Jane"));
    }


}
