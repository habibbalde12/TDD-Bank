package banking;

public class TransferCommandProcess extends CommandProcess {
    public TransferCommandProcess(Bank bank) {
        super(bank, false);
    }

    @Override
    public boolean supports(String commandType) {
        return "transfer".equalsIgnoreCase(commandType);
    }

    @Override
    public void processTokens(String[] tokens) {
        String fromId = tokens[1];
        String toId = tokens[2];
        double amount = Double.parseDouble(tokens[3]);
        bank.transfer(fromId, toId, amount);
    }
}

