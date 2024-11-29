package com.arthurprojects.banking_app.listener;

import com.arthurprojects.banking_app.util.SequenceManager;
import jakarta.persistence.PostRemove;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j

//This Entity Listener handles post-delete operations
public class AccountEntityListener {
    @Autowired
    private SequenceManager sequenceManager;

    @PostRemove
    public void onPostRemove(Object entity) {
        log.info("Entity deleted, reorganizing sequences");
        sequenceManager.resetAccountSequence();
    }
}
