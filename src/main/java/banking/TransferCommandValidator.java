package banking;

public class TransferCommandValidator extends CommandValidator {
    public TransferCommandValidator(Bank bank) {
        super(bank);
    }

    @Override
    public boolean supports(String commandType) {
        if ("transfer".equalsIgnoreCase(commandType)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean validate(String[] tokens) {
        if (tokens == null || tokens.length != 4) {
            return false;
        }
        if (!supports(tokens[0])) {
            return false;
        }

        String fromId = tokens[1];
        String toId = tokens[2];
        String amountRaw = tokens[3];

        if (!isEightDigitNumber(fromId)) {
            return false;
        }
        if (!isEightDigitNumber(toId)) {
            return false;
        }
        if (fromId.equals(toId)) {
            return false;
        }
        if (!isValidAmount(amountRaw)) {
            return false;
        }

        double amount = Double.parseDouble(amountRaw);
        if (amount <= 0.0) {
            return false;
        }

        Account from = bank.getAccount(fromId);
        Account to = bank.getAccount(toId);

        if (from == null) {
            return false;
        }
        if (to == null) {
            return false;
        }
        if (from instanceof CD) {
            return false;
        }
        if (to instanceof CD) {
            return false;
        }

        return true;
    }
}


