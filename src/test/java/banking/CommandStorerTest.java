package banking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;

public class CommandStorerTest {
    @Test
    void stores_and_returns_in_order() {
        CommandStorer commandStorer = new CommandStorer();
        commandStorer.storeInvalid("creat checking 12345678 1.0");
        commandStorer.storeInvalid("depositt 12345678 100");
        List<String> actualInvalidCommands = commandStorer.getInvalid();
        Assertions.assertEquals(2, actualInvalidCommands.size());
        Assertions.assertEquals("creat checking 12345678 1.0", actualInvalidCommands.get(0));
        Assertions.assertEquals("depositt 12345678 100", actualInvalidCommands.get(1));
    }

    @Test
    void getInvalid_returns_copy() {
        CommandStorer commandStorer = new CommandStorer();
        commandStorer.storeInvalid("command_one");
        List<String> firstCopy = commandStorer.getInvalid();
        firstCopy.add("should_not_affect_internal_list");
        List<String> secondCopy = commandStorer.getInvalid();
        Assertions.assertEquals(1, secondCopy.size());
        Assertions.assertEquals("command_one", secondCopy.get(0));
    }

    @Test
    void clear_empties_storage() {
        CommandStorer commandStorer = new CommandStorer();
        commandStorer.storeInvalid("bad_command");
        commandStorer.clear();
        List<String> afterClear = commandStorer.getInvalid();
        Assertions.assertTrue(afterClear.isEmpty());
    }
}
