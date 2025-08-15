import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DepositCommandProcessTest {
    private Bank bankInstance;
    private DepositCommandProcess depositCommandProcessInstance;

    @BeforeEach
    void setUp() {
        bankInstance = new Bank();
        bankInstance.addAccount(new Checkings("12345678", 1.5));
        depositCommandProcessInstance = new DepositCommandProcess(bankInstance);
    }

    @Test
    void supports_deposit_only() {
        assertTrue(depositCommandProcessInstance.supports("deposit"));
        assertFalse(depositCommandProcessInstance.supports("create"));
    }

    @Test
    void process_tokens_deposits_amount() {
        String[] tokens = {"deposit", "12345678", "500.0"};
        depositCommandProcessInstance.processTokens(tokens);
        Account updatedAccount = bankInstance.getAccount("12345678");
        assertEquals(500.0, updatedAccount.getBalance(), 0.0001);
    }
}
