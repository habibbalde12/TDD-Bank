package banking;

import java.util.ArrayList;
import java.util.List;

public class CommandProcess {
    protected final Bank bank;
    private final boolean compositeModeEnabled;
    private final List<CommandProcess> childProcesses = new ArrayList<>();
    private CreateCommandProcess createCommandProcess;
    private DepositCommandProcess depositCommandProcess;

    public CommandProcess(Bank bank) {
        this(bank, true);
    }

    protected CommandProcess(Bank bank, boolean compositeModeEnabled) {
        this.bank = bank;
        this.compositeModeEnabled = compositeModeEnabled;
        if (compositeModeEnabled) {
            this.createCommandProcess = new CreateCommandProcess(bank);
            this.depositCommandProcess = new DepositCommandProcess(bank);
            childProcesses.add(createCommandProcess);
            childProcesses.add(depositCommandProcess);
        }
    }

    public boolean supports(String commandType) {
        if (!compositeModeEnabled) return false;
        return createCommandProcess.supports(commandType) || depositCommandProcess.supports(commandType);
    }

    public void process(String commandLine) {
        if (commandLine == null || commandLine.isBlank()) return;
        String[] tokens = commandLine.trim().split("\\s+");
        processTokens(tokens);
    }

    public void processTokens(String[] tokens) {
        if (tokens == null || tokens.length == 0) return;
        if (compositeModeEnabled) {
            String commandType = tokens[0];
            for (CommandProcess process : childProcesses) {
                if (process.supports(commandType)) {
                    process.processTokens(tokens);
                    return;
                }
            }
        }
    }
}



