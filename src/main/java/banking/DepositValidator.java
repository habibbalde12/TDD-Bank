package banking;

public class DepositValidator extends CommandValidator {
    public DepositValidator(Bank bank) {
        super(bank);
    }

    @Override
    public boolean supports(String commandType) {
        if ("deposit".equalsIgnoreCase(commandType)) {
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
        Account account = bank.getAccount(id);
        if (account == null) {
            return false;
        }
        if (!(account instanceof Checkings) && !(account instanceof Savings)) {
            return false;
        }
        if (!isValidAmount(amountRaw)) {
            return false;
        }

        double amount = Double.parseDouble(amountRaw);
        if (account instanceof Savings && amount > 2500.0) {
            return false;
        }
        if (account instanceof Checkings && amount > 1000.0) {
            return false;
        }
        return true;
    }
}



