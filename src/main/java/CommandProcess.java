public class CommandProcess {
    private final Bank bank;

    public CommandProcess(Bank bank) {
        this.bank = bank;
    }

    public void process(String command) {
        if (command == null || command.isBlank()) return;

        String[] tokens = command.trim().split("\\s+");
        String verb = tokens[0];

        switch (verb) {
            case "create":
                handleCreate(tokens);
                break;
            case "deposit":
                handleDeposit(tokens);
                break;
            default:
                break;
        }
    }

    private void handleCreate(String[] tokens) {
        // expected: create <type> <id> <apr>
        String type = tokens[1];
        String id = tokens[2];
        double apr = Double.parseDouble(tokens[3]);

        if ("checking".equals(type)) {
            bank.addAccount(new Checkings(id, apr));
        } else if ("savings".equals(type)) {
            bank.addAccount(new Savings(id, apr));
        }

    }

    private void handleDeposit(String[] tokens) {
        String id = tokens[1];
        double amount = Double.parseDouble(tokens[2]);
        bank.deposit(id, amount);
    }
}

