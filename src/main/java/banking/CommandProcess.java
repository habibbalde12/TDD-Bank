package banking;

import java.util.ArrayList;
import java.util.List;

public class CommandProcess {
    protected final Bank bank;
    private final List<CommandProcess> children = new ArrayList<>();

    public CommandProcess(Bank bank) {
        this(bank, true);
    }

    public CommandProcess(Bank bank, boolean compositeModeEnabled) {
        this.bank = bank;
        if (compositeModeEnabled) {
            children.add(new CreateCommandProcess(bank));
            children.add(new DepositCommandProcess(bank));
            children.add(new WithdrawCommandProcess(bank));
            children.add(new TransferCommandProcess(bank));
            children.add(new PassTimeCommandProcess(bank));
        }
    }

    public boolean supports(String commandType) {
        for (CommandProcess p : children) {
            if (p.supports(commandType)) return true;
        }
        return false;
    }

    public void processTokens(String[] tokens) {}

    public void process(String command) {
        if (command == null || command.isBlank()) return;
        String[] tokens = command.trim().split("\\s+");
        String type = tokens[0].toLowerCase();
        for (CommandProcess p : children) {
            if (p.supports(type)) {
                p.processTokens(tokens);
                return;
            }
        }
    }
}
