package banking;

import java.util.ArrayList;
import java.util.List;

public class CommandProcess {
    protected final Bank bank;
    private final boolean compositeModeEnabled;
    private final List<CommandProcess> childProcesses = new ArrayList<>();

    private CreateCommandProcess createCommandProcess;
    private DepositCommandProcess depositCommandProcess;
    private WithdrawCommandProcess withdrawCommandProcess;
    private TransferCommandProcess transferCommandProcess;
    private PassTimeCommandProcess passTimeCommandProcess;

    public CommandProcess(Bank bank) {
        this(bank, true);
    }

    protected CommandProcess(Bank bank, boolean compositeModeEnabled) {
        this.bank = bank;
        this.compositeModeEnabled = compositeModeEnabled;
        if (compositeModeEnabled) {
            this.createCommandProcess = new CreateCommandProcess(bank);
            this.depositCommandProcess = new DepositCommandProcess(bank);
            this.withdrawCommandProcess = new WithdrawCommandProcess(bank);
            this.transferCommandProcess = new TransferCommandProcess(bank);
            this.passTimeCommandProcess = new PassTimeCommandProcess(bank);

            childProcesses.add(createCommandProcess);
            childProcesses.add(depositCommandProcess);
            childProcesses.add(withdrawCommandProcess);
            childProcesses.add(transferCommandProcess);
            childProcesses.add(passTimeCommandProcess);
        }
    }

    public Bank getBank() {
        return bank;
    }

    public boolean supports(String commandType) {
        if (!compositeModeEnabled) return false;
        return createCommandProcess.supports(commandType)
                || depositCommandProcess.supports(commandType)
                || withdrawCommandProcess.supports(commandType)
                || transferCommandProcess.supports(commandType)
                || passTimeCommandProcess.supports(commandType);
    }

    public void process(String commandLine) {
        if (commandLine == null || commandLine.isBlank()) return;
        String[] tokens = commandLine.trim().split("\\s+");
        processTokens(tokens, commandLine);
    }

    public void processTokens(String[] tokens) {
        processTokens(tokens, null);
    }

    private void processTokens(String[] tokens, String originalLine) {
        if (tokens == null || tokens.length == 0) return;
        if (!compositeModeEnabled) return;

        String commandType = tokens[0];
        for (CommandProcess process : childProcesses) {
            if (process.supports(commandType)) {
                process.processTokens(tokens);
                if (originalLine != null) {
                    if ("deposit".equalsIgnoreCase(commandType) && tokens.length >= 3) {
                        bank.recordTransaction(tokens[1], originalLine);
                    } else if ("withdraw".equalsIgnoreCase(commandType) && tokens.length >= 3) {
                        bank.recordTransaction(tokens[1], originalLine);
                    } else if ("transfer".equalsIgnoreCase(commandType) && tokens.length >= 4) {
                        bank.recordTransaction(tokens[1], originalLine);
                        bank.recordTransaction(tokens[2], originalLine);
                    }
                }
                return;
            }
        }
    }
}
