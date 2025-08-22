package banking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckingsTest {
    @Test
    void checkings_starts_with_zero_balance() {
        Checkings checkings = new Checkings("12345678", 2.7);
        Assertions.assertEquals(0, checkings.getBalance());
    }

    @Test
    void checkings_apr_is_set_correctly() {
        Checkings checkings = new Checkings("12345678", 2.7);
        Assertions.assertEquals(2.7, checkings.getApr());
    }
}

