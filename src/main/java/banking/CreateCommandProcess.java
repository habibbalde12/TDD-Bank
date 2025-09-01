package banking;

public class CreateCommandProcess extends CommandProcess {
    public CreateCommandProcess(Bank bank) {
        super(bank, false);
    }

    @Override
    public boolean supports(String commandType) {
        return "create".equalsIgnoreCase(commandType);
    }

    @Override
    public void processTokens(String[] tokens) {
        String accountType = tokens[1];
        String accountId = tokens[2];
        double apr = Double.parseDouble(tokens[3]);
        if (tokens.length == 5) {
            double openingBalance = Double.parseDouble(tokens[4]);
            bank.addAccount(accountType, accountId, apr, openingBalance);
        } else {
            bank.addAccount(accountType, accountId, apr);
        }
    }
}
