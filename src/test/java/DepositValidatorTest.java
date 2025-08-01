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
    void validDepositCommand() {
        bank.addAccount(new Savings("12345678", 0.5));
        String[] cmd = {"deposit", "12345678", "100"};
        assertTrue(validator.validate(cmd));
    }

    @Test
    void nonExistingAccountFails() {
        String[] cmd = {"deposit", "12345678", "100"};
        assertFalse(validator.validate(cmd));
    }

    @Test
    void negativeAmountFails() {
        bank.addAccount(new Savings("12345678", 0.5));
        String[] cmd = {"deposit", "12345678", "-50"};
        assertFalse(validator.validate(cmd));
    }

    @Test
    void invalidAmountFormatFails() {
        bank.addAccount(new Savings("12345678", 0.5));
        String[] cmd = {"deposit", "12345678", "ten"};
        assertFalse(validator.validate(cmd));
    }

    @Test
    void accountNumberWithLettersFails() {
        String[] cmd = {"deposit", "12ab5678", "50"};
        assertFalse(validator.validate(cmd));
    }

    @Test
    void wrongTokenCountFails() {
        String[] cmd = {"deposit", "12345678"};
        assertFalse(validator.validate(cmd));
    }
}

