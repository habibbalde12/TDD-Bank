package banking;

public class PassTimeCommandProcess extends CommandProcess {
    public PassTimeCommandProcess(Bank bank) {
        super(bank, false);
    }

    @Override
    public boolean supports(String commandType) {
        return "pass".equalsIgnoreCase(commandType);
    }

    @Override
    public void processTokens(String[] tokens) {
        int months = Integer.parseInt(tokens[1]);
        bank.passTime(months);
    }
}

