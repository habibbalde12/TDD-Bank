package banking;

public class PassTimeCommandValidator extends CommandValidator {
    public PassTimeCommandValidator(Bank bank) {
        super(bank);
    }

    @Override
    public boolean supports(String commandType) {
        if ("pass".equalsIgnoreCase(commandType)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean validate(String[] tokens) {
        if (tokens == null) {
            return false;
        }
        if (tokens.length != 2) {
            return false;
        }
        if (!supports(tokens[0])) {
            return false;
        }
        String months = tokens[1];
        if (months == null || months.isEmpty()) {
            return false;
        }
        for (int i = 0; i < months.length(); i++) {
            char c = months.charAt(i);
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        int m = Integer.parseInt(months);
        if (m <= 0) {
            return false;
        }
        return true;
    }
}


