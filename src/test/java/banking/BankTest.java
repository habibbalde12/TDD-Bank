package banking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BankTest {
    @Test
    void bank_starts_with_no_accounts() {
        Bank bank = new Bank();
        Assertions.assertEquals(0, bank.getAccountCount());
    }

    @Test
    void add_one_account_to_bank() {
        Bank bank = new Bank();
        bank.addAccount(new Checkings("83472619", 3.2));
        Assertions.assertEquals(1, bank.getAccountCount());
    }

    @Test
    void add_two_accounts_to_bank() {
        Bank bank = new Bank();
        bank.addAccount(new Checkings("83472619", 3.2));
        bank.addAccount(new Savings("53819264", 6.6));
        Assertions.assertEquals(2, bank.getAccountCount());
    }

    @Test
    void retrieve_account_by_id() {
        Bank bank = new Bank();
        Checkings checkings = new Checkings("83472619", 3.2);
        bank.addAccount(checkings);
        Assertions.assertEquals(checkings, bank.getAccount("83472619"));
    }

    @Test
    void deposit_by_id_increases_correct_account() {
        Bank bank = new Bank();
        Checkings checkings = new Checkings("83472619", 9.1);
        bank.addAccount(checkings);
        bank.deposit("83472619", 88.88);
        Assertions.assertEquals(88.88, checkings.getBalance());
    }

    @Test
    void withdraw_by_id_decreases_correct_account() {
        Bank bank = new Bank();
        Checkings checkings = new Checkings("83472619", 9.1);
        checkings.deposit(100.00);
        bank.addAccount(checkings);
        bank.withdraw("83472619", 23.44);
        Assertions.assertEquals(76.56, checkings.getBalance());
    }

    @Test
    void deposit_twice_through_bank() {
        Bank bank = new Bank();
        Checkings checkings = new Checkings("83472619", 9.1);
        bank.addAccount(checkings);
        bank.deposit("83472619", 45.67);
        bank.deposit("83472619", 22.33);
        Assertions.assertEquals(68.00, checkings.getBalance(), 0.001);
    }

    @Test
    void withdraw_twice_through_bank() {
        Bank bank = new Bank();
        Checkings checkings = new Checkings("83472619", 9.1);
        checkings.deposit(100);
        bank.addAccount(checkings);
        bank.withdraw("83472619", 25);
        bank.withdraw("83472619", 30);
        Assertions.assertEquals(45, checkings.getBalance());
    }

    @Test
    void transfer_decreases_from_and_increases_to() {
        Bank bank = new Bank();
        bank.addAccount(new Checkings("12345678", 1.0));
        bank.addAccount(new Savings("87654321", 1.0));
        bank.deposit("12345678", 500.0);
        bank.deposit("87654321", 200.0);

        bank.transfer("12345678", "87654321", 150.0);

        assertEquals(350.0, bank.getAccount("12345678").getBalance(), 0.0001);
        assertEquals(350.0, bank.getAccount("87654321").getBalance(), 0.0001);
    }

    @Test
    void transfer_more_than_available_moves_only_available() {
        Bank bank = new Bank();
        bank.addAccount(new Checkings("12345678", 1.0));
        bank.addAccount(new Savings("87654321", 1.0));
        bank.deposit("12345678", 80.0);
        bank.deposit("87654321", 20.0);

        bank.transfer("12345678", "87654321", 200.0);

        assertEquals(0.0, bank.getAccount("12345678").getBalance(), 0.0001);
        assertEquals(100.0, bank.getAccount("87654321").getBalance(), 0.0001);
    }

    @Test
    void transfer_same_account_no_change() {
        Bank bank = new Bank();
        bank.addAccount(new Checkings("12345678", 1.0));
        bank.deposit("12345678", 300.0);

        bank.transfer("12345678", "12345678", 50.0);

        assertEquals(300.0, bank.getAccount("12345678").getBalance(), 0.0001);
    }

    @Test
    void transfer_missing_from_account_no_change() {
        Bank bank = new Bank();
        bank.addAccount(new Savings("87654321", 1.0));
        bank.deposit("87654321", 100.0);

        bank.transfer("12345678", "87654321", 50.0);

        assertEquals(100.0, bank.getAccount("87654321").getBalance(), 0.0001);
    }

    @Test
    void transfer_missing_to_account_no_change() {
        Bank bank = new Bank();
        bank.addAccount(new Checkings("12345678", 1.0));
        bank.deposit("12345678", 120.0);

        bank.transfer("12345678", "87654321", 50.0);

        assertEquals(120.0, bank.getAccount("12345678").getBalance(), 0.0001);
    }

    @Test
    void transfer_zero_amount_no_change() {
        Bank bank = new Bank();
        bank.addAccount(new Checkings("12345678", 1.0));
        bank.addAccount(new Savings("87654321", 1.0));
        bank.deposit("12345678", 200.0);
        bank.deposit("87654321", 50.0);

        bank.transfer("12345678", "87654321", 0.0);

        assertEquals(200.0, bank.getAccount("12345678").getBalance(), 0.0001);
        assertEquals(50.0, bank.getAccount("87654321").getBalance(), 0.0001);
    }

    @Test
    void transfer_negative_amount_no_change() {
        Bank bank = new Bank();
        bank.addAccount(new Checkings("12345678", 1.0));
        bank.addAccount(new Savings("87654321", 1.0));
        bank.deposit("12345678", 200.0);
        bank.deposit("87654321", 50.0);

        bank.transfer("12345678", "87654321", -25.0);

        assertEquals(200.0, bank.getAccount("12345678").getBalance(), 0.0001);
        assertEquals(50.0, bank.getAccount("87654321").getBalance(), 0.0001);
    }


    @Test
    void pass_time_zero_months_no_change() {
        Bank bank = new Bank();
        bank.addAccount(new Savings("87654321", 6.0));
        bank.deposit("87654321", 250.0);
        bank.passTime(0);
        assertEquals(250.0, bank.getAccount("87654321").getBalance(), 0.0001);
    }

    @Test
    void pass_time_negative_months_no_change() {
        Bank bank = new Bank();
        bank.addAccount(new Checkings("12345678", 12.0));
        bank.deposit("12345678", 300.0);
        bank.passTime(-3);
        assertEquals(300.0, bank.getAccount("12345678").getBalance(), 0.0001);
    }
    @org.junit.jupiter.api.Test
    void passTime_removes_zero_balance_accounts_before_interest() {
        Bank bank = new Bank();
        bank.addAccount(new Checkings("12345678", 0.12));
        bank.passTime(1);
        assertNull(bank.getAccount("12345678"));
    }

    @Test
    void passTime_simple_interest_HALF_UP_multiple_months() {
        Bank bank = new Bank();
        bank.addAccount(new Savings("87654321", 0.06));
        bank.deposit("87654321", 1000.00);
        bank.passTime(2);
        assertEquals(1010.00, bank.getAccount("87654321").getBalance(), 1e-9);
    }

    @Test
    void recreate_same_id_after_closure_allowed() {
        Bank bank = new Bank();
        bank.addAccount(new Checkings("11112222", 0.01));
        bank.deposit("11112222", 1.0);
        bank.withdraw("11112222", 1.0);
        bank.passTime(1);
        assertNull(bank.getAccount("11112222"));
        bank.addAccount(new Savings("11112222", 0.02));
        assertNotNull(bank.getAccount("11112222"));
        assertTrue(bank.getHistory("11112222").isEmpty());
    }


}
