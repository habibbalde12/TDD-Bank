import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankTest {
    @Test
    void bank_starts_with_no_accounts() {
        Bank bank = new Bank();
        assertEquals(0, bank.getAccountCount());
    }

    @Test
    void add_one_account_to_bank() {
        Bank bank = new Bank();
        bank.addAccount(new Checkings("83472619", 3.2));
        assertEquals(1, bank.getAccountCount());
    }

    @Test
    void add_two_accounts_to_bank() {
        Bank bank = new Bank();
        bank.addAccount(new Checkings("83472619", 3.2));
        bank.addAccount(new Savings("53819264", 6.6));
        assertEquals(2, bank.getAccountCount());
    }

    @Test
    void retrieve_account_by_id() {
        Bank bank = new Bank();
        Checkings checkings = new Checkings("83472619", 3.2);
        bank.addAccount(checkings);
        assertEquals(checkings, bank.getAccount("83472619"));
    }

    @Test
    void deposit_by_id_increases_correct_account() {
        Bank bank = new Bank();
        Checkings checkings = new Checkings("83472619", 9.1);
        bank.addAccount(checkings);
        bank.deposit("83472619", 88.88);
        assertEquals(88.88, checkings.getBalance());
    }

    @Test
    void withdraw_by_id_decreases_correct_account() {
        Bank bank = new Bank();
        Checkings checkings = new Checkings("83472619", 9.1);
        checkings.deposit(100.00);
        bank.addAccount(checkings);
        bank.withdraw("83472619", 23.44);
        assertEquals(76.56, checkings.getBalance());
    }

    @Test
    void deposit_twice_through_bank() {
        Bank bank = new Bank();
        Checkings checkings = new Checkings("83472619", 9.1);
        bank.addAccount(checkings);
        bank.deposit("83472619", 45.67);
        bank.deposit("83472619", 22.33);
        assertEquals(68.00, checkings.getBalance(), 0.001);
    }

    @Test
    void withdraw_twice_through_bank() {
        Bank bank = new Bank();
        Checkings checkings = new Checkings("83472619", 9.1);
        checkings.deposit(100);
        bank.addAccount(checkings);
        bank.withdraw("83472619", 25);
        bank.withdraw("83472619", 30);
        assertEquals(45, checkings.getBalance());
    }
}