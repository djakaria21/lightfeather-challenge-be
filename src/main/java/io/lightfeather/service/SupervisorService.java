package io.lightfeather.service;

import io.lightfeather.entity.Supervisor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class SupervisorService {

    RestTemplate restTemplate;

    @Value("${supervisor.url}")
    private String supervisorUrl;

    public ArrayList<String> getSupervisors() {

        restTemplate = new RestTemplate();
        Supervisor[] supervisorList = restTemplate.getForObject(supervisorUrl, Supervisor[].class);

        ArrayList<String> processedSupervisorList = new ArrayList<String>();

        for (Supervisor supervisor : supervisorList) {
            processedSupervisorList.add(supervisor.getJurisdiction() + " - " + supervisor.getLastName() + ", " + supervisor.getFirstName());
         }
        return processedSupervisorList;
    }
}
