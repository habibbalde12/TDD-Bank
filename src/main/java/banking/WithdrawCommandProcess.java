package banking;

public class WithdrawCommandProcess extends CommandProcess {
    public WithdrawCommandProcess(Bank bank) {
        super(bank, false);
    }

    @Override
    public boolean supports(String commandType) {
        return "withdraw".equalsIgnoreCase(commandType);
    }

    @Override
    public void processTokens(String[] tokens) {
        String id = tokens[1];
        double amount = Double.parseDouble(tokens[2]);
        bank.withdraw(id, amount);
        bank.recordTransaction(id, String.join(" ", tokens));
    }
}
