package banking;

public class CreateValidator extends CommandValidator {

    public CreateValidator(Bank bank) {
        super(bank);
    }


    public boolean validate(String[] tokens) {
        if (tokens == null || tokens.length != 4)
            return false;
        if (!"create".equals(tokens[0]))
            return false;

        String type = tokens[1];
        String accountId = tokens[2];
        String rate = tokens[3];

        if (!type.equals("savings") && !type.equals("checking"))
            return false;
        if (!isEightDigitNumber(accountId))
            return false;
        if (bank.getAccount(accountId) != null)
            return false;
        if (!validInterestRate(rate))
            return false;

        return true;
    }

}





