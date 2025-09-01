package banking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SavingsTest {
    @Test
    void savings_starts_with_zero_balance() {
        Savings savings = new Savings("12345678", 6.6);
        Assertions.assertEquals(0, savings.getBalance());
    }

    @Test
    void savings_apr_is_set_correctly() {
        Savings savings = new Savings("11111111", 6.6);
        Assertions.assertEquals(6.6, savings.getApr());
    }

    @Test
    void one_withdrawal_per_month_including_transfer() {
        Bank bankInstance = new Bank();
        Savings savingsAccount = new Savings("22223333", 0.06);
        Checkings checkingsAccount = new Checkings("11112222", 0.01);
        bankInstance.addAccount(savingsAccount);
        bankInstance.addAccount(checkingsAccount);
        bankInstance.deposit("22223333", 300.0);
        bankInstance.transfer("22223333", "11112222", 50.0, "transfer 22223333 11112222 50");
        bankInstance.transfer("22223333", "11112222", 50.0, "transfer 22223333 11112222 50");
        assertEquals(250.0, savingsAccount.getBalance(), 1e-9);
        bankInstance.passTime(1);
        bankInstance.withdraw("22223333", 25.0);
        assertTrue(savingsAccount.getBalance() < 250.0 + (250.0 * (0.06 / 12.0)) + 1e-6);
    }

    @Test
    void getTypeLabel_is_Savings() {
        Savings savingsAccount = new Savings("12345678", 0.06);
        assertEquals("Savings", savingsAccount.getTypeLabel());
    }

    @Test
    void getWithdrawalsLeft_initial_is_one_and_resets_after_tick() {
        Bank bankInstance = new Bank();
        Savings savingsAccount = new Savings("12345678", 0.06);
        bankInstance.addAccount(savingsAccount);
        assertEquals(1, savingsAccount.getWithdrawalsLeft());
        bankInstance.deposit("12345678", 100.0);
        bankInstance.withdraw("12345678", 10.0);
        assertEquals(0, savingsAccount.getWithdrawalsLeft());
        savingsAccount.onMonthTick();
        assertEquals(1, savingsAccount.getWithdrawalsLeft());
    }
}



