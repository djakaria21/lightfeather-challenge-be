package io.lightfeather.controller;

import io.lightfeather.service.SupervisorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/supervisors")
public class SupervisorController {

    private final SupervisorService supervisorService;

    @GetMapping
    public ResponseEntity<Collection<String>> getSupervisors() {

        return ResponseEntity.ok(supervisorService.getSupervisors());

    }
}
