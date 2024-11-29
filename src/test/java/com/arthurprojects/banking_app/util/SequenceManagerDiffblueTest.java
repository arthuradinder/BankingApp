package com.arthurprojects.banking_app.util;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {SequenceManager.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class SequenceManagerDiffblueTest {
    @MockBean
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SequenceManager sequenceManager;

    /**
     * Test {@link SequenceManager#resetAccountSequence()}.
     * <ul>
     *   <li>Given {@link JdbcTemplate}
     * {@link JdbcTemplate#queryForObject(String, Class)} return {@code null}.</li>
     * </ul>
     * <p>
     * Method under test: {@link SequenceManager#resetAccountSequence()}
     */
    @Test
    @DisplayName("Test resetAccountSequence(); given JdbcTemplate queryForObject(String, Class) return 'null'")
    void testResetAccountSequence_givenJdbcTemplateQueryForObjectReturnNull() throws DataAccessException {
        // Arrange
        when(jdbcTemplate.queryForObject(Mockito.<String>any(), Mockito.<Class<Integer>>any())).thenReturn(null);
        doNothing().when(jdbcTemplate).execute(Mockito.<String>any());

        // Act
        sequenceManager.resetAccountSequence();

        // Assert that nothing has changed
        verify(jdbcTemplate, atLeast(1)).execute(Mockito.<String>any());
        verify(jdbcTemplate).queryForObject(eq("SELECT COALESCE(MAX(id), 0) FROM accounts"), isA(Class.class));
    }

    /**
     * Test {@link SequenceManager#resetAccountSequence()}.
     * <ul>
     *   <li>Given {@link JdbcTemplate}
     * {@link JdbcTemplate#queryForObject(String, Class)} return one.</li>
     * </ul>
     * <p>
     * Method under test: {@link SequenceManager#resetAccountSequence()}
     */
    @Test
    @DisplayName("Test resetAccountSequence(); given JdbcTemplate queryForObject(String, Class) return one")
    void testResetAccountSequence_givenJdbcTemplateQueryForObjectReturnOne() throws DataAccessException {
        // Arrange
        when(jdbcTemplate.queryForObject(Mockito.<String>any(), Mockito.<Class<Integer>>any())).thenReturn(1);
        doNothing().when(jdbcTemplate).execute(Mockito.<String>any());

        // Act
        sequenceManager.resetAccountSequence();

        // Assert that nothing has changed
        verify(jdbcTemplate, atLeast(1)).execute(Mockito.<String>any());
        verify(jdbcTemplate).queryForObject(eq("SELECT COALESCE(MAX(id), 0) FROM accounts"), isA(Class.class));
    }

    /**
     * Test {@link SequenceManager#optimizeAccountSequence()}.
     * <p>
     * Method under test: {@link SequenceManager#optimizeAccountSequence()}
     */
    @Test
    @DisplayName("Test optimizeAccountSequence()")
    void testOptimizeAccountSequence() throws DataAccessException {
        // Arrange
        doNothing().when(jdbcTemplate).execute(Mockito.<String>any());

        // Act
        sequenceManager.optimizeAccountSequence();

        // Assert that nothing has changed
        verify(jdbcTemplate).execute(eq(
                "SET @count = 0; UPDATE accounts SET id = @count:= @count + 1 ORDER BY id; ALTER TABLE accounts AUTO_INCREMENT = 1;"));
    }
}
