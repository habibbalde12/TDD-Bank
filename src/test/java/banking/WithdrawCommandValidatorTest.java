// WithdrawCommandValidatorTest.java
package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WithdrawCommandValidatorTest {
    private Bank bankInstance;
    private WithdrawCommandValidator withdrawValidator;

    @BeforeEach
    void setUp() {
        bankInstance = new Bank();
        bankInstance.addAccount(new Checkings("12345678", 500.0));
        withdrawValidator = new WithdrawCommandValidator(bankInstance);
    }

    @Test
    void supports_withdraw_only() {
        assertTrue(withdrawValidator.supports("withdraw"));
        assertFalse(withdrawValidator.supports("deposit"));
        assertFalse(withdrawValidator.supports("create"));
    }

    @Test
    void valid_withdraw_command() {
        String[] tokens = {"withdraw", "12345678", "100.0"};
        assertTrue(withdrawValidator.validate(tokens));
    }

    @Test
    void invalid_when_account_does_not_exist() {
        String[] tokens = {"withdraw", "87654321", "50.0"};
        assertFalse(withdrawValidator.validate(tokens));
    }

    @Test
    void invalid_when_account_id_is_not_eight_digits() {
        String[] tokens = {"withdraw", "12345", "50.0"};
        assertFalse(withdrawValidator.validate(tokens));
    }

    @Test
    void invalid_when_amount_is_not_positive() {
        String[] tokens = {"withdraw", "12345678", "-10.0"};
        assertFalse(withdrawValidator.validate(tokens));
    }

    @Test
    void invalid_when_command_format_is_wrong() {
        String[] tokens = {"withdraw", "12345678"};
        assertFalse(withdrawValidator.validate(tokens));
    }
}



