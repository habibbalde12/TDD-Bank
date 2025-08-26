package banking;

public class DepositCommandProcess extends CommandProcess {
    public DepositCommandProcess(Bank bank) {
        super(bank, false);
    }

    @Override
    public boolean supports(String commandType) {
        return "deposit".equalsIgnoreCase(commandType);
    }

    @Override
    public void processTokens(String[] tokens) {
        String accountIdentifier = tokens[1];
        double depositAmount = Double.parseDouble(tokens[2]);
        bank.deposit(accountIdentifier, depositAmount);
    }
}

