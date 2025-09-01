package banking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateCommandProcessTest {
    private Bank bankInstance;
    private CreateCommandProcess createCommandProcessInstance;

    @BeforeEach
    void setUp() {
        bankInstance = new Bank();
        createCommandProcessInstance = new CreateCommandProcess(bankInstance);
    }

    @Test
    void supports_create_only() {
        assertTrue(createCommandProcessInstance.supports("create"));
        assertFalse(createCommandProcessInstance.supports("deposit"));
    }
}


