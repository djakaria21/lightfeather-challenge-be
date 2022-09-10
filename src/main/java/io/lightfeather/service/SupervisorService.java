package io.lightfeather.service;

import io.lightfeather.entity.Supervisor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupervisorService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${supervisor.url}")
    private String supervisorUrl;

    public ArrayList<String> getSupervisors() {

        Supervisor[] supervisorList = restTemplate.getForObject(supervisorUrl, Supervisor[].class);

        List<Supervisor> supervisorList2 = Arrays.asList(supervisorList);

        Collections.sort(supervisorList2);
        ArrayList<String> processedSupervisorList = new ArrayList<String>();

        for (Supervisor supervisor : supervisorList2) {

            if(!Character.isDigit(supervisor.getJurisdiction().charAt(0))) {
                processedSupervisorList.add(supervisor.getJurisdiction() + " - " + supervisor.getLastName() + ", " + supervisor.getFirstName());
            }
        }

        return processedSupervisorList;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
