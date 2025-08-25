package banking;

public class PassTimeCommandProcess extends CommandProcess {
    public PassTimeCommandProcess(Bank bank) {
        super(bank);
    }

    @Override
    public boolean supports(String commandType) {
        if ("pass".equalsIgnoreCase(commandType)) {
            return true;
        }
        return false;
    }

    @Override
    public void processTokens(String[] tokens) {
        int months = Integer.parseInt(tokens[1]);
        bank.passTime(months);
    }
}
