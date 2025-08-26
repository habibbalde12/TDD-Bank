package banking;

import java.util.ArrayList;
import java.util.List;

public class MasterControl {
    private final Bank bank;
    private final CommandStorer commandStorer = new CommandStorer();
    private final List<CommandValidator> validators = new ArrayList<>();
    private final CommandProcess commandProcess;

    public MasterControl(Bank bank) {
        this.bank = bank;
        validators.add(new CreateValidator(bank));
        validators.add(new DepositValidator(bank));
        validators.add(new WithdrawCommandValidator(bank));
        validators.add(new TransferCommandValidator(bank));
        validators.add(new PassTimeCommandValidator(bank));
        this.commandProcess = new CommandProcess(bank);
    }

    public List<String> start(List<String> input) {
        commandStorer.clear();
        if (input == null) {
            return commandStorer.getInvalid();
        }
        for (String line : input) {
            if (line == null || line.isBlank()) {
                continue;
            }
            String trimmed = line.trim();
            String[] tokens = trimmed.split("\\s+");
            String commandType = tokens[0].toLowerCase();
            boolean valid = false;
            for (CommandValidator v : validators) {
                if (v.supports(commandType) && v.validate(tokens)) {
                    valid = true;
                    break;
                }
            }
            if (!valid) {
                commandStorer.storeInvalid(trimmed);
                continue;
            }
            commandProcess.process(trimmed);
        }
        return commandStorer.getInvalid();
    }
}
