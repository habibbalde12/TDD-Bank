package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TransferCommandValidatorTest {
    private Bank bank;
    private TransferCommandValidator transferValidator;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        bank.addAccount(new Checkings("12345678", 1.0));
        bank.addAccount(new Savings("87654321", 1.0));
        transferValidator = new TransferCommandValidator(bank);
    }

    @Test
    void supports_transfer_only() {
        assertTrue(transferValidator.supports("transfer"));
        assertFalse(transferValidator.supports("create"));
        assertFalse(transferValidator.supports("deposit"));
        assertFalse(transferValidator.supports("withdraw"));
        assertFalse(transferValidator.supports("pass"));
    }

    @Test
    void valid_transfer_between_checkings_and_savings() {
        String[] tokens = {"transfer", "12345678", "87654321", "200"};
        assertTrue(transferValidator.validate(tokens));
    }

    @Test
    void invalid_when_wrong_token_count() {
        String[] tokens = {"transfer", "12345678", "87654321"};
        assertFalse(transferValidator.validate(tokens));
    }

    @Test
    void invalid_when_same_account_ids() {
        String[] tokens = {"transfer", "12345678", "12345678", "50"};
        assertFalse(transferValidator.validate(tokens));
    }

    @Test
    void invalid_when_from_account_missing() {
        String[] tokens = {"transfer", "99999999", "87654321", "100"};
        assertFalse(transferValidator.validate(tokens));
    }

    @Test
    void invalid_when_to_account_missing() {
        String[] tokens = {"transfer", "12345678", "11111111", "100"};
        assertFalse(transferValidator.validate(tokens));
    }

    @Test
    void invalid_when_amount_is_zero() {
        String[] tokens = {"transfer", "12345678", "87654321", "0"};
        assertFalse(transferValidator.validate(tokens));
    }

    @Test
    void invalid_when_amount_is_negative() {
        String[] tokens = {"transfer", "12345678", "87654321", "-50"};
        assertFalse(transferValidator.validate(tokens));
    }

    @Test
    void invalid_when_amount_in_scientific_notation() {
        String[] tokens = {"transfer", "12345678", "87654321", "1e3"};
        assertFalse(transferValidator.validate(tokens));
    }

    @Test
    void invalid_when_cd_involved() {
        bank.addAccount(new CD("22223333", 1.0, 5000.0));
        String[] tokens1 = {"transfer", "22223333", "87654321", "100"};
        String[] tokens2 = {"transfer", "12345678", "22223333", "100"};
        assertFalse(transferValidator.validate(tokens1));
        assertFalse(transferValidator.validate(tokens2));
    }
    @Test
    void invalid_when_tokens_null() {
        assertFalse(transferValidator.validate((String[]) null));
    }
}
