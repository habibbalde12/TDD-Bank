package banking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CreateValidatorTest {

    private Bank bank;
    private CreateValidator validator;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        validator = new CreateValidator(bank);
    }

    @Test
    void valid_create_command() {
        String[] cmd = {"create", "savings", "12345678", "0.5"};
        Assertions.assertTrue(validator.validate(cmd));
    }

    @Test
    void invalid_account_type_fails() {
        String[] cmd = {"create", "cd", "12345678", "0.5"};
        Assertions.assertFalse(validator.validate(cmd));
    }

    @Test
    void invalid_account_number_fails() {
        String[] cmd = {"create", "savings", "abc45678", "0.5"};
        Assertions.assertFalse(validator.validate(cmd));
    }

    @Test
    void duplicate_account_fails() {
        bank.addAccount(new Savings("12345678", 0.5));
        String[] cmd = {"create", "savings", "12345678", "0.5"};
        Assertions.assertFalse(validator.validate(cmd));
    }

    @Test
    void wrong_token_count_fails() {
        String[] cmd = {"create", "savings", "12345678"};
        Assertions.assertFalse(validator.validate(cmd));
    }
}





