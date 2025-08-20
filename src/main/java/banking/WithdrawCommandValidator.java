package banking;

public class WithdrawCommandValidator extends CommandValidator {
    public WithdrawCommandValidator(Bank bank) {
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
    public boolean validate(String[] tokens) {
        if (tokens == null || tokens.length != 3) {
            return false;
        }
        if (!supports(tokens[0])) {
            return false;
        }

        String id = tokens[1];
        String amountRaw = tokens[2];

        if (!isEightDigitNumber(id)) {
            return false;
        }
        if (!isValidAmount(amountRaw)) {
            return false;
        }

        double amount = Double.parseDouble(amountRaw);
        Account account = bank.getAccount(id);
        if (account == null) {
            return false;
        }
        if (account instanceof CD) {
            return false;
        }
        if (account instanceof Checkings && amount > 400.0) {
            return false;
        }
        if (account instanceof Savings && amount > 1000.0) {
            return false;
        }
        return true;
    }
}


