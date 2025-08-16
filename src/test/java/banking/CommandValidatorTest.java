package banking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandValidatorTest {

    private Bank bank;
    private CommandValidator validator;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        validator = new CommandValidator(bank);
    }

    @Test
    void valid_create_command() {
        Assertions.assertTrue(validator.validate("create savings 12345678 0.4"));
    }

    @Test
    void valid_deposit_command() {
        bank.addAccount(new Savings("12345678", 0.4));
        Assertions.assertTrue(validator.validate("deposit 12345678 200"));
    }

    @Test
    void unknown_command_fails() {
        Assertions.assertFalse(validator.validate("delete 12345678"));
    }

    @Test
    void deposit_to_nonexistent_account_fails() {
        Assertions.assertFalse(validator.validate("deposit 12345678 100"));
    }

    @Test
    void duplicate_create_fails() {
        bank.addAccount(new Savings("12345678", 0.3));
        Assertions.assertFalse(validator.validate("create savings 12345678 0.3"));
    }

    @Test
    void empty_command_fails() {
        Assertions.assertFalse(validator.validate(""));
    }

    @Test
    void null_command_fails() {
        Assertions.assertFalse(validator.validate((String) null));
    }
}




