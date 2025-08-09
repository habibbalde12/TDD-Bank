import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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
        assertTrue(validator.validate(cmd));
    }

    @Test
    void invalid_account_type_fails() {
        String[] cmd = {"create", "cd", "12345678", "0.5"};
        assertFalse(validator.validate(cmd));
    }

    @Test
    void invalid_account_number_fails() {
        String[] cmd = {"create", "savings", "abc45678", "0.5"};
        assertFalse(validator.validate(cmd));
    }

    @Test
    void invalid_interest_rate_fails() {
        String[] cmd = {"create", "savings", "12345678", "1.2"};
        assertFalse(validator.validate(cmd));
    }

    @Test
    void duplicate_account_fails() {
        bank.addAccount(new Savings("12345678", 0.5));
        String[] cmd = {"create", "savings", "12345678", "0.5"};
        assertFalse(validator.validate(cmd));
    }

    @Test
    void wrong_token_count_fails() {
        String[] cmd = {"create", "savings", "12345678"};
        assertFalse(validator.validate(cmd));
    }
}



