package banking;

public class WithdrawCommandProcess extends CommandProcess {
    public WithdrawCommandProcess(Bank bank) {
        super(bank);
    }

    @Override
    public boolean supports(String commandType) {
        if ("withdraw".equalsIgnoreCase(commandType)) {
            return true;
        }
        return false;
    }

    @Override
    public void processTokens(String[] tokens) {
        String id = tokens[1];
        String amountRaw = tokens[2];
        double amount = Double.parseDouble(amountRaw);
        bank.withdraw(id, amount);
    }
}





