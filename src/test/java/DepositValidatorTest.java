import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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
}

