package banking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountTest {
    @Test
    void deposit_increases_balance() {
        Account account = new Checkings("12345678", 9.1);
        account.deposit(45.67);
        Assertions.assertEquals(45.67, account.getBalance());
    }

    @Test
    void withdraw_decreases_balance() {
        Account account = new Checkings("12345678", 9.1);
        account.deposit(100);
        account.withdraw(23.44);
        Assertions.assertEquals(76.56, account.getBalance());
    }

    @Test
    void withdraw_more_than_balance_sets_to_zero() {
        Account account = new Checkings("00000000", 9.1);
        account.deposit(50);
        account.withdraw(75);
        Assertions.assertEquals(0, account.getBalance());
    }

    @Test
    void deposit_twice_works() {
        Account account = new Checkings("12345678", 9.1);
        account.deposit(10);
        account.deposit(88.88);
        Assertions.assertEquals(98.88, account.getBalance());
    }

    @Test
    void withdraw_twice_works() {
        Account account = new Checkings("11111111", 9.1);
        account.deposit(100);
        account.withdraw(20);
        account.withdraw(30);
        Assertions.assertEquals(50, account.getBalance());
    }
    @Test
    void onMonthTick_resets_savings_withdrawals() {
        Bank bank = new Bank();
        Savings s = new Savings("87654321", 0.06);
        bank.addAccount(s);
        bank.deposit("87654321", 200.0);
        bank.withdraw("87654321", 50.0);
        bank.withdraw("87654321", 50.0);
        assertEquals(150.0, s.getBalance(), 1e-9);
        bank.passTime(1);
        bank.withdraw("87654321", 25.0);
        assertEquals(150.0 + (150.0 * (0.06/12.0)) - 25.0, s.getBalance(), 1e-6);
    }

    @Test
    void transfer_history_echoed_once_per_side() {
        Bank bank = new Bank();
        Checkings a = new Checkings("11112222", 0.01);
        Savings b = new Savings("22223333", 0.01);
        bank.addAccount(a); bank.addAccount(b);
        bank.deposit("11112222", 200.0);
        String t = "transfer 11112222 22223333 50";
        bank.transfer("11112222", "22223333", 50.0, t);
        long fromCount = bank.getHistory("11112222").stream().filter(t::equals).count();
        long toCount   = bank.getHistory("22223333").stream().filter(t::equals).count();
        assertEquals(1, fromCount);
        assertEquals(1, toCount);
    }

}


