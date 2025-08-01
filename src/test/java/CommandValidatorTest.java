import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommandValidatorTest {

    private Bank bank;
    private CommandValidator validator;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        validator = new CommandValidator(bank);
    }

    @Test
    void validCreateCommand() {
        assertTrue(validator.validate("create savings 12345678 0.4"));
    }

    @Test
    void validDepositCommand() {
        bank.addAccount(new Savings("12345678", 0.4));
        assertTrue(validator.validate("deposit 12345678 200"));
    }

    @Test
    void unknownCommandFails() {
        assertFalse(validator.validate("delete 12345678"));
    }

    @Test
    void depositToNonexistentAccountFails() {
        assertFalse(validator.validate("deposit 12345678 100"));
    }

    @Test
    void duplicateCreateFails() {
        bank.addAccount(new Savings("12345678", 0.3));
        assertFalse(validator.validate("create savings 12345678 0.3"));
    }

    @Test
    void emptyCommandFails() {
        assertFalse(validator.validate(""));
    }

    @Test
    void nullCommandFails() {
        assertFalse(validator.validate(null));
    }
}

