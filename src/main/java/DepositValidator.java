public class DepositValidator extends CommandValidator {

    public DepositValidator(Bank bank) {
        super(bank);
    }

    // Used by DepositValidatorTest
    public boolean validate(String[] tokens) {
        if (tokens == null || tokens.length != 3)
            return false;
        if (!"deposit".equals(tokens[0]))
            return false;

        String accountId = tokens[1];
        String amount = tokens[2];

        if (!isEightDigitNumber(accountId))
            return false;
        if (bank.getAccount(accountId) == null)
            return false;
        if (!isValidAmount(amount))
            return false;

        return true;
    }

}
