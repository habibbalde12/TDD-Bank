package banking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SavingsTest {
    @Test
    void savings_starts_with_zero_balance() {
        Savings savings = new Savings("12345678", 6.6);
        Assertions.assertEquals(0, savings.getBalance());
    }

    @Test
    void savings_apr_is_set_correctly() {
        Savings savings = new Savings("11111111", 6.6);
        Assertions.assertEquals(6.6, savings.getApr());
    }
}



