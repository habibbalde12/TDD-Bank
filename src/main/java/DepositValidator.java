public class DepositValidator {
    private final Bank bank;

    public DepositValidator(Bank bank) {
        this.bank = bank;
    }

    public boolean validate(String[] tokens) {
        if (tokens.length != 3) {
            return false;
        }

        String accountId = tokens[1];
        String amount = tokens[2];

        if (!isEightDigitNumber(accountId)) {
            return false;
        }

        if (bank.getAccount(accountId) == null) {
            return false;
        }

        if (!isValidAmount(amount)) {
            return false;
        }

        return true;
    }

    private boolean isEightDigitNumber(String s) {
        if (s.length() != 8)
            return false;
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i)))
                return false;
        }
        return true;
    }

    private boolean isValidAmount(String s) {
        if (s == null || s.isEmpty())
            return false;

        boolean hasDecimal = false;
        boolean hasDigit = false;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '.') {
                if (hasDecimal)
                    return false;
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
        return val > 0;
    }
}

