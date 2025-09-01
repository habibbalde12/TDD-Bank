package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MasterControlEndToEndTest {
    private Bank bank;
    private MasterControl mc;
    private Output printer;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        mc = new MasterControl(bank);
        printer = new Output();
    }

    @Test
    void full_flow_no_invalids() {
        List<String> input = new ArrayList<>();
        input.add("create checking 12345678 0.5");
        input.add("create savings 87654321 0.6");
        input.add("deposit 12345678 500");
        input.add("deposit 87654321 800");
        input.add("withdraw 12345678 200");
        input.add("transfer 87654321 12345678 150");
        input.add("pass 2");

        List<String> out = mc.start(input);

        List<String> state = printer.print(bank);
        assertTrue(state.stream().anyMatch(s -> s.startsWith("Checking 12345678")));
        assertTrue(state.stream().anyMatch(s -> s.startsWith("Savings 87654321")));
    }
}


