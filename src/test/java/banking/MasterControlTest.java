package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MasterControlTest {
    MasterControl masterControl;
    List<String> input;

    @BeforeEach
    void setUp() {
        input = new ArrayList<>();
        Bank bank = new Bank();
        CommandValidator createValidator = new CreateValidator(bank);
        CommandValidator depositValidator = new DepositValidator(bank);
        CommandValidator withdrawValidator = new WithdrawCommandValidator(bank);
        CommandValidator transferValidator = new TransferCommandValidator(bank);
        CommandProcess commandProcess = new CommandProcess(bank);
        CommandStorer commandStorer = new CommandStorer();
        masterControl = new MasterControl(createValidator, depositValidator, withdrawValidator, transferValidator, commandProcess, commandStorer);
    }

    private void assertSingleInvalid(String command, List<String> actual) {
        assertEquals(1, actual.size());
        assertEquals(command, actual.get(0));
    }

    @Test
    void transfer_typo_is_invalid() {
        input.add("tranfer 12345678 87654321 100");
        List<String> actual = masterControl.start(input);
        assertSingleInvalid("tranfer 12345678 87654321 100", actual);
    }

    @Test
    void valid_transfer_is_not_marked_invalid() {
        input.add("create checking 12345678 0.5");
        input.add("create savings 87654321 0.6");
        input.add("transfer 12345678 87654321 200");
        List<String> actual = masterControl.start(input);
        assertEquals(0, actual.size());
    }

    @Test
    void transfer_missing_account_is_invalid() {
        input.add("create checking 12345678 0.5");
        input.add("transfer 12345678 87654321 200");
        List<String> actual = masterControl.start(input);
        assertSingleInvalid("transfer 12345678 87654321 200", actual);
    }
}



