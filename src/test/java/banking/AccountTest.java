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
        Bank bankInstance = new Bank();
        Savings savingsAccount = new Savings("87654321", 0.06);
        bankInstance.addAccount(savingsAccount);
        bankInstance.deposit("87654321", 200.0);
        bankInstance.withdraw("87654321", 50.0);
        bankInstance.withdraw("87654321", 50.0);
        assertEquals(150.0, savingsAccount.getBalance(), 1e-9);
        bankInstance.passTime(1);
        bankInstance.withdraw("87654321", 25.0);
        assertEquals(150.0 + (150.0 * (0.06 / 12.0)) - 25.0, savingsAccount.getBalance(), 1e-6);
    }

    @Test
    void transfer_history_echoed_once_per_side() {
        Bank bankInstance = new Bank();
        Checkings checkingAccount = new Checkings("11112222", 0.01);
        Savings savingsAccount = new Savings("22223333", 0.01);
        bankInstance.addAccount(checkingAccount);
        bankInstance.addAccount(savingsAccount);
        bankInstance.deposit("11112222", 200.0);
        String transferText = "transfer 11112222 22223333 50";
        bankInstance.transfer("11112222", "22223333", 50.0, transferText);
        long fromCountOccurrences = bankInstance.getHistory("11112222").stream().filter(transferText::equals).count();
        long toCountOccurrences = bankInstance.getHistory("22223333").stream().filter(transferText::equals).count();
        assertEquals(1, fromCountOccurrences);
        assertEquals(1, toCountOccurrences);
    }


}


