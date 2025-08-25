package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PassTimeCommandProcessTest {
    private Bank bank;
    private PassTimeCommandProcess process;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        process = new PassTimeCommandProcess(bank);
    }

    @Test
    void supports_pass_only() {
        assertTrue(process.supports("pass"));
        assertFalse(process.supports("deposit"));
        assertFalse(process.supports("withdraw"));
        assertFalse(process.supports("create"));
        assertFalse(process.supports("transfer"));
    }

    @Test
    void process_tokens_applies_interest_to_checkings() {
        bank.addAccount(new Checkings("12345678", 0.12));
        bank.deposit("12345678", 100.0);
        process.processTokens(new String[]{"pass", "1"});
        assertEquals(101.0, bank.getAccount("12345678").getBalance(), 0.01);
    }

    @Test
    void process_tokens_applies_interest_multiple_months_savings() {
        bank.addAccount(new Savings("87654321", 0.06));
        bank.deposit("87654321", 1000.0);
        process.processTokens(new String[]{"pass", "2"});
        assertEquals(1010.0, bank.getAccount("87654321").getBalance(), 0.01);
    }
}
