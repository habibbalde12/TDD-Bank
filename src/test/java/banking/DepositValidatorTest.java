package banking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DepositValidatorTest {

    private Bank bank;
    private DepositValidator validator;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        validator = new DepositValidator(bank);
    }

    @Test
    void valid_deposit_command() {
        bank.addAccount(new Savings("12345678", 0.5));
        String[] cmd = {"deposit", "12345678", "100"};
        assertTrue(validator.validate(cmd));
    }

    @Test
    void nonexisting_account_fails() {
        String[] cmd = {"deposit", "12345678", "100"};
        assertFalse(validator.validate(cmd));
    }

    @Test
    void negative_amount_fails() {
        bank.addAccount(new Savings("12345678", 0.5));
        String[] cmd = {"deposit", "12345678", "-50"};
        assertFalse(validator.validate(cmd));
    }

    @Test
    void invalid_amount_format_fails() {
        bank.addAccount(new Savings("12345678", 0.5));
        String[] cmd = {"deposit", "12345678", "ten"};
        assertFalse(validator.validate(cmd));
    }

    @Test
    void account_number_with_letters_fails() {
        String[] cmd = {"deposit", "12ab5678", "50"};
        assertFalse(validator.validate(cmd));
    }

    @Test
    void wrong_token_count_fails() {
        String[] cmd = {"deposit", "12345678"};
        assertFalse(validator.validate(cmd));
    }
   @Test
    void checking_1000_cap_savings_2500_cap() {
        Bank bank = new Bank();
        bank.addAccount(new Checkings("11112222", 0.01));
        bank.addAccount(new Savings("22223333", 0.01));
        DepositValidator dv = new DepositValidator(bank);
        assertTrue(dv.validate(new String[]{"deposit","11112222","1000"}));
        assertFalse(dv.validate(new String[]{"deposit","11112222","1000.01"}));
        assertTrue(dv.validate(new String[]{"deposit","22223333","2500"}));
        assertFalse(dv.validate(new String[]{"deposit","22223333","2500.01"}));
    }

}



