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

    @Test
    void process_tokens_creates_checking() {
        String[] tokens = {"create", "checking", "12345678", "1.5"};
        createCommandProcessInstance.processTokens(tokens);
        Account createdAccount = bankInstance.getAccount("12345678");
        Assertions.assertNotNull(createdAccount);
        Assertions.assertTrue(createdAccount instanceof Checkings);
        Assertions.assertEquals(1.5, createdAccount.getApr(), 0.0001);
    }

    @Test
    void process_tokens_creates_savings() {
        String[] tokens = {"create", "savings", "87654321", "2.0"};
        createCommandProcessInstance.processTokens(tokens);
        Account createdAccount = bankInstance.getAccount("87654321");
        Assertions.assertNotNull(createdAccount);
        Assertions.assertTrue(createdAccount instanceof Savings);
        Assertions.assertEquals(2.0, createdAccount.getApr(), 0.0001);
    }
}


