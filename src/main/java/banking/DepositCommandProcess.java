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
        String id = tokens[1];
        double amount = Double.parseDouble(tokens[2]);
        bank.deposit(id, amount);
        bank.recordTransaction(id, String.join(" ", tokens));
    }
}
