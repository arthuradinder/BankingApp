package com.arthurprojects.banking_app.scheduler;

import com.arthurprojects.banking_app.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SequenceOptimizationScheduler {
    @Autowired
    private AccountService accountService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void optimizeSequences() {
        accountService.optimizeIds();
    }
}
