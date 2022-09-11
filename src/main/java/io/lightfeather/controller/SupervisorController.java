package io.lightfeather.controller;

import io.lightfeather.entity.Notification;
import io.lightfeather.service.SupervisorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.logging.Logger;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api")
public class SupervisorController {

    @Autowired
    private SupervisorService supervisorService;

    @GetMapping("/supervisors")
    public ResponseEntity<Collection<String>> getSupervisors() {

        return ResponseEntity.ok(supervisorService.getSupervisors());

    }

    @PostMapping("/submit")
    public ResponseEntity<String> addNotification( @Valid @RequestBody Notification notification) {
        try {
            Logger.getAnonymousLogger().info("Notification: " + notification.toString());
            return ResponseEntity.ok("Notification added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error adding notification: " + e.getMessage());
        }
    }
}
