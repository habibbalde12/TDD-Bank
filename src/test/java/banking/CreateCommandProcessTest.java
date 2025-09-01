package banking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        Assertions.assertTrue(createCommandProcessInstance.supports("create"));
        Assertions.assertFalse(createCommandProcessInstance.supports("deposit"));
    }

}


