package banking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandStorerTest {
    @Test
    void stores_and_returns_in_order() {
        CommandStorer commandStorer = new CommandStorer();
        commandStorer.storeInvalid("creat checking 12345678 1.0");
        commandStorer.storeInvalid("depositt 12345678 100");
        List<String> actualInvalidCommands = commandStorer.getInvalid();
        assertEquals(2, actualInvalidCommands.size());
        assertEquals("creat checking 12345678 1.0", actualInvalidCommands.get(0));
        assertEquals("depositt 12345678 100", actualInvalidCommands.get(1));
    }

    @Test
    void getInvalid_returns_copy() {
        CommandStorer commandStorer = new CommandStorer();
        commandStorer.storeInvalid("command_one");
        List<String> firstCopy = commandStorer.getInvalid();
        firstCopy.add("should_not_affect_internal_list");
        List<String> secondCopy = commandStorer.getInvalid();
        assertEquals(1, secondCopy.size());
        assertEquals("command_one", secondCopy.get(0));
    }

    @Test
    void clear_empties_storage() {
        CommandStorer commandStorer = new CommandStorer();
        commandStorer.storeInvalid("bad_command");
        commandStorer.clear();
        List<String> afterClear = commandStorer.getInvalid();
        Assertions.assertTrue(afterClear.isEmpty());
    }
    @Test
    void preserves_original_text_and_invalids_in_input_order() {
        Bank bank = new Bank();
        MasterControl mc = new MasterControl(bank);
        java.util.List<String> input = java.util.Arrays.asList(
                "create checking 12345678 1.0",
                "deposit 12345678 200",
                "makemoney 12345678 10",
                "deposit 99999999 10"
        );
        java.util.List<String> out = mc.start(input);
        int n = out.size();
        assertEquals("makemoney 12345678 10", out.get(n-2));
        assertEquals("deposit 99999999 10", out.get(n-1));
    }

}


