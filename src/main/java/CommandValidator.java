public class CommandValidator {
    private final CreateValidator createValidator;
    private final DepositValidator depositValidator;

    public CommandValidator(Bank bank) {
        this.createValidator = new CreateValidator(bank);
        this.depositValidator = new DepositValidator(bank);
    }

    public boolean validate(String command) {
        if (command == null || command.isBlank()) {
            return false;
        }

        String[] tokens = command.trim().split("\\s+");
        if (tokens.length == 0) {
            return false;
        }

        if (tokens[0].equalsIgnoreCase("create")) {
            return createValidator.validate(tokens);
        } else if (tokens[0].equalsIgnoreCase("deposit")) {
            return depositValidator.validate(tokens);
        }

        return false;
    }


}

