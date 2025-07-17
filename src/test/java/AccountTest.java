import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountTest {
    @Test
    void deposit_increases_balance() {
        Account account = new Checkings("12345678", 9.1);
        account.deposit(45.67);
        assertEquals(45.67, account.getBalance());
    }

    @Test
    void withdraw_decreases_balance() {
        Account account = new Checkings("12345678", 9.1);
        account.deposit(100);
        account.withdraw(23.44);
        assertEquals(76.56, account.getBalance());
    }

    @Test
    void withdraw_more_than_balance_sets_to_zero() {
        Account account = new Checkings("00000000", 9.1);
        account.deposit(50);
        account.withdraw(75);
        assertEquals(0, account.getBalance());
    }

    @Test
    void deposit_twice_works() {
        Account account = new Checkings("12345678", 9.1);
        account.deposit(10);
        account.deposit(88.88);
        assertEquals(98.88, account.getBalance());
    }

    @Test
    void withdraw_twice_works() {
        Account account = new Checkings("11111111", 9.1);
        account.deposit(100);
        account.withdraw(20);
        account.withdraw(30);
        assertEquals(50, account.getBalance());
    }
}
