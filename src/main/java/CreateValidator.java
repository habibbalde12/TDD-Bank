public class CreateValidator {
    private final Bank bank;

    public CreateValidator(Bank bank) {
        this.bank = bank;
    }

    public boolean validate(String[] tokens) {
        if (tokens.length != 4) {
            return false;
        }

        String type = tokens[1];
        String accountId = tokens[2];
        String rate = tokens[3];

        if (!type.equals("savings") && !type.equals("checking")) {
            return false;
        }

        if (!isValidNumber(accountId)) {
            return false;
        }

        if (bank.getAccount(accountId) != null) {
            return false;
        }

        if (!ValidInterestRate(rate)) {
            return false;
        }

        return true;
    }

    private boolean isValidNumber(String s) {
        if (s.length() != 8)
            return false;
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i)))
                return false;
        }
        return true;
    }

    private boolean ValidInterestRate(String s) {
        if (s == null || s.isEmpty())
            return false;

        boolean hasDecimal = false;
        boolean hasDigit = false;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '.') {
                if (hasDecimal) return false;
                hasDecimal = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else {
                return false;
            }
        }

        if (!hasDigit)
            return false;

        double val = Double.parseDouble(s);
        return val >= 0 && val <= 1;
    }
}

