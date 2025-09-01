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
        Bank bank = new Bank();
        Savings s = new Savings("22223333", 0.06);
        Checkings c = new Checkings("11112222", 0.01);
        bank.addAccount(s); bank.addAccount(c);
        bank.deposit("22223333", 300.0);
        bank.transfer("22223333", "11112222", 50.0, "transfer 22223333 11112222 50");
        bank.transfer("22223333", "11112222", 50.0, "transfer 22223333 11112222 50");
        assertEquals(250.0, s.getBalance(), 1e-9);
        bank.passTime(1);
        bank.withdraw("22223333", 25.0);
        assertTrue(s.getBalance() < 250.0 + (250.0 * (0.06/12.0)) + 1e-6);
    }

}



