package com.arthurprojects.banking_app.controller;

import com.arthurprojects.banking_app.util.SequenceManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/maintenance")
public class MaintenanceController {
    @Autowired
    private SequenceManager sequenceManager;

    @PostMapping("/resequence")
    public ResponseEntity<String> resequenceAccounts() {
        try {
            sequenceManager.resetAccountSequence();
            return ResponseEntity.ok("Account IDs resequenced successfully");
        } catch (Exception e) {
            log.error("Error during resequencing: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error during resequencing: " + e.getMessage());
        }
    }
}
