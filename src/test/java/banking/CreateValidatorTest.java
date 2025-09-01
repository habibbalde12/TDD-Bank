package banking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    @Test
    void validates_id_is_eight_digits_and_apr_0_to_10() {
        Bank bank = new Bank();
        CreateValidator v = new CreateValidator(bank);
        assertFalse(v.validate(new String[]{"create","checking","1234567","1"}));
        assertFalse(v.validate(new String[]{"create","checking","123456789","1"}));
        assertFalse(v.validate(new String[]{"create","checking","12345678","-1"}));
        assertFalse(v.validate(new String[]{"create","checking","12345678","10.01"}));
        assertTrue(v.validate(new String[]{"create","checking","12345678","0.6"}));
    }

}





