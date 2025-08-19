package banking;

public class CreateCommandProcess extends CommandProcess {
    public CreateCommandProcess(Bank bank) {
        super(bank, false);
    }

    @Override
    public boolean supports(String commandType) {
        return "create".equals(commandType);
    }

    @Override
    public void processTokens(String[] tokens) {
        String accountType = tokens[1];
        String accountIdentifier = tokens[2];
        double annualPercentageRate = Double.parseDouble(tokens[3]);
        if ("checking".equals(accountType)) {
            bank.addAccount(new Checkings(accountIdentifier, annualPercentageRate));
        } else if ("savings".equals(accountType)) {
            bank.addAccount(new Savings(accountIdentifier, annualPercentageRate));
        }
    }
}

