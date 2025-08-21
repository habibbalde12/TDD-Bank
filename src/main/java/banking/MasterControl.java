package banking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MasterControl {
    private final List<CommandValidator> validators;
    private final CommandProcess commandProcess;
    private final CommandStorer commandStorer;

    public MasterControl(CommandValidator createValidator,
                         CommandValidator depositValidator,
                         CommandValidator withdrawValidator,
                         CommandValidator transferValidator,
                         CommandProcess commandProcess,
                         CommandStorer commandStorer) {
        this.validators = new ArrayList<>(Arrays.asList(createValidator, depositValidator, withdrawValidator, transferValidator));
        this.commandProcess = commandProcess;
        this.commandStorer = commandStorer;
    }

    public List<String> start(List<String> input) {
        commandStorer.clear();
        if (input == null) {
            return commandStorer.getInvalid();
        }
        for (String line : input) {
            boolean valid = false;
            for (CommandValidator v : validators) {
                if (v.validate(line)) {
                    valid = true;
                    break;
                }
            }
            if (!valid) {
                commandStorer.storeInvalid(line);
                continue;
            }
            commandProcess.process(line);
        }
        return commandStorer.getInvalid();
    }
}



