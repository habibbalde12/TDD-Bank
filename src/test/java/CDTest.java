import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CDTest {
    @Test
    void cd_starts_with_specified_balance() {
        CD cd = new CD("12345678", 5.4, 1742.89);
        assertEquals(1742.89, cd.getBalance());
    }

    @Test
    void cd_apr_is_set_correctly() {
        CD cd = new CD("11111111", 5.4, 1742.89);
        assertEquals(5.4, cd.getApr());
    }
}