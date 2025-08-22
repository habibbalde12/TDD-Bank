package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TransferCommandProcessTest {
    private Bank bankInstance;
    private TransferCommandProcess transferProcess;

    @BeforeEach
    void setUp() {
        bankInstance = new Bank();
        transferProcess = new TransferCommandProcess(bankInstance);
    }

    @Test
    void supports_transfer_only() {
        assertTrue(transferProcess.supports("transfer"));
        assertFalse(transferProcess.supports("create"));
        assertFalse(transferProcess.supports("deposit"));
        assertFalse(transferProcess.supports("withdraw"));
        assertFalse(transferProcess.supports("pass"));
    }

    @Test
    void process_tokens_transfers_amount_checkings_to_savings() {
        bankInstance.addAccount(new Checkings("12345678", 1.0));
        bankInstance.addAccount(new Savings("87654321", 1.0));
        bankInstance.deposit("12345678", 500.0);
        bankInstance.deposit("87654321", 100.0);

        String[] tokens = {"transfer", "12345678", "87654321", "200.0"};
        transferProcess.processTokens(tokens);

        assertEquals(300.0, bankInstance.getAccount("12345678").getBalance(), 0.0001);
        assertEquals(300.0, bankInstance.getAccount("87654321").getBalance(), 0.0001);
    }

    @Test
    void process_tokens_multiple_transfers_accumulate() {
        bankInstance.addAccount(new Checkings("12345678", 1.0));
        bankInstance.addAccount(new Savings("87654321", 1.0));
        bankInstance.deposit("12345678", 600.0);
        bankInstance.deposit("87654321", 50.0);

        transferProcess.processTokens(new String[]{"transfer", "12345678", "87654321", "100.0"});
        transferProcess.processTokens(new String[]{"transfer", "12345678", "87654321", "150.0"});

        assertEquals(350.0, bankInstance.getAccount("12345678").getBalance(), 0.0001);
        assertEquals(300.0, bankInstance.getAccount("87654321").getBalance(), 0.0001);
    }
}
