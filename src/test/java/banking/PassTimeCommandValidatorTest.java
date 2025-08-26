package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PassTimeCommandValidatorTest {
    private Bank bank;
    private PassTimeCommandValidator validator;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        validator = new PassTimeCommandValidator(bank);
    }

    @Test
    void supports_pass_only() {
        assertTrue(validator.supports("pass"));
        assertFalse(validator.supports("deposit"));
        assertFalse(validator.supports("withdraw"));
        assertFalse(validator.supports("create"));
        assertFalse(validator.supports("transfer"));
    }

    @Test
    void valid_positive_integer_months() {
        String[] t = {"pass", "3"};
        assertTrue(validator.validate(t));
    }

    @Test
    void zero_months_invalid() {
        String[] t = {"pass", "0"};
        assertFalse(validator.validate(t));
    }

    @Test
    void negative_months_invalid() {
        String[] t = {"pass", "-2"};
        assertFalse(validator.validate(t));
    }

    @Test
    void decimal_months_invalid() {
        String[] t = {"pass", "1.5"};
        assertFalse(validator.validate(t));
    }

    @Test
    void scientific_notation_invalid() {
        String[] t = {"pass", "1e2"};
        assertFalse(validator.validate(t));
    }

    @Test
    void wrong_token_count_invalid() {
        String[] t = {"pass"};
        assertFalse(validator.validate(t));
    }
}
