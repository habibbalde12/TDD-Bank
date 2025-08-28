package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OutputTest {
    private Bank bank;
    private Output printer;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        printer = new Output();
    }

    @Test
    void prints_two_accounts_sorted_by_id() {
        bank.addAccount(new Savings("87654321", 0.6));
        bank.addAccount(new Checkings("12345678", 0.5));
        bank.deposit("12345678", 200.0);
        bank.deposit("87654321", 300.0);
        List<String> lines = printer.print(bank);
        assertEquals(2, lines.size());
        assertTrue(lines.get(0).startsWith("Checking 12345678"));
        assertTrue(lines.get(1).startsWith("Savings 87654321"));
        assertTrue(lines.get(0).contains("200.00"));
        assertTrue(lines.get(1).contains("300.00"));
    }
}
