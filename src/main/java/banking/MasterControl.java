package banking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MasterControl {
    private final Bank bank;
    private final List<CommandValidator> validators;
    private final CommandProcess processor;
    private final CommandStorer storer;
    private final Output output;

    public MasterControl(Bank bank) {
        this.bank = bank;
        this.validators = new ArrayList<>(Arrays.asList(
                new CreateValidator(bank),
                new DepositValidator(bank),
                new WithdrawCommandValidator(bank),
                new TransferCommandValidator(bank),
                new PassTimeCommandValidator(bank)
        ));
        this.processor = new CommandProcess(bank, true);
        this.storer = new CommandStorer();
        this.output = new Output();
    }

    public List<String> start(List<String> input) {
        storer.clear();
        if (input == null) {
            List<String> result = new ArrayList<>();
            result.addAll(output.print(bank));
            result.addAll(storer.getInvalid());
            return result;
        }
        for (String line : input) {
            if (line == null || line.isBlank()) {
                continue;
            }
            String[] tokens = line.trim().split("\\s+");
            String type = tokens[0].toLowerCase();
            boolean supported = false;
            for (CommandValidator v : validators) {
                if (v.supports(type)) {
                    supported = true;
                    if (v.validate(tokens)) {
                        processor.process(line);
                    } else {
                        storer.storeInvalid(line);
                    }
                    break;
                }
            }
            if (!supported) {
                storer.storeInvalid(line);
            }
        }
        List<String> result = new ArrayList<>();
        result.addAll(output.print(bank));
        result.addAll(storer.getInvalid());
        return result;
    }
}
