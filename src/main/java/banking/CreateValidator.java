package banking;

public class CreateValidator extends CommandValidator {
    public CreateValidator(Bank bank) {
        super(bank);
    }

    @Override
    public boolean supports(String commandType) {
        return "create".equalsIgnoreCase(commandType);
    }

    @Override
    public boolean validate(String[] tokens) {
        if (tokens == null) {
            return false;
        }
        if (tokens.length != 4 && tokens.length != 5) {
            return false;
        }
        if (!supports(tokens[0])) {
            return false;
        }
        String type = tokens[1].toLowerCase();
        String id = tokens[2];
        String apr = tokens[3];
        if (!type.equals("checking") && !type.equals("savings") && !type.equals("cd")) {
            return false;
        }
        if (!isEightDigitNumber(id)) {
            return false;
        }
        if (bank.getAccount(id) != null) {
            return false;
        }
        if (!validInterestRate(apr)) {
            return false;
        }
        if (type.equals("cd")) {
            if (tokens.length != 5) {
                return false;
            }
            String openAmountRaw = tokens[4];
            if (!isPlainDecimal(openAmountRaw)) {
                return false;
            }
            double openAmount = Double.parseDouble(openAmountRaw);
            if (openAmount < 1000.0 || openAmount > 10000.0) {
                return false;
            }
            return true;
        }
        return tokens.length == 4;
    }

    @Override
    protected boolean validInterestRate(String s) {
        if (!isPlainDecimal(s)) {
            return false;
        }
        double v = Double.parseDouble(s);
        return v > 0.0 && v <= 10.0;
    }
}
