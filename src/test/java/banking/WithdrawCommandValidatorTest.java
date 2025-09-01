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
    @Test
    void validate_string_null_and_blank_rejected_withdraw() {
        Bank bankInstanceLocal = new Bank();
        bankInstanceLocal.addAccount(new Checkings("12345678", 0.01));
        WithdrawCommandValidator withdrawValidatorLocal = new WithdrawCommandValidator(bankInstanceLocal);
        assertFalse(withdrawValidatorLocal.validate((String) null));
        assertFalse(withdrawValidatorLocal.validate("  "));
    }

    @Test
    void dot_amount_and_signed_amount_invalid() {
        Bank bankInstanceLocal = new Bank();
        bankInstanceLocal.addAccount(new Checkings("12345678", 0.01));
        WithdrawCommandValidator withdrawValidatorLocal = new WithdrawCommandValidator(bankInstanceLocal);
        assertFalse(withdrawValidatorLocal.validate("withdraw 12345678 ."));
        assertFalse(withdrawValidatorLocal.validate("withdraw 12345678 -0.01"));
        assertFalse(withdrawValidatorLocal.validate("withdraw 12345678 +5"));
    }

    @Test
    void invalid_when_command_type_not_supported_in_validate_array() {
        Bank bankInstanceLocal = new Bank();
        bankInstanceLocal.addAccount(new Checkings("12345678", 0.01));
        WithdrawCommandValidator withdrawValidatorLocal = new WithdrawCommandValidator(bankInstanceLocal);
        assertFalse(withdrawValidatorLocal.validate(new String[]{"withdrew","12345678","10"}));
    }

    @Test
    void invalid_when_account_is_cd() {
        Bank bankInstanceLocal = new Bank();
        bankInstanceLocal.addAccount(new CD("23456789", 0.02, 2000.0));
        WithdrawCommandValidator withdrawValidatorLocal = new WithdrawCommandValidator(bankInstanceLocal);
        assertFalse(withdrawValidatorLocal.validate(new String[]{"withdraw","23456789","10"}));
    }

    @Test
    void checkings_boundary_400_valid_401_invalid() {
        Bank bankInstanceLocal = new Bank();
        bankInstanceLocal.addAccount(new Checkings("12345678", 0.01));
        WithdrawCommandValidator withdrawValidatorLocal = new WithdrawCommandValidator(bankInstanceLocal);
        assertTrue(withdrawValidatorLocal.validate(new String[]{"withdraw","12345678","400"}));
        assertFalse(withdrawValidatorLocal.validate(new String[]{"withdraw","12345678","401"}));
    }

    @Test
    void savings_boundary_1000_valid_1000_01_invalid() {
        Bank bankInstanceLocal = new Bank();
        bankInstanceLocal.addAccount(new Savings("87654321", 0.01));
        WithdrawCommandValidator withdrawValidatorLocal = new WithdrawCommandValidator(bankInstanceLocal);
        assertTrue(withdrawValidatorLocal.validate(new String[]{"withdraw","87654321","1000"}));
        assertFalse(withdrawValidatorLocal.validate(new String[]{"withdraw","87654321","1000.01"}));
    }

}





