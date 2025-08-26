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
        String type = tokens[1].toLowerCase();
        String id = tokens[2];
        double apr = Double.parseDouble(tokens[3]);
        if (type.equals("checking")) {
            bank.addAccount(new Checkings(id, apr));
            return;
        }
        if (type.equals("savings")) {
            bank.addAccount(new Savings(id, apr));
            return;
        }
        if (type.equals("cd")) {
            double open = Double.parseDouble(tokens[4]);
            bank.addAccount(new CD(id, apr, open));
        }
    }
}
