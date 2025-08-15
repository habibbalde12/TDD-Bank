import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommandProcessTest {
    private Bank bankInstance;
    private CommandProcess commandProcessInstance;

    @BeforeEach
    void setUp() {
        bankInstance = new Bank();
        commandProcessInstance = new CommandProcess(bankInstance);
    }

    @Test
    void supports_create_and_deposit() {
        assertTrue(commandProcessInstance.supports("create"));
        assertTrue(commandProcessInstance.supports("deposit"));
    }

    @Test
    void process_create_checking_adds_account() {
        commandProcessInstance.process("create checking 12345678 1.0");
        Account createdAccount = bankInstance.getAccount("12345678");
        assertNotNull(createdAccount);
        assertTrue(createdAccount instanceof Checkings);
        assertEquals("12345678", createdAccount.getId());
    }

    @Test
    void process_create_savings_adds_account() {
        commandProcessInstance.process("create savings 87654321 0.8");
        Account createdAccount = bankInstance.getAccount("87654321");
        assertNotNull(createdAccount);
        assertTrue(createdAccount instanceof Savings);
    }

    @Test
    void process_deposit_updates_balance() {
        commandProcessInstance.process("create savings 11112222 0.5");
        commandProcessInstance.process("deposit 11112222 250");
        Account updatedAccount = bankInstance.getAccount("11112222");
        assertEquals(250.0, updatedAccount.getBalance(), 0.0001);
    }

    @Test
    void process_unknown_command_does_nothing() {
        commandProcessInstance.process("depositt 11112222 10");
        Account possiblyCreatedAccount = bankInstance.getAccount("11112222");
        assertNull(possiblyCreatedAccount);
    }
}
