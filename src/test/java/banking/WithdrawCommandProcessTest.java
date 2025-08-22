// WithdrawCommandProcessTest.java
package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WithdrawCommandProcessTest {
    private Bank bankInstance;
    private WithdrawCommandProcess withdrawCommandProcessInstance;

    @BeforeEach
    void setUp() {
        bankInstance = new Bank();
        withdrawCommandProcessInstance = new WithdrawCommandProcess(bankInstance);
    }

    @Test
    void supports_withdraw_only() {
        assertTrue(withdrawCommandProcessInstance.supports("withdraw"));
        assertFalse(withdrawCommandProcessInstance.supports("deposit"));
        assertFalse(withdrawCommandProcessInstance.supports("create"));
        assertFalse(withdrawCommandProcessInstance.supports("transfer"));
        assertFalse(withdrawCommandProcessInstance.supports("pass"));
    }

    @Test
    void process_tokens_withdraws_amount_from_checkings() {
        bankInstance.addAccount(new Checkings("12345678", 1.5));
        bankInstance.deposit("12345678", 500.0);
        String[] tokens = {"withdraw", "12345678", "200.0"};
        withdrawCommandProcessInstance.processTokens(tokens);
        Account updatedAccount = bankInstance.getAccount("12345678");
        assertEquals(300.0, updatedAccount.getBalance(), 0.0001);
    }

    @Test
    void process_tokens_withdraws_amount_from_savings() {
        bankInstance.addAccount(new Savings("12345678", 1.0));
        bankInstance.deposit("12345678", 400.0);
        String[] tokens = {"withdraw", "12345678", "150.0"};
        withdrawCommandProcessInstance.processTokens(tokens);
        Account updatedAccount = bankInstance.getAccount("12345678");
        assertEquals(250.0, updatedAccount.getBalance(), 0.0001);
    }

    @Test
    void process_tokens_overdraft_sets_to_zero_when_amount_exceeds_balance() {
        bankInstance.addAccount(new Checkings("12345678", 1.0));
        bankInstance.deposit("12345678", 150.0);
        String[] tokens = {"withdraw", "12345678", "999.0"};
        withdrawCommandProcessInstance.processTokens(tokens);
        Account updatedAccount = bankInstance.getAccount("12345678");
        assertEquals(0.0, updatedAccount.getBalance(), 0.0001);
    }

    @Test
    void process_tokens_zero_amount_no_change() {
        bankInstance.addAccount(new Savings("12345678", 1.0));
        bankInstance.deposit("12345678", 250.0);
        String[] tokens = {"withdraw", "12345678", "0.0"};
        withdrawCommandProcessInstance.processTokens(tokens);
        Account updatedAccount = bankInstance.getAccount("12345678");
        assertEquals(250.0, updatedAccount.getBalance(), 0.0001);
    }
}





