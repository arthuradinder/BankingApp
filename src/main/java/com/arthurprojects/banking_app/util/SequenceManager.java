package com.arthurprojects.banking_app.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

//this utility class manages ID sequences

@Component
@Slf4j
public class SequenceManager {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public void resetAccountSequence() {

        try {
            log.info("Starting sequence reset");

            // Create temporary table with new sequential IDs
            jdbcTemplate.execute(
                    "CREATE TEMPORARY TABLE temp_accounts AS " +
                            "SELECT *, ROW_NUMBER() OVER (ORDER BY id) AS new_id " +
                            "FROM accounts"
            );

            // Update existing accounts with new sequential IDs
            jdbcTemplate.execute(
                    "UPDATE accounts a " +
                            "JOIN temp_accounts t ON a.id = t.id " +
                            "SET a.id = t.new_id"
            );

            // Drop temporary table
            jdbcTemplate.execute("DROP TEMPORARY TABLE IF EXISTS temp_accounts");

            // Get the maximum ID currently in use
            Integer maxId = jdbcTemplate.queryForObject(
                    "SELECT COALESCE(MAX(id), 0) FROM accounts", Integer.class);

            // Reset the auto-increment to the next available number
            if (maxId != null) {
                log.info("Max ID found: {}", maxId);
                jdbcTemplate.execute(
                        "ALTER TABLE accounts AUTO_INCREMENT = " + (maxId + 1));
                log.info("Sequence reset completed");
            }
        } catch (Exception e) {
            log.error("Error resetting sequence", e);
            throw e;
        }

    }

    public void optimizeAccountSequence() {
        // This will reset the sequence to fill gaps
        jdbcTemplate.execute(
                "SET @count = 0; " +
                        "UPDATE accounts SET id = @count:= @count + 1 ORDER BY id; " +
                        "ALTER TABLE accounts AUTO_INCREMENT = 1;"
        );
    }
}
